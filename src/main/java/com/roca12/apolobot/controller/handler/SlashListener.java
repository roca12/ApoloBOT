package com.roca12.apolobot.controller.handler;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.service.TrainingService;
import com.roca12.apolobot.util.ILoveResponses;
import org.javacord.api.DiscordApi;

import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import com.roca12.apolobot.model.Embed;
import com.roca12.apolobot.service.ReRunApoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

public class SlashListener {

    private TrainingService trainingService;

    private SlashCommandInteraction slashCommandInteraction;

    private Properties prop = new Properties();

    private Embed embed;

    private DiscordApi api;

    public SlashListener(DiscordApi api) {
        this.api = api;
        trainingService = new TrainingService();
    }

    public void handleSlashComms() {
        api.addSlashCommandCreateListener(event -> {
            slashCommandInteraction = event.getSlashCommandInteraction();

            String command = slashCommandInteraction.getCommandName();
            String author = slashCommandInteraction.getUser().getName();
            System.out.println(author + " -> " + command);

            //if (author.equals("roca12")) {

            switch (command) {

                case "ayuda": {
                    showAyuda();
                    break;
                }

                case "ping": {
                    showPing();
                    break;
                }
                case "test": {
                    showTest();
                    break;
                }

                case "numerousuarios": {
                    showNumeroUsuarios();
                    break;
                }

                case "entrenamiento": {
                    showTraining();
                    break;
                }

                case "evento": {
                    notImplementedYet();
                    break;
                }

                case "teamo": {
                    iLoveYou();
                    break;
                }

                default:
                    notExist();
            }
//			} else {
//				notCoach();
//			}

        });

    }

    public void showTest() {
        ReRunApoloService rraDao = new ReRunApoloService();
        try {
            rraDao.createNewReRunByTest();
            slashCommandInteraction.createImmediateResponder().setContent("Instancia DB corriendo y recibiendo solicitudes").
                    appendNewLine().append("Todos los sistemas estan operativos!").respond();
        } catch (Exception e) {
            slashCommandInteraction.createImmediateResponder().setContent(e.getMessage()).respond();
            slashCommandInteraction.createImmediateResponder().setContent("Algun sistema esta fallando").respond();
        }
    }

    public void showPing() {
        slashCommandInteraction.createImmediateResponder().setContent("Pong!").respond();
    }

    public void iLoveYou() {
        slashCommandInteraction.createImmediateResponder().setContent(ILoveResponses.getRandomPhrase()).respond();
    }

    public void showTraining() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH");

        String subCommandName = slashCommandInteraction.getOptionByName("anunciar").map(option -> "anunciar")
                .or(() -> slashCommandInteraction.getOptionByName("cancelar").map(option -> "cancelar"))
                .orElse("");

        var fecha = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");
        LocalDateTime dateTime;
        if (fecha.equalsIgnoreCase("mañana")) {
            dateTime = LocalDateTime.now().plusDays(1);
            dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
        } else if (fecha.equalsIgnoreCase("pasado")) {
            dateTime = LocalDateTime.now().plusDays(2);
            dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
        } else if (fecha.equalsIgnoreCase("semana")) {
            dateTime = LocalDateTime.now().plusDays(7);
            dateTime = dateTime.truncatedTo(ChronoUnit.HOURS);
        } else {
            try {
                dateTime = LocalDateTime.parse(fecha, formatter);
            } catch (Exception e) {
                slashCommandInteraction.createImmediateResponder()
                        .setContent("¡Recuerda que el formato es dd-MM-yyy HH!")
                        .respond();
                return;
            }
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            slashCommandInteraction.createImmediateResponder()
                    .setContent("La fecha no puede ser anterior al momento actual, prueba a revisar la fecha que mencionaste ;)")
                    .respond();
            return;
        }

        StringBuilder rolesMentions = new StringBuilder();
        String[] roleNames = {"Avanzada", "Intermedia", "Básica", "Aprendiz", "admin", "Student UEB"};

        for (String role : roleNames)
            slashCommandInteraction.getServer().get()
                    .getRolesByName(role).stream().findFirst()
                    .ifPresent(
                            r -> rolesMentions.append(r.getMentionTag()).append(" ")
                    );

        switch (subCommandName) {
            case "anunciar":
                var trainingName = slashCommandInteraction.getArguments().get(1).getStringValue().orElse("Generic Training");
                trainingService.save(new Training(slashCommandInteraction.getUser().getName(), dateTime, trainingName));
                System.out.println("Entrenamiento creado por: " + slashCommandInteraction.getUser().getName() + " a las " + dateTime);
                slashCommandInteraction.createImmediateResponder()
                        .setContent("¡Entrenamiento anunciado para el " + dateTime + "!\n" + rolesMentions)
                        .setAllowedMentions(
                                new AllowedMentionsBuilder().setMentionRoles(true).build()
                        )
                        .respond();
                break;

            case "cancelar":
                if (trainingService.delete(dateTime))
                    System.err.println("El entrenamiento de las " + dateTime + " borrado");
                else
                    System.err.println("No se pudo eliminar");
                slashCommandInteraction.createImmediateResponder()
                        .setContent("El entrenamiento programado para el " + dateTime + " ha sido cancelado.")
                        .respond();
                break;

            default:
                slashCommandInteraction.createImmediateResponder()
                        .setContent("Comando no reconocido.")
                        .respond();
        }
    }

    public void showNumeroUsuarios() {
        Set<Server> servers = api.getServers();
        for (Server s : servers) {
            String out = s.getName() + " -> " + s.getMemberCount() + " users.";
            slashCommandInteraction.createImmediateResponder().setContent(out).respond();
        }
    }

    public void showAyuda() {

        embed = new Embed();
        embed.setTitle("Ayuda Apolo Bot (Admin)");
        embed.setDescription("Listado de comandos activos en el bot Apolo (solo pueden usarlos los coaches)");
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
        slashCommandInteraction.createImmediateResponder().addEmbed(embed.createAndGetEmbed()).respond();

    }

    public void notExist() {
        slashCommandInteraction.createImmediateResponder().setContent("Comando no valido")
                .setFlags(MessageFlag.EPHEMERAL).respond();
    }

    public void notCoach() {
        slashCommandInteraction.createImmediateResponder().setContent("No tienes permisos de usar este comando")
                .setFlags(MessageFlag.EPHEMERAL).respond();
    }

    public void notImplementedYet() {
        slashCommandInteraction.createImmediateResponder().setContent("Este comando no ha sido implementando aun :(")
                .addComponents(ActionRow.of(Button.success("success", "Send a message"),
                        Button.danger("danger", "Delete this message"),
                        Button.secondary("secondary", "Remind me after 5 minutes")))
                .respond();
    }

    public void showButtons() {
        slashCommandInteraction.createImmediateResponder().setContent("Click on one of these Buttons!")
                .addComponents(ActionRow.of(Button.success("success", "Send a message"),
                        Button.danger("danger", "Delete this message"),
                        Button.secondary("secondary", "Remind me after 5 minutes")))
                .respond();
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public SlashCommandInteraction getSlashCommandInteraction() {
        return slashCommandInteraction;
    }

    public void setSlashCommandInteraction(SlashCommandInteraction slashCommandInteraction) {
        this.slashCommandInteraction = slashCommandInteraction;
    }

    public Embed getEmbed() {
        return embed;
    }

    public void setEmbed(Embed embed) {
        this.embed = embed;
    }

    public DiscordApi getApi() {
        return api;
    }

    public void setApi(DiscordApi api) {
        this.api = api;
    }

}
