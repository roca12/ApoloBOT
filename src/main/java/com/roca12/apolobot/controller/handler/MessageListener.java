package com.roca12.apolobot.controller.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.core.io.ClassPathResource;

/**
 * Responsible for listening to and processing messages in the Discord server. This class primarily
 * handles content moderation by filtering messages that contain inappropriate language (bad words)
 * and removing them.
 *
 * @author roca12
 * @version 2025-I
 */
public class MessageListener implements MessageCreateListener {

  /** Discord API instance */
  private DiscordApi api;

  /** List of words that are not allowed in messages */
  private ArrayList<String> badWords;

  /** File reference for loading bad words */
  private File file;

  /** Scanner for reading files */
  private Scanner sc;

  /** Properties for configuration */
  private Properties prop = new Properties();

  /**
   * Constructor for MessageListener. Initializes the Discord API and loads the list of bad words.
   *
   * @param api The Discord API instance
   */
  public MessageListener(DiscordApi api) {
    this.api = api;
    badWords = new ArrayList<>();
    loadBadWords();
  }

  /**
   * Handles message creation events in Discord. This method is called whenever a new message is
   * sent in a channel the bot can see. It filters out messages from bots and checks if the message
   * contains inappropriate language. If bad words are found, it warns the user and deletes the
   * message.
   *
   * @param event The message creation event
   */
  @Override
  public void onMessageCreate(MessageCreateEvent event) {
    // Ignore messages from bots (including this bot)
    if (event.getMessageAuthor().isBotUser()) {
      return;
    }

    // Get message content and author
    Message message = event.getMessage();
    String msg = message.getContent();
    String autor = message.getAuthor().getDisplayName();

    // Check if the message contains bad words
    if (checkDeleteables(msg)) {
      // Send a warning to the channel
      String alerta = "⚠️ Cuidado con ese vocabulario, " + autor + ".";
      System.out.println(autor + " -> está escribiendo malas palabras.");
      event.getChannel().sendMessage(alerta);

      // Delete the message containing bad words
      message
          .delete()
          .exceptionally(
              e -> {
                System.out.println("No se pudo eliminar el mensaje: " + e.getMessage());
                return null;
              });
    }
  }

  /**
   * Loads the list of bad words from a file. This method reads the badwords.txt file from the
   * resources directory and populates the badWords list with the words found in the file. Each word
   * is converted to lowercase for case-insensitive comparison.
   */
  private void loadBadWords() {
    try {
      // Load the bad words file from resources
      InputStream r = new ClassPathResource("files/badwords.txt").getInputStream();
      sc = new Scanner(r);

      // Read each line and add it to the badWords list
      while (sc.hasNext()) {
        badWords.add(sc.nextLine().toLowerCase());
      }

    } catch (FileNotFoundException e) {
      // Critical error if the bad words file is not found
      System.out.println("File badwords.txt not found");
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      // Handle other IO exceptions
      e.printStackTrace();
    }
  }

  /**
   * Checks if a message contains any bad words. This method splits the message into words and
   * checks each word against the list of bad words. If any word matches, the method returns true,
   * indicating that the message should be deleted.
   *
   * @param msg The message content to check
   * @return true if the message contains bad words, false otherwise
   */
  public boolean checkDeleteables(String msg) {
    // Split the message into individual words
    String[] words = msg.split(" ");

    // Check each word against the bad words list
    for (String word : words) {
      if (badWords.contains(word)) {
        return true; // Bad word found
      } else {
        continue;
      }
    }
    return false; // No bad words found
  }

  /**
   * Gets the list of bad words.
   *
   * @return The ArrayList of bad words
   */
  public ArrayList<String> getBadWords() {
    return badWords;
  }

  /**
   * Sets the list of bad words.
   *
   * @param badWords The ArrayList of bad words to set
   */
  public void setBadWords(ArrayList<String> badWords) {
    this.badWords = badWords;
  }

  /**
   * Gets the properties object.
   *
   * @return The Properties object
   */
  public Properties getProp() {
    return prop;
  }

  /**
   * Sets the properties object.
   *
   * @param prop The Properties object to set
   */
  public void setProp(Properties prop) {
    this.prop = prop;
  }

  /**
   * Gets the Discord API instance.
   *
   * @return The DiscordApi object
   */
  public DiscordApi getApi() {
    return api;
  }

  /**
   * Sets the Discord API instance.
   *
   * @param api The DiscordApi object to set
   */
  public void setApi(DiscordApi api) {
    this.api = api;
  }

  /**
   * Gets the file reference.
   *
   * @return The File object
   */
  public File getFile() {
    return file;
  }

  /**
   * Sets the file reference.
   *
   * @param file The File object to set
   */
  public void setFile(File file) {
    this.file = file;
  }

  /**
   * Gets the scanner object.
   *
   * @return The Scanner object
   */
  public Scanner getSc() {
    return sc;
  }

  /**
   * Sets the scanner object.
   *
   * @param sc The Scanner object to set
   */
  public void setSc(Scanner sc) {
    this.sc = sc;
  }
}
