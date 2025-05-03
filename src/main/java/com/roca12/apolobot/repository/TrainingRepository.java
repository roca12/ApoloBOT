package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.Training;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interfaz de repositorio para acceder a entidades de Training en MongoDB.
 * Esta interfaz proporciona métodos para encontrar sesiones de entrenamiento por fecha/hora
 * y para encontrar próximas sesiones de entrenamiento que han sido o no anunciadas.
 * 
 * Extiende MongoRepository para heredar operaciones CRUD estándar.
 * 
 * @author roca12
 * @version 2025-I
 */
public interface TrainingRepository extends MongoRepository<Training, String> {

  /**
   * Encuentra una sesión de entrenamiento por su fecha y hora exactas.
   * 
   * @param dateTime La fecha y hora a buscar
   * @return La entidad Training coincidente, o null si no se encuentra ninguna
   */
  Training findTrainingByDateTime(LocalDateTime dateTime);

  /**
   * Encuentra sesiones de entrenamiento dentro de un rango de fecha/hora con un estado de anuncio específico.
   * Este método se utiliza para encontrar próximas sesiones de entrenamiento que han sido o no anunciadas.
   * 
   * @param startDateTime El inicio del rango de fecha/hora
   * @param endDateTime El fin del rango de fecha/hora
   * @param announced Si se deben encontrar sesiones de entrenamiento anunciadas (true) o no anunciadas (false)
   * @return Una lista de entidades Training coincidentes
   */
  List<Training> findByDateTimeBetweenAndAnnounced(
      LocalDateTime startDateTime, LocalDateTime endDateTime, Boolean announced);
}
