package com.roca12.apolobot.controller.handler;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.roca12.apolobot.model.Embed;
import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.service.ReRunApoloService;
import com.roca12.apolobot.service.TrainingService;
import com.roca12.apolobot.util.ILoveResponses;
import com.roca12.apolobot.util.PDFProcessor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.springframework.core.io.ClassPathResource;

/**
 * Responsible for handling and responding to slash commands in the Discord bot.
 * This class listens for slash command interactions and executes the appropriate
 * action based on the command received. It handles commands for help, ping, test,
 * user count, training management, event management, and translation.
 * 
 * @author roca12
 * @version 2025-I
 */
public class SlashListener {

  /** Service for managing training sessions */
  private TrainingService trainingService;

  /** Current slash command interaction being processed */
  private SlashCommandInteraction slashCommandInteraction;

  /** Properties for configuration */
  private Properties prop = new Properties();

  /** Embed message builder for rich content */
  private Embed embed;

  /** Discord API instance */
  private DiscordApi api;

  /** API key for Google Translate service */
  private String traductorApiKey;

  /** List of words that are not allowed in messages */
  private ArrayList<String> badWords;

  /** Scanner for reading files */
  private Scanner sc;

  /** Current version of the bot */
  private String BOT_VERSION = "2025-I";

  /**
   * Constructor for SlashListener.
   * Initializes the Discord API, training service, and loads the list of bad words.
   * 
   * @param api The Discord API instance
   */
  public SlashListener(DiscordApi api) {
    this.api = api;
    trainingService = new TrainingService();
    badWords = new ArrayList<>();
    loadBadWords();
  }

  /**
   * Sets up the listener for slash command interactions.
   * This method registers a listener that processes all slash commands
   * and routes them to the appropriate handler method based on the command name.
   */
  public void handleSlashComms() {
    api.addSlashCommandCreateListener(
        event -> {
          // Get the slash command interaction from the event
          slashCommandInteraction = event.getSlashCommandInteraction();

          // Extract command name and author for logging
          String command = slashCommandInteraction.getCommandName();
          String author = slashCommandInteraction.getUser().getName();
          System.out.println(author + " -> " + command);

          // Permission check (currently commented out)
          // if (author.equals("roca12")) {

          // Route the command to the appropriate handler method
          switch (command) {
            case "ayuda":
              {
                showAyuda();
                break;
              }

            case "ping":
              {
                showPing();
                break;
              }
            case "test":
              {
                showTest();
                break;
              }

            case "numerousuarios":
              {
                showNumeroUsuarios();
                break;
              }

            case "entrenamiento":
              {
                showTraining();
                break;
              }

            case "evento":
              {
                notImplementedYet();
                break;
              }

            case "teamo":
              {
                iLoveYou();
                break;
              }

            case "traducir":
              {
                traducir();
                break;
              }

            case "traducirpdf":
              {
                processPDF();
                break;
              }

            default:
              notExist();
          }
          // Permission check else block (currently commented out)
          //			} else {
          //				notCoach();
          //			}
        });
  }

  /**
   * Handles the "traducir" command to translate text to Spanish.
   * This method takes the text provided by the user, checks it for inappropriate content,
   * and then uses the Google Translate API to translate it to Spanish.
   * The translated text is then sent back to the user.
   */
  // TODO: probar variables de entorno
  @SuppressWarnings("deprecation")
  public void traducir() {
    // Get the text to translate from the command arguments
    String texto = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");

    // Check if the text contains inappropriate content
    if (checkDeleteables(texto)) {
      slashCommandInteraction.createImmediateResponder().setContent("¡**").respond();
      return;
    }

    // Initialize the Google Translate service with the API key
    Translate translate =
        TranslateOptions.newBuilder().setApiKey(traductorApiKey).build().getService();

    // Set the target language to Spanish
    String targetLanguage = "es";

    // Perform the translation
    Translation translation =
        translate.translate(texto, Translate.TranslateOption.targetLanguage(targetLanguage));
    String traduccion = translation.getTranslatedText();

    // Send the translated text back to the user
    slashCommandInteraction
        .createImmediateResponder()
        .setContent(translation.getTranslatedText())
        .respond();
  }

