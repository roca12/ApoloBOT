package com.roca12.apolobot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.core.io.ClassPathResource;

import com.roca12.apolobot.controller.handler.LessonMessageSender;
import com.roca12.apolobot.controller.handler.MessageListener;
import com.roca12.apolobot.controller.handler.SlashBuilder;
import com.roca12.apolobot.controller.handler.SlashListener;
import com.roca12.apolobot.service.ReRunApoloService;

public class AplMain {


	private final boolean PRODUCTION_STATE = true;

	private String token;
	private DiscordApi api;

	private SlashBuilder sb;
	private SlashListener sl;
	private MessageListener ml;
	private LessonMessageSender ms;

	private Properties prop = new Properties();
	
	//private GroceryItemDAO giDao;
	private ReRunApoloService rraDao;

	public AplMain() {
		//giDao = new GroceryItemDAO();
		rraDao = new ReRunApoloService();
	}

	public void run() {
		
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
		//System.out.println(System.getProperty("user.dir"));
		try {
			InputStream r = new ClassPathResource("files/main.properties").getInputStream();
			prop.load(r);
			//System.out.println("token " + prop.getProperty("apolo.test.token"));
			if (PRODUCTION_STATE) {
				System.out.println("Running in prod mode");
				token = prop.getProperty("apolo.prod.token");
			} else {
				System.out.println("Running in dev test mode");
				token = prop.getProperty("apolo.test.token");
			}
			
			
			api = new DiscordApiBuilder().setToken(token).setAllIntents().login().join();
			sb = new SlashBuilder(api);
			sl = new SlashListener(api);
			sl.setTraductorApiKey(prop.getProperty("apolo.traductor.apikey"));
			ml = new MessageListener(api);
			ms = new LessonMessageSender(api, PRODUCTION_STATE);
			
			testMongoDB(); 

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
	
	private void testMongoDB() {
		rraDao.createNewReRun();
	}
}