package com.roca12.apolobot.model;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("training")
public class Training {
  @Indexed private String coach;
  @Indexed private LocalDateTime dateTime;
  @Indexed private String trainingName;
  @Indexed private Boolean announced;

  public Training(String coach, LocalDateTime dateTime, String trainingName) {
    this.coach = coach;
    this.dateTime = dateTime;
    this.trainingName = trainingName;
    this.announced = false;
  }

  public String getCoach() {
    return coach;
  }

  public void setCoach(String coach) {
    this.coach = coach;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getTrainingName() {
    return trainingName;
  }

  public void setTrainingName(String trainingName) {
    this.trainingName = trainingName;
  }

  public Boolean getAnnounced() {
    return announced;
  }

  public void setAnnounced(Boolean announced) {
    this.announced = announced;
  }
}