  /**
   * Handles the "test" command to verify system functionality.
   * This method tests the critical systems of the bot, including the database connection,
   * and reports the status back to the user.
   */
  public void showTest() {
    ReRunApoloService rraDao = new ReRunApoloService();
    try {
      // Test database connection by creating a new record
      rraDao.createNewReRunByTest();

      // Send success message with system status
      slashCommandInteraction
          .createImmediateResponder()
          .setContent("Verificando sistemas criticos de APOLO")
          .appendNewLine()
          .append("Versión de bot: " + BOT_VERSION)
          .appendNewLine()
          .append("Instancia DB corriendo y recibiendo solicitudes")
          .appendNewLine()
          .append("Todos los sistemas estan operativos!")
          .respond();

    } catch (Exception e) {
      // Send error message if any system is failing
      slashCommandInteraction.createImmediateResponder().setContent(e.getMessage()).respond();
      slashCommandInteraction
          .createImmediateResponder()
          .setContent("Algun sistema esta fallando")
          .respond();
    }
  }

  /**
   * Handles the "traducirpdf" command to translate PDF content to Spanish.
   * This method takes a PDF file uploaded by the user, extracts its text content,
   * translates it to Spanish using the Google Translate API, and then returns
   * the translated text either directly or as a text file attachment if it's too long.
   */
  public void processPDF() {
    // Get the PDF attachment from the command options
    Optional<SlashCommandInteractionOption> pdfOption =
        slashCommandInteraction.getOptionByName("pdf");

    // Defer the response to allow time for processing
    slashCommandInteraction.respondLater();

    if (pdfOption.isPresent() && pdfOption.get().getAttachmentValue().isPresent()) {
      Attachment pdfAttachment = pdfOption.get().getAttachmentValue().get();

      // Validate that the file is a PDF
      if (!pdfAttachment.getFileName().endsWith(".pdf")) {
        slashCommandInteraction
            .createFollowupMessageBuilder()
            .setContent(
                "EL archivo proporcionado no es un PDF 😡. Por favor, sube un archivo válido.")
            .send();
        return;
      }

      try {
        // Get the PDF file as a byte array
        byte[] archivo = pdfAttachment.asByteArray().get();

        // Initialize the Google Translate service
        Translate translate =
            TranslateOptions.newBuilder().setApiKey(traductorApiKey).build().getService();

        // Process the PDF and translate its content
        String translatedFile = PDFProcessor.processPDF(archivo, translate);
        System.out.println("PDF procesado\n" + translatedFile);

        // If the translated text is too long, send it as a file attachment
        if (translatedFile.length() > 2000) {
          slashCommandInteraction
              .createFollowupMessageBuilder()
              .setContent("Traducción de texto largo:\n")
              .addAttachment(PDFProcessor.generateTXT(translatedFile))
              .send();
        } else {
          // Otherwise, send it directly in the message
          slashCommandInteraction
              .createFollowupMessageBuilder()
              .setContent("Traducción: \n" + translatedFile)
              .send();
        }
      } catch (Exception e) {
        // Handle errors in PDF processing
        slashCommandInteraction
            .createFollowupMessageBuilder()
            .setContent("Error al procesar PDF.")
            .send();
        e.printStackTrace();
      }
    }
  }

  /**
   * Handles the "ping" command.
   * This method responds with a simple "Pong!" message to verify that the bot is responsive.
   */
  public void showPing() {
    slashCommandInteraction.createImmediateResponder().setContent("Pong!").respond();
  }

  /**
   * Handles the "teamo" command.
   * This method responds with a random love-related phrase from the ILoveResponses utility.
   */
  public void iLoveYou() {
    slashCommandInteraction
        .createImmediateResponder()
        .setContent(ILoveResponses.getRandomPhrase())
        .respond();
  }

