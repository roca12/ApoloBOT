package com.roca12.apolobot;

import com.roca12.apolobot.controller.AplMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Clase principal de la aplicación para el bot de Discord ApoloBOT.
 * Esta clase sirve como punto de entrada para la aplicación Spring Boot.
 * Inicializa el contexto de Spring y arranca el bot de Discord.
 * 
 * La aplicación utiliza MongoDB para la persistencia de datos, que se habilita
 * a través de la anotación @EnableMongoRepositories.
 * 
 * @author roca12
 * @version 2025-I
 */
@SpringBootApplication
@EnableMongoRepositories
public class ApolobotApplication {

  /**
   * Método principal que inicia la aplicación Spring Boot e inicializa el bot de Discord.
   * 
   * @param args Argumentos de línea de comandos pasados a la aplicación
   */
  public static void main(String[] args) {
    // Initialize the Spring application context
    SpringApplication.run(ApolobotApplication.class, args);

    // Create and run the main controller for the Discord bot
    AplMain am = new AplMain();
    am.run();
  }
}
