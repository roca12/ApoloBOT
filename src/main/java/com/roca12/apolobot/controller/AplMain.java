package com.roca12.apolobot.controller;

import com.roca12.apolobot.controller.handler.*;
import com.roca12.apolobot.service.ReRunApoloService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.core.io.ClassPathResource;

/**
 * Clase controladora principal para el bot de Discord ApoloBOT.
 * Esta clase es responsable de inicializar y configurar la API de Discord,
 * configurar los manejadores de comandos, los oyentes de mensajes y las tareas programadas.
 * 
 * El bot puede ejecutarse en modo de producción o desarrollo, controlado por la
 * bandera PRODUCTION_STATE. En modo de producción, utiliza el token de producción,
 * mientras que en modo de desarrollo, utiliza el token de prueba.
 * 
 * @author roca12
 * @version 2025-I
 */
public class AplMain {

  /** Flag to determine if the bot is running in production mode */
  private final boolean PRODUCTION_STATE = false;

  /** Discord API token */
  private String token;

  /** Discord API instance */
  private DiscordApi api;

  /** Handler for building slash commands */
  private SlashBuilder sb;

  /** Handler for processing slash commands */
  private SlashListener sl;

  /** Handler for processing messages */
  private MessageListener ml;

  /** Handler for sending scheduled lesson messages */
  private LessonMessageSender ms;

  /** Properties for configuration */
  private Properties prop = new Properties();

  /** Service for ReRunApolo operations */
  private ReRunApoloService rraDao;

  /**
   * Constructor para AplMain.
   * Inicializa el ReRunApoloService.
   */
  public AplMain() {
    rraDao = new ReRunApoloService();
  }

  /**
   * Método principal para ejecutar el bot de Discord.
   * Este método inicializa todos los componentes necesarios, configura los manejadores de comandos,
   * los oyentes de mensajes y las tareas programadas.
   */
  public void run() {
    // Load configuration and initialize critical components
    loadAndCheckCriticals();
    System.out.println("Starting Apolo bot");
    try {
      if (checkReqs()) {
        // Print the bot invite URL
        System.out.println("Invite the bot with the following url: " + api.createBotInvite());

        // Register slash commands globally
        api.bulkOverwriteGlobalApplicationCommands(sb.initSlashCommands()).join();

        // Print bot information
        printInfo();

        // Add message listener
        api.addListener(ml);

        // Set up slash command handler
        sl.handleSlashComms();

        // Set up scheduled lesson messages
        ms.setTimer();

      } else {
        System.out.println("something is broken, check reqs");
        System.exit(0);
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  /**
   * Carga la configuración desde el archivo de propiedades e inicializa los componentes críticos.
   * Este método carga el token de Discord basado en el estado de producción,
   * inicializa la API de Discord y configura todos los manejadores.
   * 
   * @return true si la inicialización fue exitosa, false en caso contrario
   */
  private boolean loadAndCheckCriticals() {
    // System.out.println(System.getProperty("user.dir"));
    try {
      // Load properties from the main.properties file
      InputStream r = new ClassPathResource("files/main.properties").getInputStream();
      prop.load(r);
      // System.out.println("token " + prop.getProperty("apolo.test.token"));

      // Select the appropriate token based on production state
      if (PRODUCTION_STATE) {
        System.out.println("Running in prod mode");
        token = prop.getProperty("apolo.prod.token");
      } else {
        System.out.println("Running in dev test mode");
        token = prop.getProperty("apolo.test.token");
      }

      // Initialize the Discord API with the token
      api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();

      // Initialize all handlers
      sb = new SlashBuilder(api);
      sl = new SlashListener(api);
      sl.setTraductorApiKey(prop.getProperty("apolo.traductor.apikey"));
      ml = new MessageListener(api);
      ms = new LessonMessageSender(api, PRODUCTION_STATE);

      // Test MongoDB connection
      testMongoDB();

      return true;

    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("main.properties error");
      return false;
    }
  }

  /**
   * Imprime información sobre el bot de Discord.
   * Actualmente, este método solo imprime el ID del cliente.
   */
  private void printInfo() {
    System.out.println("Client:" + api.getClientId());
  }

  /**
   * Verifica si se cumplen todos los requisitos para que el bot se ejecute.
   * Actualmente, este método solo verifica si el token no está en blanco.
   * 
   * @return true si se cumplen todos los requisitos, false en caso contrario
   */
  private boolean checkReqs() {
    if (token.isBlank()) {
      System.err.println("Discord token error");
      return false;
    } else {
      return true;
    }
  }

  /**
   * Prueba la conexión de MongoDB creando un nuevo registro ReRunApolo.
   * Este método se llama durante la inicialización para asegurar que la
   * conexión de MongoDB esté funcionando correctamente.
   */
  private void testMongoDB() {
    rraDao.createNewReRun();
  }
}
