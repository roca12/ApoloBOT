package com.roca12.apolobot.model;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

/**
 * Model class for creating and managing Discord message embeds.
 * This class provides a simplified interface for creating rich embeds
 * that can be sent in Discord messages, with support for titles, descriptions,
 * fields, colors, images, and more.
 * 
 * @author roca12
 * @version 2025-I
 */
public class Embed {
  /** The title of the embed */
  private String title;
  /** The main text content of the embed */
  private String description;
  /** The user displayed as the author of the embed */
  private User author;
  /** List of fields to display in the embed (format: "name,value") */
  private ArrayList<String> fields;
  /** List of inline fields to display in the embed (format: "name,value") */
  private ArrayList<String> inLineFields;
  /** The color of the embed's left border */
  private String color;
  /** The footer text and icon of the embed (format: "text,iconUrl") */
  private String footer;
  /** The main image of the embed */
  private String image;
  /** The small image displayed in the top right of the embed */
  private String thumbnail;

  /**
   * Default constructor that initializes an empty embed.
   * All string fields are initialized to empty strings, lists are initialized as empty ArrayLists,
   * and the author is set to null.
   */
  public Embed() {
    fields = new ArrayList<String>();
    inLineFields = new ArrayList<String>();
    this.title = "";
    this.description = "";
    this.author = null;
    this.color = "";
    this.footer = "";
    this.image = "";
    this.thumbnail = "";
  }

  /**
   * Parameterized constructor that initializes an embed with all properties.
   * 
   * @param title The title of the embed
   * @param description The main text content of the embed
   * @param author The user displayed as the author of the embed
   * @param fields List of fields to display in the embed (format: "name,value")
   * @param inLineFields List of inline fields to display in the embed (format: "name,value")
   * @param color The color of the embed's left border
   * @param footer The footer text and icon of the embed (format: "text,iconUrl")
   * @param image The main image of the embed
   * @param thumbnail The small image displayed in the top right of the embed
   */
  public Embed(
      String title,
      String description,
      User author,
      ArrayList<String> fields,
      ArrayList<String> inLineFields,
      String color,
      String footer,
      String image,
      String thumbnail) {
    super();
    this.title = title;
    this.description = description;
    this.author = author;
    this.fields = fields;
    this.inLineFields = inLineFields;
    this.color = color;
    this.footer = footer;
    this.image = image;
    this.thumbnail = thumbnail;
  }

  /**
   * Gets the title of the embed.
   * 
   * @return The title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the embed.
   * 
   * @param title The title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the embed.
   * 
   * @return The description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the embed.
   * 
   * @param description The description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the author of the embed.
   * 
   * @return The author user
   */
  public User getAuthor() {
    return author;
  }

  /**
   * Sets the author of the embed.
   * 
   * @param author The author user to set
   */
  public void setAuthor(User author) {
    this.author = author;
  }

  /**
   * Gets the list of fields for the embed.
   * 
   * @return The list of fields (format: "name,value")
   */
  public ArrayList<String> getFields() {
    return fields;
  }

  /**
   * Sets the list of fields for the embed.
   * 
   * @param fields The list of fields to set (format: "name,value")
   */
  public void setFields(ArrayList<String> fields) {
    this.fields = fields;
  }

  /**
   * Gets the list of inline fields for the embed.
   * 
   * @return The list of inline fields (format: "name,value")
   */
  public ArrayList<String> getInLineFields() {
    return inLineFields;
  }

  /**
   * Sets the list of inline fields for the embed.
   * 
   * @param inLineFields The list of inline fields to set (format: "name,value")
   */
  public void setInLineFields(ArrayList<String> inLineFields) {
    this.inLineFields = inLineFields;
  }

  /**
   * Gets the color of the embed's left border.
   * 
   * @return The color
   */
  public String getColor() {
    return color;
  }

  /**
   * Sets the color of the embed's left border.
   * 
   * @param color The color to set
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * Gets the footer of the embed.
   * 
   * @return The footer (format: "text,iconUrl")
   */
  public String getFooter() {
    return footer;
  }

  /**
   * Sets the footer of the embed.
   * 
   * @param footer The footer to set (format: "text,iconUrl")
   */
  public void setFooter(String footer) {
    this.footer = footer;
  }

  /**
   * Gets the main image of the embed.
   * 
   * @return The image path
   */
  public String getImage() {
    return image;
  }

  /**
   * Sets the main image of the embed.
   * 
   * @param image The image path to set
   */
  public void setImage(String image) {
    this.image = image;
  }

  /**
   * Gets the thumbnail image of the embed.
   * 
   * @return The thumbnail image path
   */
  public String getThumbnail() {
    return thumbnail;
  }

  /**
   * Sets the thumbnail image of the embed.
   * 
   * @param thumbnail The thumbnail image path to set
   */
  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  /**
   * Adds a field to the embed.
   * 
   * @param data The field data in format "name,value"
   */
  public void addfield(String data) {
    fields.add(data);
  }

  /**
   * Adds an inline field to the embed.
   * 
   * @param data The inline field data in format "name,value"
   */
  public void addInlinefield(String data) {
    inLineFields.add(data);
  }

  /**
   * Creates and returns an EmbedBuilder with all the configured properties.
   * This method constructs a Discord embed using the Javacord EmbedBuilder class,
   * applying all the properties that have been set in this Embed object.
   * 
   * @return The constructed EmbedBuilder object ready to be sent in a Discord message
   */
  public EmbedBuilder createAndGetEmbed() {
    EmbedBuilder embed = new EmbedBuilder();

    // Set title if not empty
    if (!title.isEmpty()) {
      embed.setTitle(title);
    }

    // Set description if not empty
    if (!description.isEmpty()) {
      embed.setDescription(description);
    }

    // Set author if not null
    if (author != null) {
      embed.setAuthor(author);
    }

    // Add all non-empty fields
    for (String s : fields) {
      if (!s.isEmpty()) {
        String[] temp = s.split(",");
        embed.addField(temp[0], temp[1]);
      }
    }

    // Add all non-empty inline fields
    for (String s : inLineFields) {
      if (!s.isEmpty()) {
        String[] temp = s.split(",");
        embed.addInlineField(temp[0], temp[1]);
      }
    }

    // Set color if not empty
    if (!color.isEmpty()) {
      Color aux = Color.getColor(color, Color.BLACK);
      embed.setColor(aux);
    }

    // Set footer if not empty
    if (!footer.isEmpty()) {
      String[] temp = footer.split(",");
      embed.setFooter(temp[0], temp[1]);
    }

    // Set image if not empty
    if (!image.isEmpty()) {
      embed.setImage(new File(image));
    }

    // Set thumbnail if not empty
    if (!thumbnail.isEmpty()) {
      embed.setThumbnail(new File(thumbnail));
    }

    return embed;
  }
}
