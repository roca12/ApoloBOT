package com.roca12.apolobot.model;

import java.util.Date;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase modelo que representa una entidad ReRunApolo. Esta clase está mapeada a la colección
 * "rerunapolo" en MongoDB y almacena información sobre eventos de reinicio del bot.
 *
 * @author roca12
 * @version 2025-I
 */
@Document("rerunapolo")
public class ReRunApolo {

  /** Identificador único para el registro ReRunApolo */
  @Id private String id;

  /** Fecha y hora cuando el bot fue reiniciado */
  @NotBlank private Date reRunDate;

  /** Tipo o razón del reinicio */
  @NotBlank private String reRunKind;

  /**
   * Constructor por defecto. Requerido por Spring Data MongoDB para la instanciación de objetos.
   */
  public ReRunApolo() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Constructor parametrizado para crear un ReRunApolo con todas las propiedades.
   *
   * @param id El identificador único
   * @param reRunDate La fecha y hora del reinicio
   * @param reRunKind El tipo o razón del reinicio
   */
  public ReRunApolo(String id, Date reRunDate, String reRunKind) {
    super();
    this.id = id;
    this.reRunDate = reRunDate;
    this.reRunKind = reRunKind;
  }

  /**
   * Obtiene el identificador único.
   *
   * @return El ID
   */
  public String getId() {
    return id;
  }

  /**
   * Establece el identificador único.
   *
   * @param id El ID a establecer
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Obtiene la fecha y hora del reinicio.
   *
   * @return La fecha de reinicio
   */
  public Date getReRunDate() {
    return reRunDate;
  }

  /**
   * Establece la fecha y hora del reinicio.
   *
   * @param reRunDate La fecha de reinicio a establecer
   */
  public void setReRunDate(Date reRunDate) {
    this.reRunDate = reRunDate;
  }

  /**
   * Obtiene el tipo o razón del reinicio.
   *
   * @return El tipo de reinicio
   */
  public String getReRunKind() {
    return reRunKind;
  }

  /**
   * Establece el tipo o razón del reinicio.
   *
   * @param reRunKind El tipo de reinicio a establecer
   */
  public void setReRunKind(String reRunKind) {
    this.reRunKind = reRunKind;
  }

  /**
   * Devuelve una representación en cadena del objeto ReRunApolo.
   *
   * @return Una cadena que contiene todas las propiedades del objeto
   */
  @Override
  public String toString() {
    return "ReRunApolo [id=" + id + ", reRunDate=" + reRunDate + ", reRunKind=" + reRunKind + "]";
  }
}
