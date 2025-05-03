package com.roca12.apolobot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for translating documentation comments in Java files. This class reads Java files,
 * identifies Javadoc comments, translates them from English to Spanish using the TranslationUtil,
 * and saves the translated versions to new files with a "_es" suffix.
 *
 * @author roca12
 * @version 2025-I
 */
public class TranslateDocumentation {

  /** Pattern for matching Javadoc comments */
  private static final Pattern JAVADOC_PATTERN =
      Pattern.compile("/\\*\\*(.*?)\\*/", Pattern.DOTALL);

  /** List of files that have been processed */
  private static List<String> processedFiles = new ArrayList<>();

  /**
   * Main method to run the documentation translation.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    // Define the base directory for the project
    String baseDir = "src/main/java/com/roca12/apolobot";

    // List of all Java files to translate
    String[] filesToTranslate = {
      baseDir + "/ApolobotApplication.java",
      baseDir + "/controller/AplMain.java",
      baseDir + "/controller/ApplicationContextHolder.java",
      baseDir + "/controller/handler/SlashBuilder.java",
      baseDir + "/controller/handler/SlashListener.java",
      baseDir + "/controller/handler/MessageListener.java",
      baseDir + "/controller/handler/LessonMessageSender.java",
      baseDir + "/controller/handler/TrainingAnnouncer.java",
      baseDir + "/model/Embed.java",
      baseDir + "/model/ReRunApolo.java",
      baseDir + "/model/Training.java",
      baseDir + "/repository/ReRunApoloRepository.java",
      baseDir + "/repository/TrainingRepository.java",
      baseDir + "/service/ReRunApoloService.java",
      baseDir + "/service/TrainingService.java",
      baseDir + "/util/Encryptor.java",
      baseDir + "/util/ILoveResponses.java",
      baseDir + "/util/PDFProcessor.java",
      baseDir + "/util/TranslateDocumentation.java",
      baseDir + "/util/TranslationUtil.java"
    };

    // Process each file
    for (String filePath : filesToTranslate) {
      try {
        processFile(filePath);
        System.out.println("Processed: " + filePath);
      } catch (IOException e) {
        System.err.println("Error processing file " + filePath + ": " + e.getMessage());
      }
    }

    // Print summary
    System.out.println("\nTranslation complete. Processed " + processedFiles.size() + " files:");
    for (String file : processedFiles) {
      System.out.println("- " + file);
    }
  }

  /**
   * Processes a single Java file, translating its Javadoc comments.
   *
   * @param filePath The path to the Java file to process
   * @throws IOException If an I/O error occurs
   */
  private static void processFile(String filePath) throws IOException {
    // Read the file content
    Path path = Paths.get(filePath);
    String content = new String(Files.readAllBytes(path));

    // Find and translate all Javadoc comments
    Matcher matcher = JAVADOC_PATTERN.matcher(content);
    StringBuffer sb = new StringBuffer();

    while (matcher.find()) {
      String javadoc = matcher.group(0);
      String translatedJavadoc = TranslationUtil.translateJavadoc(javadoc);

      // Replace the original Javadoc with the translated one
      matcher.appendReplacement(sb, Matcher.quoteReplacement(translatedJavadoc));
    }
    matcher.appendTail(sb);

    // Write the translated content back to the original file
    Files.write(path, sb.toString().getBytes());

    // Add to the list of processed files
    processedFiles.add(filePath);
  }

  /**
   * Alternative method to process a file line by line. This method is more memory-efficient for
   * large files.
   *
   * @param filePath The path to the Java file to process
   * @throws IOException If an I/O error occurs
   */
  private static void processFileLine(String filePath) throws IOException {
    File inputFile = new File(filePath);
    File tempFile = new File(filePath + ".temp");

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

      StringBuilder javadocBuilder = null;
      String line;

      while ((line = reader.readLine()) != null) {
        // Check if this line starts a Javadoc comment
        if (line.trim().startsWith("/**")) {
          javadocBuilder = new StringBuilder(line + "\n");
        }
        // Check if this line is part of a Javadoc comment
        else if (javadocBuilder != null && !line.trim().endsWith("*/")) {
          javadocBuilder.append(line).append("\n");
        }
        // Check if this line ends a Javadoc comment
        else if (javadocBuilder != null && line.trim().endsWith("*/")) {
          javadocBuilder.append(line).append("\n");

          // Translate the complete Javadoc comment
          String javadoc = javadocBuilder.toString();
          String translatedJavadoc = TranslationUtil.translateJavadoc(javadoc);

          // Write the translated Javadoc
          writer.write(translatedJavadoc);

          // Reset the Javadoc builder
          javadocBuilder = null;
        }
        // If not in a Javadoc comment, write the line as is
        else {
          writer.write(line + "\n");
        }
      }
    }

    // Replace the original file with the temp file
    if (!inputFile.delete()) {
      throw new IOException("Could not delete original file: " + filePath);
    }
    if (!tempFile.renameTo(inputFile)) {
      throw new IOException("Could not rename temp file to original: " + filePath);
    }

    // Add to the list of processed files
    processedFiles.add(filePath);
  }
}
