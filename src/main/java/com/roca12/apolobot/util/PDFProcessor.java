package com.roca12.apolobot.util;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Utility class for processing PDF files.
 * This class provides methods to extract text from PDF files,
 * translate the text to Spanish, and save the translated text to a file.
 * 
 * @author roca12
 * @version 2025-I
 */
public class PDFProcessor {

  /**
   * Default constructor.
   */
  public PDFProcessor() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Translates the extracted text from a PDF to Spanish.
   * 
   * @param originalText The original text extracted from the PDF
   * @param translate The Google Translate service instance
   * @return The translated text in Spanish
   */
  private static String translatePDFText(String originalText, Translate translate) {
    String targetLanguage = "es";

    Translation translation =
        translate.translate(originalText, Translate.TranslateOption.targetLanguage(targetLanguage));
    String traduccion = translation.getTranslatedText();
    return traduccion;
  }

  /**
   * Processes a PDF file by extracting its text and translating it to Spanish.
   * This method processes the PDF page by page, translating each page separately.
   * 
   * @param archivo The PDF file as a byte array
   * @param translate The Google Translate service instance
   * @return The translated text from the entire PDF
   * @throws IOException If there is an error reading the PDF
   */
  public static String processPDF(byte[] archivo, Translate translate) throws IOException {
    PDDocument document = Loader.loadPDF(archivo);
    StringBuilder sb = new StringBuilder();
    PDFTextStripper textStripper = new PDFTextStripper();
    for (int i = 0; i < document.getNumberOfPages(); i++) {
      textStripper.setStartPage(i + 1);
      textStripper.setEndPage(i + 1);
      String originalText = textStripper.getText(document);
      sb.append(translatePDFText(originalText, translate));
    }

    document.close();
    return sb.toString();
  }

  /**
   * Generates a text file containing the translated text.
   * 
   * @param text The translated text to save
   * @return The generated text file
   * @throws IOException If there is an error writing to the file
   */
  public static File generateTXT(String text) throws IOException {
    File file = new File("traduccion.txt");
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(format(text));
    bw.close();
    fw.close();

    return file;
  }

  /**
   * Formats the translated text for better readability.
   * This method adds line breaks to ensure the text is properly formatted.
   * 
   * @param text The text to format
   * @return The formatted text
   */
  public static String format(String text) {
    StringBuilder sb = new StringBuilder();
    int x = 0;
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '\n' || i + 1 % 100 == 0) {
        sb.append(text.substring(x, i + 1) + "\n");
        x = i + 1;
        continue;
      }
    }
    return sb.toString();
  }
}
