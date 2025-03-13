package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.Training;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrainingRepository extends MongoRepository<Training, String> {
  Training findTrainingByDateTime(LocalDateTime dateTime);

  List<Training> findByDateTimeBetweenAndAnnounced(
      LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean announced);
}
