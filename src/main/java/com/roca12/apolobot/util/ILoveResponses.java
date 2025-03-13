package com.roca12.apolobot.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class ILoveResponses {
  public static List<String> phrases;

  public static String getRandomPhrase() {
    Path path = Paths.get("src/main/resources/files/iloveresponses.txt");
    try {
      phrases = Files.readAllLines(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    Random random = new Random();
    int randomIndex = random.nextInt(phrases.size());
    return phrases.get(randomIndex);
  }
}
