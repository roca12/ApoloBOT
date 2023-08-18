package controller.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class MessageListener implements MessageCreateListener {
	
	private DiscordApi api;

	private ArrayList<String> badWords;
	private File file;
	private Scanner sc;
	private Properties prop = new Properties();

	public MessageListener(DiscordApi api) {
		this.api= api;
		badWords = new ArrayList<>();
		loadBadWords();
	}
	
	

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		Message message = event.getMessage();
		String msg = message.getContent();
		String autor = message.getAuthor().getDisplayName();
		if (checkDeleteables(msg)) {
			String alerta = "Cuidado con ese vocabulario (" + autor + ")";
			System.out.println(autor + " -> esta escribiendo malas palabras");
			event.getChannel().sendMessage(alerta);

			// aprender a eliminar un mensaje
		}
	}

	private void loadBadWords() {
		try {
			file = new File("src/util/badwords.txt");

			sc = new Scanner(file);
			while (sc.hasNext()) {
				badWords.add(sc.nextLine().toLowerCase());
			}

		} catch (FileNotFoundException e) {
			System.out.println("File badwords.txt not found");
			e.printStackTrace();
			System.exit(0);
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

	public ArrayList<String> getBadWords() {
		return badWords;
	}

	public void setBadWords(ArrayList<String> badWords) {
		this.badWords = badWords;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Scanner getSc() {
		return sc;
	}

	public void setSc(Scanner sc) {
		this.sc = sc;
	}
	
	

}