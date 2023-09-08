package com.roca12.apolobot.controller.handler;

import java.time.LocalTime;
import java.util.Set;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;
import org.javacord.api.entity.message.mention.AllowedMentionsBuilder;

public class LessonMessageSender {

	private DiscordApi api;
	private boolean isProd;

	public LessonMessageSender(DiscordApi api, boolean isProd) {
		this.api = api;
		this.isProd = isProd;
	}
	
	public LessonMessageSender() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DiscordApi getApi() {
		return api;
	}

	public void setApi(DiscordApi api) {
		this.api = api;
	}

	public boolean isProd() {
		return isProd;
	}

	public void setProd(boolean isProd) {
		this.isProd = isProd;
	}

	public void setTimer() {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						sendMessageLesson();

						if (isProd) {
							// cada 12 horas
							Thread.sleep(1000 * 60 * 60 * 12);
						} else {
							// cada minuto
							Thread.sleep(1000 * 60);
						}
						
						// cada hora
						// Thread.sleep(1000*60*60);

						// cada dia
						// Thread.sleep(1000*60*60*24);
					} catch (InterruptedException ie) {
					}
				}
			}
		};
		t.start();
	}


	public void sendMessageLesson() {
		//falta guardar archivo de fechas y horas
		//averiguar si puede ser un properties
		
		Set<ServerTextChannel> allChannels = api.getServerTextChannelsByName("general");

		AllowedMentions allowedMentions = new AllowedMentionsBuilder().setMentionEveryoneAndHere(true).build();
		
		for (ServerTextChannel s : allChannels) {
			new MessageBuilder().setAllowedMentions(allowedMentions)
					.setContent("Recuerda que la clase es de 8 a 9 PM, los dias Lunes, Martes y Miercoles ")
					.append("@here").send(s);
		}

		System.out.println("Enviando mensaje de recordatorio de clase " + LocalTime.now().toString());

	}
	
	

}
