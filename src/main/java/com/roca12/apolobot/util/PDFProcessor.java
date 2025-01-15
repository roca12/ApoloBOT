package com.roca12.apolobot.util;

import java.io.File;
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

	public static File processPDF(InputStream pdfStream, Translate translate) throws IOException {
		PDDocument document = PDDocument.load(pdfStream);

		File outputFile = File.createTempFile("translated", ".pdf");
		PDFTextStripper textStripper = new PDFTextStripper();
		for (int i = 0; i < document.getNumberOfPages(); i++) {
			textStripper.setStartPage(i + 1);
			textStripper.setEndPage(i + 1);

			String originalText = textStripper.getText(document);
			try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(i),
					PDPageContentStream.AppendMode.OVERWRITE, true)) {
				String[] lines = originalText.split("\n");
				int yPosition = 750;
				contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
				for (String line : lines) {
					contentStream.beginText();
					contentStream.newLineAtOffset(50, yPosition);
					line = translatePDFText(line, translate);
					contentStream.showText(sanitizeText(line));
					contentStream.endText();
					yPosition -= 15;
				}
			}

		}
		pdfStream.close();

		document.save(outputFile);
		document.close();
		return outputFile;
	}

	private static String sanitizeText(String text) {
		return text.replaceAll("[\\r\\f]", ""); // Eliminar caracteres de control (\r y \f)
	}

}
