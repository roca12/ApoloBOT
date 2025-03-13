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

public class PDFProcessor {

  public PDFProcessor() {
    // TODO Auto-generated constructor stub
  }

  private static String translatePDFText(String originalText, Translate translate) {
    String targetLanguage = "es";

    Translation translation =
        translate.translate(originalText, Translate.TranslateOption.targetLanguage(targetLanguage));
    String traduccion = translation.getTranslatedText();
    return traduccion;
  }

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

  public static File generateTXT(String text) throws IOException {
    File file = new File("traduccion.txt");
    FileWriter fw = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(format(text));
    bw.close();
    fw.close();

    return file;
  }

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
