package com.roca12.apolobot.util;

/**
 * Utility class to run the Javadoc translation process. This class simply calls the main method of
 * TranslateDocumentation to translate all Javadoc comments in the project from English to Spanish.
 *
 * @author roca12
 * @version 2025-I
 */
public class RunTranslation {

  /**
   * Main method to run the translation process.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    System.out.println("Starting Javadoc translation process...");

    // Call the TranslateDocumentation main method to perform the translation
    TranslateDocumentation.main(args);

    System.out.println("Javadoc translation process completed.");
  }
}
