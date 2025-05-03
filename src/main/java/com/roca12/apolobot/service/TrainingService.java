package com.roca12.apolobot.service;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.repository.TrainingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Training entities. This class provides business logic for creating,
 * saving, and deleting training sessions. It acts as an intermediary between the controllers and
 * the repository layer.
 *
 * @author roca12
 * @version 2025-I
 */
@Service
public class TrainingService {
  /** Repository for accessing training data */
  private TrainingRepository trainingRepository;

  /**
   * Constructor for TrainingService. Initializes the training repository from the application
   * context.
   */
  public TrainingService() {
    trainingRepository = ApplicationContextHolder.getContext().getBean(TrainingRepository.class);
  }

  /**
   * Saves a training session to the database. This method can be used to create a new training
   * session or update an existing one.
   *
   * @param training The Training entity to save
   */
  public void save(Training training) {
    trainingRepository.save(training);
  }

  /**
   * Deletes training sessions with the specified date and time. This method finds all training
   * sessions with the exact date and time and deletes them from the database.
   *
   * @param localDateTime The date and time of the training sessions to delete
   * @return true if the deletion was successful, false if an error occurred
   */
  public boolean delete(LocalDateTime localDateTime) {
    // Find all trainings with the specified date and time
    List<Training> trainings =
        trainingRepository.findAll().stream()
            .filter(f -> f.getDateTime().equals(localDateTime))
            .toList();

    try {
      // Delete all matching trainings
      trainingRepository.deleteAll(trainings);
    } catch (Exception e) {
      // Return false if an error occurs during deletion
      return false;
    }

    // Return true if deletion was successful
    return true;
  }
}
