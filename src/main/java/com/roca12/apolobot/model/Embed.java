package com.roca12.apolobot.model;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class Embed {
  private String title;
  private String description;
  private User author;
  private ArrayList<String> fields;
  private ArrayList<String> inLineFields;
  private String color;
  private String footer;
  private String image;
  private String thumbnail;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public ArrayList<String> getFields() {
    return fields;
  }

  public void setFields(ArrayList<String> fields) {
    this.fields = fields;
  }

  public ArrayList<String> getInLineFields() {
    return inLineFields;
  }

  public void setInLineFields(ArrayList<String> inLineFields) {
    this.inLineFields = inLineFields;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(String thumbnail) {
    this.thumbnail = thumbnail;
  }

  public void addfield(String data) {
    fields.add(data);
  }

  public void addInlinefield(String data) {
    inLineFields.add(data);
  }

  public EmbedBuilder createAndGetEmbed() {

    EmbedBuilder embed = new EmbedBuilder();
    if (!title.isEmpty()) {
      embed.setTitle(title);
    }

    if (!description.isEmpty()) {
      embed.setDescription(description);
    }

    if (!author.equals(null)) {

      embed.setAuthor(author);
    }

    for (String s : fields) {
      if (!s.isEmpty()) {
        String[] temp = s.split(",");
        embed.addField(temp[0], temp[1]);
      }
    }

    for (String s : inLineFields) {
      if (!s.isEmpty()) {
        String[] temp = s.split(",");
        embed.addInlineField(temp[0], temp[1]);
      }
    }

    if (!color.isEmpty()) {
      Color aux = Color.getColor(color, Color.BLACK);
      embed.setColor(aux);
    }

    if (!footer.isEmpty()) {
      String[] temp = footer.split(",");
      embed.setFooter(temp[0], temp[1]);
    }

    if (!image.isEmpty()) {
      embed.setImage(new File(image));
    }

    if (!thumbnail.isEmpty()) {
      embed.setThumbnail(new File(thumbnail));
    }

    return embed;
  }
}
