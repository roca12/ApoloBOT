package com.roca12.apolobot.controller;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.roca12.apolobot.controller.handler.LessonMessageSender;
import com.roca12.apolobot.controller.handler.MessageListener;
import com.roca12.apolobot.controller.handler.SlashBuilder;
import com.roca12.apolobot.controller.handler.SlashListener;
@SpringBootApplication
public class AplMain {

	boolean productionState = true;

	String token;
	DiscordApi api;

	SlashBuilder sb;
	SlashListener sl;
	MessageListener ml;
	LessonMessageSender ms;

	Properties prop = new Properties();

	

	public  void run() {
		loadAndCheckCriticals();
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

	private boolean loadAndCheckCriticals() {
		System.out.println(System.getProperty("user.dir"));
		try {
			InputStream r = new ClassPathResource("files/main.properties").getInputStream();
			prop.load(r);
			System.out.println("token " + prop.getProperty("apolo.test.token"));
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
			e.printStackTrace();
			System.err.println("main.properties error");
			return false;
		}

	}

	private void printInfo() {
		System.out.println("Client:" + api.getClientId());
	}

	private boolean checkReqs() {
		if (token.isBlank()) {
			System.err.println("Discord token error");
			return false;
		} else {
			return true;
		}
	}

}