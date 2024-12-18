package com.roca12.apolobot.controller.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;

import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.springframework.core.io.ClassPathResource;

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
	

	private ArrayList<String> badWords;
	
	private Scanner sc;

	public SlashListener(DiscordApi api) {
		
		this.api = api;
		badWords = new ArrayList<>();
		loadBadWords();
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
					 
					traducir();
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
	public void traducir() {
		String texto = slashCommandInteraction.getArguments().get(0).getStringValue().orElse("");
		if(checkDeleteables(texto)) {
			slashCommandInteraction.createImmediateResponder().setContent("¡**").respond();
			return;
		}
        Translate translate = TranslateOptions.newBuilder()
                .setApiKey(traductorApiKey)
                .build()
                .getService();


        String targetLanguage = "es";


        Translation translation = translate.translate(
            texto,
            Translate.TranslateOption.targetLanguage(targetLanguage)
        );
        String traduccion = translation.getTranslatedText();
        
        slashCommandInteraction.createImmediateResponder().setContent(translation.getTranslatedText()).respond();
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
