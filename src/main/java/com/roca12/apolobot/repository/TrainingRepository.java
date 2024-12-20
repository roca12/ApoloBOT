package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingRepository extends MongoRepository<Training, String> {
    Training findTrainingByDateTime(LocalDateTime dateTime);

    List<Training> findByDateTimeBetweenAndAnnounced(LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean announced);
}
