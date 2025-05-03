package com.roca12.apolobot.repository;

import com.roca12.apolobot.model.ReRunApolo;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interfaz de repositorio para acceder a entidades ReRunApolo en MongoDB. Esta interfaz proporciona
 * métodos para recuperar registros de reinicio del bot y contar el número total de eventos de
 * reinicio.
 *
 * <p>Extiende MongoRepository para heredar operaciones CRUD estándar.
 *
 * @author roca12
 * @version 2025-I
 */
public interface ReRunApoloRepository extends MongoRepository<ReRunApolo, String> {

  /**
   * Recupera todos los registros de reinicio del bot.
   *
   * @return Un ArrayList que contiene todas las entidades ReRunApolo
   */
  public ArrayList<ReRunApolo> findAll();

  /**
   * Encuentra un registro específico de reinicio del bot por su identificador único.
   *
   * @param id El identificador único del registro de reinicio
   * @return Un Optional que contiene la entidad ReRunApolo coincidente, o vacío si no se encuentra
   *     ninguna
   */
  public Optional<ReRunApolo> findById(String id);

  /**
   * Cuenta el número total de registros de reinicio del bot.
   *
   * @return El recuento total de entidades ReRunApolo
   */
  public long count();
}
