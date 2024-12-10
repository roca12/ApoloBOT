package com.roca12.apolobot.controller.handler;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.Attachment;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.roca12.apolobot.model.Embed;
import com.roca12.apolobot.service.ReRunApoloService;


public class SlashListener {

	private SlashCommandInteraction slashCommandInteraction;

	private Properties prop = new Properties();

	private Embed embed;

	private DiscordApi api;
	
	private String traductorApiKey;

	public SlashListener(DiscordApi api) {
		this.api = api;
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
					notImplementedYet();
					break;
				}

				case "evento": {
					notImplementedYet();
					break;
				}
				
				case "traducir":{
					 String text = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");
					traducir(text);
					break;
				}
				
				case "traducirpdf":{
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
	
	//TODO: probar variables de entorno
	@SuppressWarnings("deprecation")
	public void traducir(String texto) {
        Translate translate = TranslateOptions.newBuilder()
                .setApiKey(traductorApiKey)
                .build()
                .getService();


        String targetLanguage = "es";


        Translation translation = translate.translate(
            texto,
            Translate.TranslateOption.targetLanguage(targetLanguage)
        );
        slashCommandInteraction.createImmediateResponder().setContent(translation.getTranslatedText()).respond();
	}
	
	public void traducirpdf() {
		Optional<SlashCommandInteractionOption> archivo = slashCommandInteraction.getOptionByName("archivo");
		if (archivo.isPresent() && archivo.get().getAttachmentValue().isPresent()) {
			Attachment attachment = archivo.get().getAttachmentValue().get();
			if(attachment.getFileName().endsWith(".pdf")) {
				try {
				PDDocument document = PDDocument.load(attachment.asInputStream());

	         
	            PDFTextStripper pdfStripper = new PDFTextStripper();
	            
					String text = pdfStripper.getText(document);
					traducir(text);
				} catch (IOException e) {
					slashCommandInteraction.createImmediateResponder().setContent("Error al leer el pdf");
				}

			}else {
				slashCommandInteraction.createImmediateResponder().setContent("El archivo subido debe de ser un pdf. ");
				return;
			}
		}
	}

	public void showTest() {
		ReRunApoloService rraDao= new ReRunApoloService();
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

	public void showNumeroUsuarios() {
		Set<Server> servers =api.getServers();
		for (Server s : servers) {
			String out = s.getName()+ " -> "+s.getMemberCount()+" users.";
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

	public String getTraductorApiKey() {
		return traductorApiKey;
	}

	public void setTraductorApiKey(String traductorApiKey) {
		this.traductorApiKey = traductorApiKey;
	}
	

}
