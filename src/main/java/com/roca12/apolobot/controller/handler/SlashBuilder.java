package com.roca12.apolobot.controller.handler;

import java.util.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.*;

/**
 * Responsable de construir y definir comandos de barra diagonal para el bot de Discord.
 * Esta clase crea todas las definiciones de comandos de barra diagonal que se registrarán en Discord.
 * Cada comando se define con un nombre, descripción y parámetros opcionales.
 * 
 * @author roca12
 * @version 2025-I
 */
public class SlashBuilder {

  /** Discord API instance */
  private DiscordApi api;

  /** Set of slash command builders for all commands */
  private Set<SlashCommandBuilder> builders;

  /** Properties for configuration */
  private Properties prop = new Properties();

  /**
   * Constructor para SlashBuilder.
   * Inicializa la API de Discord y el conjunto de constructores de comandos de barra diagonal.
   * 
   * @param api La instancia de la API de Discord
   */
  public SlashBuilder(DiscordApi api) {
    this.api = api;
    builders = new HashSet<>();
  }

  /**
   * Inicializa y define todos los comandos de barra diagonal para el bot de Discord.
   * Este método crea definiciones para comandos como ayuda, ping, test,
   * conteo de usuarios, gestión de entrenamientos, gestión de eventos y traducción.
   * 
   * @return Un conjunto de objetos SlashCommandBuilder que representan todos los comandos
   */
  public Set<SlashCommandBuilder> initSlashCommands() {

    // Help command - Shows a list of all commands
    builders.add(
        new SlashCommandBuilder()
            .setName("ayuda")
            .setDescription("Muestra la lista completa de comandos y quienes pueden usarlo"));

    // Ping command - Simple response to check if bot is online
    builders.add(
        new SlashCommandBuilder().setName("ping").setDescription("Retorna un saludo conocido"));

    // Test command - Checks connectivity of all systems
    builders.add(
        new SlashCommandBuilder()
            .setName("test")
            .setDescription("Verifica la conectividad de todos los sistemas"));

    // User count command - Counts users in the server
    builders.add(
        new SlashCommandBuilder()
            .setName("numerousuarios")
            .setDescription("Cuenta la cantidad de usuarios existentes en el servidor"));

    // Training command - Announce or cancel training sessions
    builders.add(
        new SlashCommandBuilder()
            .setName("entrenamiento")
            .setDescription("Capacidad de anunciar o cancelar clases")
            .addOption(
                new SlashCommandOptionBuilder()
                    .setName("cancelar")
                    .setDescription("Cancela la clase en la fecha especificada")
                    .setType(SlashCommandOptionType.SUB_COMMAND)
                    .addOption(
                        SlashCommandOption.createStringOption(
                            "fecha", "La fecha de la clase en formato dd-MM-yyyy HH:mm", true))
                    .build())
            .addOption(
                new SlashCommandOptionBuilder()
                    .setName("anunciar")
                    .setDescription(
                        "Envía un viso a todos los participantes acerca de una fecha de"
                            + " entrenamiento")
                    .setType(SlashCommandOptionType.SUB_COMMAND)
                    .addOption(
                        SlashCommandOption.createStringOption(
                            "fecha", "La fecha de la clase en formato dd-MM-yyyy HH:mm", true))
                    .addOption(
                        SlashCommandOption.createStringOption(
                            "nombre", "El nombre de la clase", true))
                    .build()));

    // Love command - Fun command to show love for the bot
    builders.add(
        new SlashCommandBuilder()
            .setName("teamo")
            .setDescription("Demuestra tu amor por el bot :D"));

    // Event command - Manage future events with reminders
    builders.add(
        SlashCommand.with(
            "evento",
            "Administra eventos futuros, tendra un recordatorio un dia antes y una hora antes",
            Arrays.asList(
                SlashCommandOption.createWithOptions(
                    SlashCommandOptionType.SUB_COMMAND, "Agregar", "Agrega nuevo evento"),
                SlashCommandOption.createWithOptions(
                    SlashCommandOptionType.SUB_COMMAND, "Eliminar", "Elimina un evento por ID"),
                SlashCommandOption.createWithOptions(
                    SlashCommandOptionType.SUB_COMMAND, "Actualizar", "Actualiza un evento por ID"),
                SlashCommandOption.createWithOptions(
                    SlashCommandOptionType.SUB_COMMAND, "Listar", "Lista todos los eventos"))));

    // Translate command - Translates text to Spanish
    builders.add(
        new SlashCommandBuilder()
            .setName("traducir")
            .setDescription("Traduce un texto desde cualquier idioma al español.")
            .addOption(
                SlashCommandOption.createStringOption(
                    "text", "El texto que deseas traducir.", true)));

    // Translate PDF command - Translates PDF content to Spanish
    builders.add(
        new SlashCommandBuilder()
            .setName("traducirpdf")
            .setDescription("Traduce el texto de un PDF y te devuelve el PDF ya traducido. ")
            .addOption(
                SlashCommandOption.createAttachmentOption(
                    "pdf", "El PDF que va a ser traducido.", true)));

    return builders;
  }

  /**
   * Obtiene el conjunto de constructores de comandos de barra diagonal.
   * 
   * @return El conjunto de objetos SlashCommandBuilder
   */
  public Set<SlashCommandBuilder> getBuilders() {
    return builders;
  }

  /**
   * Establece el conjunto de constructores de comandos de barra diagonal.
   * 
   * @param builders El conjunto de objetos SlashCommandBuilder a establecer
   */
  public void setBuilders(Set<SlashCommandBuilder> builders) {
    this.builders = builders;
  }

  /**
   * Obtiene el objeto de propiedades.
   * 
   * @return El objeto Properties
   */
  public Properties getProp() {
    return prop;
  }

  /**
   * Establece el objeto de propiedades.
   * 
   * @param prop El objeto Properties a establecer
   */
  public void setProp(Properties prop) {
    this.prop = prop;
  }

  /**
   * Obtiene la instancia de la API de Discord.
   * 
   * @return El objeto DiscordApi
   */
  public DiscordApi getApi() {
    return api;
  }

  /**
   * Establece la instancia de la API de Discord.
   * 
   * @param api El objeto DiscordApi a establecer
   */
  public void setApi(DiscordApi api) {
    this.api = api;
  }
}
