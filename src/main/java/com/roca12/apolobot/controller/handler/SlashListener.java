package com.roca12.apolobot.controller.handler;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;

import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;

import com.roca12.apolobot.model.Embed;

public class SlashListener {

	private SlashCommandInteraction slashCommandInteraction;

	private Properties prop = new Properties();

	private Embed embed;

	private DiscordApi api;

	public SlashListener(DiscordApi api) {
		this.api = api;
	}

	public void handleSlashComms() {
		api.addSlashCommandCreateListener(event -> {
			slashCommandInteraction = event.getSlashCommandInteraction();

			String command = slashCommandInteraction.getCommandName();
			String author = slashCommandInteraction.getUser().getName();
			System.out.println(author + " -> " + command);

			if (author.equals("roca12")) {

				switch (command) {

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

				case "ayuda": {
					showAyuda();
					break;
				}

				default:
					notExist();
				}
			}else {
				notCoach();
			}
			
		});

	}

	public void showTest() {
		slashCommandInteraction.createImmediateResponder().setContent("Todos los sistemas estan operativos!").respond();
	}

	public void showPing() {
		slashCommandInteraction.createImmediateResponder().setContent("Pong!").respond();
	}

	public void showNumeroUsuarios() {
		slashCommandInteraction.createImmediateResponder().setContent("Click on one of these Buttons!")
				.addComponents(ActionRow.of(Button.success("success", "Send a message"),
						Button.danger("danger", "Delete this message"),
						Button.secondary("secondary", "Remind me after 5 minutes")))
				.respond();
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
