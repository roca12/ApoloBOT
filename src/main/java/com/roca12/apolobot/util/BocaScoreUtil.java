package com.roca12.apolobot.util;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Lee el marcador de la CCPL del BOCA del Poli con el usuario publico "score" (sin clave). */
public class BocaScoreUtil {

  public static final String BASE_URL = "http://boca.poligran.edu.co";
  private static final String USUARIO = "score";
  private static final String CLAVE = "";

  // la pagina del marcador se refresca cada 60 s
  private static final long CACHE_MS = 60_000;

  private static Marcador cache;
  private static long cacheHora;

  /** minuto es null si el problema no esta resuelto */
  public record Problema(String letra, int intentos, Integer minuto) {
    public boolean resuelto() {
      return minuto != null;
    }
  }

  public record Equipo(
      int puestoGlobal,
      Integer puestoLocal,
      String usuario,
      String sede,
      String nombre,
      String universidad,
      List<Problema> problemas,
      int resueltos,
      int penalidad) {}

  public record Marcador(
      String titulo, boolean congelado, List<String> letras, List<Equipo> equipos) {

    public List<Equipo> deUniversidad(String universidad) {
      String buscada = TextUtil.normalizar(universidad);
      return equipos.stream()
          .filter(e -> TextUtil.normalizar(e.universidad()).contains(buscada))
          .toList();
    }
  }

  public static synchronized Marcador getMarcador() throws IOException {
    if (cache == null || System.currentTimeMillis() - cacheHora > CACHE_MS) {
      cache = descargarMarcador();
      cacheHora = System.currentTimeMillis();
    }
    return cache;
  }

  private static Marcador descargarMarcador() throws IOException {
    HttpClient client =
        HttpClient.newBuilder()
            .cookieHandler(new CookieManager())
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    // BOCA manda la clave como sha256(sha256(clave) + id de sesion), el id viene en el login
    String login = get(client, BASE_URL + "/index.php");
    Matcher sesion = Pattern.compile("password\\.value\\) \\+ '([^']+)'").matcher(login);
    if (!sesion.find()) {
      throw new IOException("No encontre el formulario de login de BOCA");
    }
    String hash = sha256(sha256(CLAVE) + sesion.group(1));
    String respuesta = get(client, BASE_URL + "/index.php?name=" + USUARIO + "&password=" + hash);
    if (!respuesta.contains("score/index.php")) {
      throw new IOException("BOCA no acepto el usuario " + USUARIO);
    }

    String html = get(client, BASE_URL + "/score/index.php");
    return parse(html, getTitulo(login));
  }

  // Las pestañas vienen en la misma tabla: sitegroup1 es el global y sitegroup2 el local
  private static Marcador parse(String html, String titulo) {
    List<String> letras = new ArrayList<>();
    Matcher encabezado = Pattern.compile("<td nowrap><b>([A-Z]+) &nbsp;</b></td>").matcher(html);
    while (encabezado.find()) letras.add(encabezado.group(1));

    Pattern fila = Pattern.compile("<tr class=\"sitegroup(\\d+)\">(.*?)</tr>", Pattern.DOTALL);
    Pattern celda = Pattern.compile("<td[^>]*>(.*?)</td>", Pattern.DOTALL);

    List<List<String>> globales = new ArrayList<>();
    Map<String, Integer> puestosLocales = new HashMap<>();
    Matcher m = fila.matcher(html);
    while (m.find()) {
      List<String> celdas = new ArrayList<>();
      Matcher c = celda.matcher(m.group(2));
      while (c.find()) celdas.add(c.group(1));
      if (celdas.size() < 4) continue;

      if (m.group(1).equals("1")) {
        globales.add(celdas);
      } else if (m.group(1).equals("2")) {
        puestosLocales.put(usuario(celdas), Integer.parseInt(texto(celdas.get(0))));
      }
    }

    List<Equipo> equipos = new ArrayList<>();
    for (List<String> celdas : globales) {
      equipos.add(parseEquipo(celdas, letras, puestosLocales.get(usuario(celdas))));
    }
    return new Marcador(titulo, html.contains("Scoreboard frozen"), letras, equipos);
  }

  private static Equipo parseEquipo(List<String> celdas, List<String> letras, Integer puestoLocal) {
    // ej: "[Bog-39] [Bog-39]UEBcammers - Universidad El Bosque"
    String completo = texto(celdas.get(2));
    Matcher sede = Pattern.compile("^\\[([^\\]]+)\\]").matcher(completo);
    String codigoSede = sede.find() ? sede.group(1) : "";
    String sinSede = completo.replaceAll("^(\\[[^\\]]*\\]\\s*)+", "");
    int guion = sinSede.lastIndexOf(" - ");
    String nombre = guion >= 0 ? sinSede.substring(0, guion) : sinSede;
    String universidad = guion >= 0 ? sinSede.substring(guion + 3) : "";

    // cada celda de problema es "intentos/minuto", o "intentos/-" si no lo ha resuelto
    List<Problema> problemas = new ArrayList<>();
    for (int i = 0; i < letras.size() && 3 + i < celdas.size() - 1; i++) {
      Matcher intento = Pattern.compile("(\\d+)/(\\d+|-)").matcher(texto(celdas.get(3 + i)));
      if (!intento.find()) continue;
      problemas.add(
          new Problema(
              letras.get(i),
              Integer.parseInt(intento.group(1)),
              intento.group(2).equals("-") ? null : Integer.parseInt(intento.group(2))));
    }

    // total: "resueltos (penalidad)"
    Matcher total =
        Pattern.compile("(\\d+)\\s*\\((\\d+)\\)").matcher(texto(celdas.get(celdas.size() - 1)));
    int resueltos = 0, penalidad = 0;
    if (total.find()) {
      resueltos = Integer.parseInt(total.group(1));
      penalidad = Integer.parseInt(total.group(2));
    }

    return new Equipo(
        Integer.parseInt(texto(celdas.get(0))),
        puestoLocal,
        usuario(celdas),
        codigoSede,
        nombre,
        universidad,
        problemas,
        resueltos,
        penalidad);
  }

  // "team039/1" -> "team039"
  private static String usuario(List<String> celdas) {
    return texto(celdas.get(1)).replaceAll("/\\d+$", "");
  }

  private static String getTitulo(String loginHtml) {
    Matcher h2 = Pattern.compile("<h2>(.*?)</h2>", Pattern.DOTALL).matcher(loginHtml);
    if (!h2.find()) return "CCPL";
    // la primera linea es el nombre de la liga, el resto es "CCPL 2026 Contest"
    List<String> lineas = List.of(h2.group(1).split("<br\\s*/?>"));
    return texto(String.join(" ", lineas.size() > 1 ? lineas.subList(1, lineas.size()) : lineas));
  }

  private static String texto(String html) {
    return html.replaceAll("<[^>]*>", " ")
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replaceAll("\\s+", " ")
        .trim();
  }

  private static String get(HttpClient client, String url) throws IOException {
    HttpRequest request =
        HttpRequest.newBuilder(URI.create(url))
            .timeout(Duration.ofSeconds(20))
            .header("User-Agent", "ApoloBOT")
            .build();
    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new IOException("BOCA respondio " + response.statusCode() + " para " + url);
      }
      return response.body();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException("Consulta a BOCA interrumpida", e);
    }
  }

  private static String sha256(String text) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(md.digest(text.getBytes(StandardCharsets.UTF_8)));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }
}