  /**
   * Handles the "entrenamiento" command for managing training sessions.
   * This method processes both announcing new training sessions and canceling existing ones.
   * It validates the date and time format, ensures the date is in the future,
   * and performs the appropriate action based on the subcommand (anunciar or cancelar).
   */
  public void showTraining() {
    // Define the date-time formatter for parsing user input
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH");

    // Determine which subcommand was used (anunciar or cancelar)
    String subCommandName =
        slashCommandInteraction
            .getOptionByName("anunciar")
            .map(option -> "anunciar")
            .or(() -> slashCommandInteraction.getOptionByName("cancelar").map(option -> "cancelar"))
            .orElse("");

    // Get the date from the command arguments
    var fecha = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");
    LocalDateTime dateTime;

    // Parse special date keywords or the actual date format
    if (fecha.equalsIgnoreCase("mañana")) {
      // Tomorrow at the current hour
      dateTime = LocalDateTime.now().plusDays(1);
      dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
    } else if (fecha.equalsIgnoreCase("pasado")) {
      // Day after tomorrow at the current hour
      dateTime = LocalDateTime.now().plusDays(2);
      dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
    } else if (fecha.equalsIgnoreCase("semana")) {
      // One week from now at the current hour
      dateTime = LocalDateTime.now().plusDays(7);
      dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
    } else {
      // Parse the date-time string using the formatter
      try {
        dateTime = LocalDateTime.parse(fecha, formatter);
      } catch (Exception e) {
        // Handle invalid date format
        slashCommandInteraction
            .createImmediateResponder()
            .setContent("¡Recuerda que el formato es dd-MM-yyy HH!")
            .respond();
        return;
      }
    }

    // Ensure the date is in the future
    if (dateTime.isBefore(LocalDateTime.now())) {
      slashCommandInteraction
          .createImmediateResponder()
          .setContent(
              "La fecha no puede ser anterior al momento actual, prueba a revisar la fecha que"
                  + " mencionaste ;)")
          .respond();
      return;
    }

    // Build mentions for all relevant roles
    StringBuilder rolesMentions = new StringBuilder();
    String[] roleNames = {"Avanzada", "Intermedia", "Básica", "Aprendiz", "admin", "Student UEB"};

    for (String role : roleNames)
      slashCommandInteraction.getServer().get().getRolesByName(role).stream()
          .findFirst()
          .ifPresent(r -> rolesMentions.append(r.getMentionTag()).append(" "));

    // Process the appropriate subcommand
    switch (subCommandName) {
      case "anunciar":
        // Get the training name from the command arguments
        var trainingName =
            slashCommandInteraction
                .getArguments()
                .get(1)
                .getStringValue()
                .orElse("Generic Training");

        // Save the new training session
        trainingService.save(
            new Training(slashCommandInteraction.getUser().getName(), dateTime, trainingName));

        // Log the creation
        System.out.println(
            "Entrenamiento creado por: "
                + slashCommandInteraction.getUser().getName()
                + " a las "
                + dateTime);

        // Respond to the user and mention relevant roles
        slashCommandInteraction
            .createImmediateResponder()
            .setContent("¡Entrenamiento anunciado para el " + dateTime + "!\n" + rolesMentions)
            .setAllowedMentions(new AllowedMentionsBuilder().setMentionRoles(true).build())
            .respond();
        break;

      case "cancelar":
        // Delete the training session
        if (trainingService.delete(dateTime))
          System.err.println("El entrenamiento de las " + dateTime + " borrado");
        else System.err.println("No se pudo eliminar");

        // Respond to the user
        slashCommandInteraction
            .createImmediateResponder()
            .setContent("El entrenamiento programado para el " + dateTime + " ha sido cancelado.")
            .respond();
        break;

      default:
        // Handle unrecognized subcommand
        slashCommandInteraction
            .createImmediateResponder()
            .setContent("Comando no reconocido.")
            .respond();
    }
  }

  /**
   * Handles the "numerousuarios" command.
   * This method counts and displays the number of users in each server
   * that the bot is a member of.
   */
  public void showNumeroUsuarios() {
    Set<Server> servers = api.getServers();
    for (Server s : servers) {
      String out = s.getName() + " -> " + s.getMemberCount() + " users.";
      slashCommandInteraction.createImmediateResponder().setContent(out).respond();
    }
  }

  /**
   * Handles the "ayuda" command.
   * This method creates and displays an embed message containing a list
   * of all available slash commands with their descriptions.
   */
  public void showAyuda() {
    embed = new Embed();
    embed.setTitle("Ayuda Apolo Bot (Admin)");
    embed.setDescription(
        "Listado de comandos activos en el bot Apolo (solo pueden usarlos los coaches)");
    embed.setAuthor(api.getYourself());
    embed.setColor("RED");
    // falta imagen icono
    Set<SlashCommand> list;
    try {
      list = api.getGlobalSlashCommands().get();
      for (SlashCommand s : list) {
        embed.addfield(s.getName() + "," + s.getDescription());
      }
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Error on ayuda embed");
      e.printStackTrace();
    }
    slashCommandInteraction
        .createImmediateResponder()
        .addEmbed(embed.createAndGetEmbed())
        .respond();
  }

