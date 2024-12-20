package com.roca12.apolobot.controller.handler;

import java.util.*;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.*;

public class SlashBuilder {

    private DiscordApi api;

    private Set<SlashCommandBuilder> builders;

    private Properties prop = new Properties();

    public SlashBuilder(DiscordApi api) {
        this.api = api;
        builders = new HashSet<>();
    }

    public Set<SlashCommandBuilder> initSlashCommands() {

        builders.add(new SlashCommandBuilder().setName("ayuda")
                .setDescription("Muestra la lista completa de comandos y quienes pueden usarlo"));

        builders.add(new SlashCommandBuilder().setName("ping").setDescription("Retorna un saludo conocido"));

        builders.add(new SlashCommandBuilder().setName("test")
                .setDescription("Verifica la conectividad de todos los sistemas"));

        builders.add(new SlashCommandBuilder().setName("numerousuarios")
                .setDescription("Cuenta la cantidad de usuarios existentes en el servidor"));

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
                                                SlashCommandOption.createStringOption("fecha", "La fecha de la clase en formato dd-MM-yyyy HH:mm", true)
                                        )
                                        .build()
                        )
                        .addOption(
                                new SlashCommandOptionBuilder()
                                        .setName("anunciar")
                                        .setDescription("Envía un viso a todos los participantes acerca de una fecha de entrenamiento")
                                        .setType(SlashCommandOptionType.SUB_COMMAND)
                                        .addOption(
                                                SlashCommandOption.createStringOption("fecha", "La fecha de la clase en formato dd-MM-yyyy HH:mm", true)
                                        )
                                        .addOption(
                                                SlashCommandOption.createStringOption("nombre", "El nombre de la clase", true)
                                        )
                                        .build()
                        )
        );

        builders.add(
                new SlashCommandBuilder().setName("teamo")
                        .setDescription("Demuestra tu amor por el bot :D")
        );

        builders.add(SlashCommand.with("evento",
                "Administra eventos futuros, tendra un recordatorio un dia antes y una hora antes",
                Arrays.asList(
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Agregar",
                                "Agrega nuevo evento"),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Eliminar",
                                "Elimina un evento por ID"),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Actualizar",
                                "Actualiza un evento por ID"),
                        SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND, "Listar",
                                "Lista todos los eventos"))));

        return builders;
    }

    public Set<SlashCommandBuilder> getBuilders() {
        return builders;
    }

    public void setBuilders(Set<SlashCommandBuilder> builders) {
        this.builders = builders;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public DiscordApi getApi() {
        return api;
    }

    public void setApi(DiscordApi api) {
        this.api = api;
    }
}
