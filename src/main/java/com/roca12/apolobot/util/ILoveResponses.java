package com.roca12.apolobot.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * Utility class for handling responses to "I love" messages.
 * This class reads a list of response phrases from a file and
 * provides a method to get a random phrase from the list.
 * 
 * @author roca12
 * @version 2025-I
 */
public class ILoveResponses {
  /** List of response phrases loaded from the file */
  private static List<String> phrases;

  /**
   * Gets a random response phrase from the list.
   * This method reads the phrases from the file each time it's called,
   * ensuring that any updates to the file are immediately available.
   * 
   * @return A randomly selected response phrase
   * @throws RuntimeException If there is an error reading the file
   */
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
