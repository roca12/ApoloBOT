package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import controller.handler.LessonMessageSender;
import controller.handler.MessageListener;
import controller.handler.SlashBuilder;
import controller.handler.SlashListener;

public class AplMain {

	static boolean productionState = false;

	static String token;
	static DiscordApi api;

	static SlashBuilder sb;
	static SlashListener sl;
	static MessageListener ml;
	static LessonMessageSender ms;

	static Properties prop = new Properties();

	public static void main(String[] args) {
		loadAndCheckCriticals();
		run();
	}

	public static void run() {
		System.out.println("Starting Apolo bot");
		try {
			if (checkReqs()) {

				System.out.println("Invite the bot with the following url: " + api.createBotInvite());

				api.bulkOverwriteGlobalApplicationCommands(sb.initSlashCommands()).join();

				printInfo();

				api.addListener(ml);

				sl.handleSlashComms();

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

	private static boolean loadAndCheckCriticals() {

		try {
			prop.load(new FileInputStream("src/util/main.properties"));
			if (productionState) {
				System.out.println("Running in prod mode");
				token = prop.getProperty("apolo.prod.token");
			} else {
				System.out.println("Running in dev test mode");
				token = prop.getProperty("apolo.test.token");
			}

			api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();
			sb = new SlashBuilder(api);
			sl = new SlashListener(api);
			ml = new MessageListener(api);
			ms = new LessonMessageSender(api, productionState);

			return true;

		} catch (IOException e) {
			System.err.println("main.properties error");
			return false;
		}

	}

	private static void printInfo() {
		System.out.println("Client:" + api.getClientId());
	}

	private static boolean checkReqs() {

		try {
			prop.load(new FileInputStream("src/util/messagelistener.properties"));
			ml.setProp(prop);
		} catch (IOException e) {
			System.err.println("messagelistener.properties error");
			return false;
		}

		try {
			prop.load(new FileInputStream("src/util/slashbuilder.properties"));
			sb.setProp(prop);
		} catch (IOException e) {
			System.err.println("slashbuilder.properties error");
			return false;
		}

		try {
			prop.load(new FileInputStream("src/util/slashlistener.properties"));
			sl.setProp(prop);
		} catch (IOException e) {
			System.err.println("slashlistener.properties error");
			return false;
		}

		if (token.isBlank()) {
			System.err.println("Discord token error");
			return false;
		} else {
			return true;
		}
	}

}