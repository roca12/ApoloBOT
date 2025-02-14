package com.roca12.apolobot.controller.handler;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
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

import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.service.TrainingService;
import com.roca12.apolobot.util.ILoveResponses;
import com.roca12.apolobot.util.PDFProcessor;

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

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import com.roca12.apolobot.model.Embed;
import com.roca12.apolobot.service.ReRunApoloService;

public class SlashListener {

	private TrainingService trainingService;

	private SlashCommandInteraction slashCommandInteraction;

	private Properties prop = new Properties();

	private Embed embed;

	private DiscordApi api;

	private String traductorApiKey;

	private ArrayList<String> badWords;

	private Scanner sc;

	public SlashListener(DiscordApi api) {
		this.api = api;
		trainingService = new TrainingService();
		badWords = new ArrayList<>();
		loadBadWords();
	}

	public void handleSlashComms() {
		api.addSlashCommandCreateListener(event -> {
			slashCommandInteraction = event.getSlashCommandInteraction();

			String command = slashCommandInteraction.getCommandName();
			String author = slashCommandInteraction.getUser().getName();
			System.out.println(author + " -> " + command);

			// if (author.equals("roca12")) {

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

			case "traducir": {
				traducir();
				break;
			}

			case "traducirpdf": {
				processPDF();
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

	// TODO: probar variables de entorno
	@SuppressWarnings("deprecation")
	public void traducir() {
		String texto = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");
		if (checkDeleteables(texto)) {
			slashCommandInteraction.createImmediateResponder().setContent("¡**").respond();
			return;
		}
		Translate translate = TranslateOptions.newBuilder().setApiKey(traductorApiKey).build().getService();

		String targetLanguage = "es";

		Translation translation = translate.translate(texto, Translate.TranslateOption.targetLanguage(targetLanguage));
		String traduccion = translation.getTranslatedText();

		slashCommandInteraction.createImmediateResponder().setContent(translation.getTranslatedText()).respond();
	}

	public void showTest() {
		ReRunApoloService rraDao = new ReRunApoloService();
		try {
			rraDao.createNewReRunByTest();
			slashCommandInteraction.createImmediateResponder()
					.setContent("Instancia DB corriendo y recibiendo solicitudes").appendNewLine()
					.append("Todos los sistemas estan operativos!").respond();
		} catch (Exception e) {
			slashCommandInteraction.createImmediateResponder().setContent(e.getMessage()).respond();
			slashCommandInteraction.createImmediateResponder().setContent("Algun sistema esta fallando").respond();
		}
	}

	public void processPDF() {
		Optional<SlashCommandInteractionOption> pdfOption = slashCommandInteraction.getOptionByName("pdf");
		slashCommandInteraction.respondLater();
		if (pdfOption.isPresent() && pdfOption.get().getAttachmentValue().isPresent()) {
			Attachment pdfAttachment = pdfOption.get().getAttachmentValue().get();
			if (!pdfAttachment.getFileName().endsWith(".pdf")) {
				slashCommandInteraction.createFollowupMessageBuilder()
						.setContent("EL archivo proporcionado no es un PDF 😡. Por favor, sube un archivo válido.")
						.send();
				return;
			}
			try {
				byte[] archivo = pdfAttachment.asByteArray().get();
				Translate translate = TranslateOptions.newBuilder().setApiKey(traductorApiKey).build().getService();
				String translatedFile = PDFProcessor.processPDF(archivo, translate);
				System.out.println("PDF procesado\n"+translatedFile);
				if (translatedFile.length() > 2000) {
					slashCommandInteraction.createFollowupMessageBuilder().setContent("Traducción de texto largo:\n")
							.addAttachment(PDFProcessor.generateTXT(translatedFile)).send();
				} else {
					slashCommandInteraction.createFollowupMessageBuilder()
							.setContent("Traducción: \n" + translatedFile).send();
				}
			} catch (Exception e) {
				slashCommandInteraction.createFollowupMessageBuilder().setContent("Error al procesar PDF.").send();
				e.printStackTrace();
			}

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
				.or(() -> slashCommandInteraction.getOptionByName("cancelar").map(option -> "cancelar")).orElse("");

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
						.setContent("¡Recuerda que el formato es dd-MM-yyy HH!").respond();
				return;
			}
		}

		if (dateTime.isBefore(LocalDateTime.now())) {
			slashCommandInteraction.createImmediateResponder().setContent(
					"La fecha no puede ser anterior al momento actual, prueba a revisar la fecha que mencionaste ;)")
					.respond();
			return;
		}

		StringBuilder rolesMentions = new StringBuilder();
		String[] roleNames = { "Avanzada", "Intermedia", "Básica", "Aprendiz", "admin", "Student UEB" };

		for (String role : roleNames)
			slashCommandInteraction.getServer().get().getRolesByName(role).stream().findFirst()
					.ifPresent(r -> rolesMentions.append(r.getMentionTag()).append(" "));

		switch (subCommandName) {
		case "anunciar":
			var trainingName = slashCommandInteraction.getArguments().get(1).getStringValue()
					.orElse("Generic Training");
			trainingService.save(new Training(slashCommandInteraction.getUser().getName(), dateTime, trainingName));
			System.out.println(
					"Entrenamiento creado por: " + slashCommandInteraction.getUser().getName() + " a las " + dateTime);
			slashCommandInteraction.createImmediateResponder()
					.setContent("¡Entrenamiento anunciado para el " + dateTime + "!\n" + rolesMentions)
					.setAllowedMentions(new AllowedMentionsBuilder().setMentionRoles(true).build()).respond();
			break;

		case "cancelar":
			if (trainingService.delete(dateTime))
				System.err.println("El entrenamiento de las " + dateTime + " borrado");
			else
				System.err.println("No se pudo eliminar");
			slashCommandInteraction.createImmediateResponder()
					.setContent("El entrenamiento programado para el " + dateTime + " ha sido cancelado.").respond();
			break;

		default:
			slashCommandInteraction.createImmediateResponder().setContent("Comando no reconocido.").respond();
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

	public String getTraductorApiKey() {
		return traductorApiKey;
	}

	public void setTraductorApiKey(String traductorApiKey) {
		this.traductorApiKey = traductorApiKey;
	}

}
