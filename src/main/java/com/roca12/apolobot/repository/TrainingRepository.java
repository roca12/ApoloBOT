package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface TrainingRepository extends MongoRepository<Training, String> {
    Training findTrainingByDateTime(LocalDateTime dateTime);
}
