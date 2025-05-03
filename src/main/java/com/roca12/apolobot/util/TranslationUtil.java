package com.roca12.apolobot.util;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.core.io.ClassPathResource;

/**
 * Utility class for translating text from English to Spanish.
 * This class uses the Google Cloud Translation API to perform translations.
 * It is primarily used for translating documentation comments in the codebase.
 * 
 * @author roca12
 * @version 2025-I
 */
public class TranslationUtil {

    /** API key for Google Translate service */
    private static String traductorApiKey;
    
    /** Google Translate service instance */
    private static Translate translate;
    
    static {
        // Load the API key from properties file
        try {
            Properties prop = new Properties();
            InputStream r = new ClassPathResource("files/main.properties").getInputStream();
            prop.load(r);
            traductorApiKey = prop.getProperty("apolo.traductor.apikey");
            
            // Initialize the Google Translate service
            translate = TranslateOptions.newBuilder()
                .setApiKey(traductorApiKey)
                .build()
                .getService();
        } catch (IOException e) {
            System.err.println("Error loading translator API key: " + e.getMessage());
        }
    }
    
    /**
     * Translates text from English to Spanish.
     * 
     * @param text The English text to translate
     * @return The translated Spanish text
     */
    public static String translateToSpanish(String text) {
        if (translate == null) {
            System.err.println("Translation service not initialized");
            return text;
        }
        
        try {
            // Perform the translation
            Translation translation = translate.translate(
                text,
                Translate.TranslateOption.targetLanguage("es"),
                Translate.TranslateOption.sourceLanguage("en")
            );
            
            return translation.getTranslatedText();
        } catch (Exception e) {
            System.err.println("Error translating text: " + e.getMessage());
            return text;
        }
    }
    
    /**
     * Translates a Javadoc comment from English to Spanish.
     * This method preserves the Javadoc format while translating the content.
     * 
     * @param javadoc The English Javadoc comment to translate
     * @return The translated Spanish Javadoc comment
     */
    public static String translateJavadoc(String javadoc) {
        // Split the Javadoc into lines
        String[] lines = javadoc.split("\n");
        StringBuilder result = new StringBuilder();
        
        for (String line : lines) {
            // Extract the text part (after * if present)
            String prefix = "";
            String textToTranslate = line;
            
            if (line.contains("*")) {
                prefix = line.substring(0, line.indexOf("*") + 1);
                if (line.length() > line.indexOf("*") + 1) {
                    textToTranslate = line.substring(line.indexOf("*") + 1);
                } else {
                    textToTranslate = "";
                }
            }
            
            // Skip translation for empty lines or lines with just tags
            if (textToTranslate.trim().isEmpty() || 
                textToTranslate.trim().startsWith("@") ||
                textToTranslate.trim().startsWith("<") ||
                textToTranslate.trim().startsWith("*/")) {
                result.append(line).append("\n");
                continue;
            }
            
            // Translate the text part
            String translatedText = translateToSpanish(textToTranslate);
            
            // Combine the prefix and translated text
            result.append(prefix).append(translatedText).append("\n");
        }
        
        return result.toString().trim();
    }
}