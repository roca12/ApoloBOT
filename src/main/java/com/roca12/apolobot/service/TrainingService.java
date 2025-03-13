package com.roca12.apolobot.service;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.repository.TrainingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {
  private TrainingRepository trainingRepository;

  public TrainingService() {
    trainingRepository = ApplicationContextHolder.getContext().getBean(TrainingRepository.class);
  }

  public void save(Training training) {
    trainingRepository.save(training);
  }

  public boolean delete(LocalDateTime localDateTime) {
    List<Training> trainings =
        trainingRepository.findAll().stream()
            .filter(f -> f.getDateTime().equals(localDateTime))
            .toList();

    try {
      trainingRepository.deleteAll(trainings);
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
