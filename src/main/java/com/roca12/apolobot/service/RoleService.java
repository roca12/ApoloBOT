package com.roca12.apolobot.service;

import com.roca12.apolobot.util.TextUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

/** Rangos de los miembros del servidor. Necesita el Server Members Intent activado. */
public class RoleService {

  // De mayor a menor nivel
  public static final List<String> RANGOS =
      List.of("Élite", "Avanzada", "Intermedia", "Básica", "Aprendiz");

  /** Rangos que tiene el usuario en el servidor, en el orden de RANGOS. */
  public List<String> getRangos(User user, Server server) {
    List<String> roles =
        user.getRoles(server).stream().map(r -> TextUtil.normalizar(r.getName())).toList();
    return RANGOS.stream().filter(r -> roles.contains(TextUtil.normalizar(r))).toList();
  }

  /** Miembros del servidor agrupados por rango. Alguien con varios rangos sale en cada uno. */
  public Map<String, List<User>> getMiembrosPorRango(Server server) {
    Map<String, List<User>> porRango = new LinkedHashMap<>();
    RANGOS.forEach(r -> porRango.put(r, new ArrayList<>()));

    for (User user : server.getMembers()) {
      if (user.isBot()) continue;
      for (String rango : getRangos(user, server)) {
        porRango.get(rango).add(user);
      }
    }

    porRango.values().forEach(l -> l.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName())));
    return porRango;
  }
}
