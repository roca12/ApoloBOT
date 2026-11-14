package com.roca12.apolobot.util;

import java.text.Normalizer;
import java.util.Locale;

/** Utilidades para comparar textos escritos por personas. */
public class TextUtil {

  /** Quita tildes, pasa a minusculas y une las palabras con "_" para poder comparar nombres */
  public static String normalizar(String text) {
    if (text == null) return "";
    String sinTildes = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    return sinTildes
        .toLowerCase(Locale.ROOT)
        .replaceAll("[^a-z0-9]+", "_")
        .replaceAll("^_+|_+$", "");
  }
}
