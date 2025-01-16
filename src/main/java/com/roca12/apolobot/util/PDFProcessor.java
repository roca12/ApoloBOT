package com.roca12.apolobot.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

public class PDFProcessor {

	public PDFProcessor() {
		// TODO Auto-generated constructor stub
	}

	private static String translatePDFText(String originalText, Translate translate) {
		String targetLanguage = "es";

		Translation translation = translate.translate(originalText,
				Translate.TranslateOption.targetLanguage(targetLanguage));
		String traduccion = translation.getTranslatedText();
		return traduccion;
	}

	public static String processPDF(InputStream pdfStream, Translate translate) throws IOException {
		PDDocument document = PDDocument.load(pdfStream);
		StringBuilder sb = new StringBuilder();
		File outputFile = File.createTempFile("translated", ".pdf");
		PDFTextStripper textStripper = new PDFTextStripper();
		for (int i = 0; i < document.getNumberOfPages(); i++) {
			textStripper.setStartPage(i + 1);
			textStripper.setEndPage(i + 1);
			String originalText = textStripper.getText(document);
			sb.append(translatePDFText(originalText, translate));
		}

		pdfStream.close();


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
		for(int i = 0; i<text.length(); i++) {
			if(text.charAt(i) == '\n'||i+1%100==0) {
				sb.append(text.substring(x, i+1)+"\n");
				x = i+1;
				continue;
			}
			
		}
		return sb.toString();
	}

}
