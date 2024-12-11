package com.roca12.apolobot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("training")
public class Training {
    @Id
    private String id;
    @Indexed
    private String coach;
    @Indexed
    private LocalDateTime dateTime;

    public Training(String coach, LocalDateTime dateTime) {
        this.coach = coach;
        this.dateTime = dateTime;
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
}