  /**
   * Responds to an invalid command.
   * This method sends an ephemeral message (only visible to the command user)
   * indicating that the command is not valid.
   */
  public void notExist() {
    slashCommandInteraction
        .createImmediateResponder()
        .setContent("Comando no valido")
        .setFlags(MessageFlag.EPHEMERAL)
        .respond();
  }

  /**
   * Responds when a user without coach permissions tries to use a restricted command.
   * This method sends an ephemeral message (only visible to the command user)
   * indicating that they don't have permission to use the command.
   */
  public void notCoach() {
    slashCommandInteraction
        .createImmediateResponder()
        .setContent("No tienes permisos de usar este comando")
        .setFlags(MessageFlag.EPHEMERAL)
        .respond();
  }

  /**
   * Responds to a command that is not yet implemented.
   * This method sends a message with buttons indicating that the
   * requested feature is not yet available.
   */
  public void notImplementedYet() {
    slashCommandInteraction
        .createImmediateResponder()
        .setContent("Este comando no ha sido implementando aun :(")
        .addComponents(
            ActionRow.of(
                Button.success("success", "Send a message"),
                Button.danger("danger", "Delete this message"),
                Button.secondary("secondary", "Remind me after 5 minutes")))
        .respond();
  }

  /**
   * Displays a message with interactive buttons.
   * This method sends a message with three different types of buttons
   * that the user can interact with.
   */
  public void showButtons() {
    slashCommandInteraction
        .createImmediateResponder()
        .setContent("Click on one of these Buttons!")
        .addComponents(
            ActionRow.of(
                Button.success("success", "Send a message"),
                Button.danger("danger", "Delete this message"),
                Button.secondary("secondary", "Remind me after 5 minutes")))
        .respond();
  }

  /**
   * Loads the list of bad words from a file.
   * This method reads the badwords.txt file from the resources directory
   * and populates the badWords list with the words found in the file.
   * Each word is converted to lowercase for case-insensitive comparison.
   */
  private void loadBadWords() {
    try {
      InputStream r = new ClassPathResource("files/badwords.txt").getInputStream();
      sc = new Scanner(r);
      while (sc.hasNext()) {
        badWords.add(sc.nextLine().toLowerCase());
      }

    } catch (FileNotFoundException e) {
      System.out.println("File badwords.txt not found");
      e.printStackTrace();
      System.exit(0);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Checks if a message contains any bad words.
   * This method splits the message into words and checks each word
   * against the list of bad words.
   * 
   * @param msg The message content to check
   * @return true if the message contains bad words, false otherwise
   */
  public boolean checkDeleteables(String msg) {
    String[] words = msg.split(" ");
    for (String word : words) {
      if (badWords.contains(word)) {
        return true;
      } else {
        continue;
      }
    }
    return false;
  }

  /**
   * Gets the properties object.
   * 
   * @return The Properties object
   */
  public Properties getProp() {
    return prop;
  }

  /**
   * Sets the properties object.
   * 
   * @param prop The Properties object to set
   */
  public void setProp(Properties prop) {
    this.prop = prop;
  }

  /**
   * Gets the current slash command interaction.
   * 
   * @return The SlashCommandInteraction object
   */
  public SlashCommandInteraction getSlashCommandInteraction() {
    return slashCommandInteraction;
  }

  /**
   * Sets the current slash command interaction.
   * 
   * @param slashCommandInteraction The SlashCommandInteraction object to set
   */
  public void setSlashCommandInteraction(SlashCommandInteraction slashCommandInteraction) {
    this.slashCommandInteraction = slashCommandInteraction;
  }

  /**
   * Gets the embed message builder.
   * 
   * @return The Embed object
   */
  public Embed getEmbed() {
    return embed;
  }

  /**
   * Sets the embed message builder.
   * 
   * @param embed The Embed object to set
   */
  public void setEmbed(Embed embed) {
    this.embed = embed;
  }

  /**
   * Gets the Discord API instance.
   * 
   * @return The DiscordApi object
   */
  public DiscordApi getApi() {
    return api;
  }

  /**
   * Sets the Discord API instance.
   * 
   * @param api The DiscordApi object to set
   */
  public void setApi(DiscordApi api) {
    this.api = api;
  }

  /**
   * Gets the Google Translate API key.
   * 
   * @return The API key string
   */
  public String getTraductorApiKey() {
    return traductorApiKey;
  }

  /**
   * Sets the Google Translate API key.
   * 
   * @param traductorApiKey The API key string to set
   */
  public void setTraductorApiKey(String traductorApiKey) {
    this.traductorApiKey = traductorApiKey;
  }
}
