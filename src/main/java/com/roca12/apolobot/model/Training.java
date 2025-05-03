package com.roca12.apolobot.model;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a training session in the application.
 * This class is a MongoDB document that stores information about training sessions,
 * including the coach's name, the date and time of the training, the training name,
 * and whether the training has been announced.
 * 
 * @author roca12
 * @version 2025-I
 */
@Document("training")
public class Training {
  /** The name of the coach conducting the training */
  @Indexed private String coach;

  /** The date and time when the training will take place */
  @Indexed private LocalDateTime dateTime;

  /** The name or title of the training session */
  @Indexed private String trainingName;

  /** Flag indicating whether the training has been announced */
  @Indexed private Boolean announced;

  /**
   * Constructor for creating a new Training.
   * Initializes a training session with the given coach, date/time, and name.
   * The announced flag is set to false by default.
   * 
   * @param coach The name of the coach conducting the training
   * @param dateTime The date and time when the training will take place
   * @param trainingName The name or title of the training session
   */
  public Training(String coach, LocalDateTime dateTime, String trainingName) {
    this.coach = coach;
    this.dateTime = dateTime;
    this.trainingName = trainingName;
    this.announced = false;
  }

  /**
   * Gets the name of the coach conducting the training.
   * 
   * @return The coach's name
   */
  public String getCoach() {
    return coach;
  }

  /**
   * Sets the name of the coach conducting the training.
   * 
   * @param coach The coach's name to set
   */
  public void setCoach(String coach) {
    this.coach = coach;
  }

  /**
   * Gets the date and time when the training will take place.
   * 
   * @return The LocalDateTime object representing the training date and time
   */
  public LocalDateTime getDateTime() {
    return dateTime;
  }

  /**
   * Sets the date and time when the training will take place.
   * 
   * @param dateTime The LocalDateTime object to set
   */
  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  /**
   * Gets the name or title of the training session.
   * 
   * @return The training name
   */
  public String getTrainingName() {
    return trainingName;
  }

  /**
   * Sets the name or title of the training session.
   * 
   * @param trainingName The training name to set
   */
  public void setTrainingName(String trainingName) {
    this.trainingName = trainingName;
  }

  /**
   * Checks if the training has been announced.
   * 
   * @return true if the training has been announced, false otherwise
   */
  public Boolean getAnnounced() {
    return announced;
  }

  /**
   * Sets whether the training has been announced.
   * 
   * @param announced true to mark as announced, false otherwise
   */
  public void setAnnounced(Boolean announced) {
    this.announced = announced;
  }
}
