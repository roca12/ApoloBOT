package com.roca12.apolobot.controller.handler;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
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
		Month[] months = { Month.FEBRUARY, Month.MARCH, Month.MAY, Month.JUNE, Month.AUGUST, Month.SEPTEMBER,
				Month.OCTOBER, Month.NOVEMBER };
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					LocalDateTime now = LocalDateTime.now();
					now.atZone(ZoneId.of("America/Bogota"));
					if (Arrays.binarySearch(months, now.getMonth()) != -1) {
						if (isProd) {
							if (now.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
								if (now.getHour() == 12 && now.getMinute() == 00 && now.getSecond() == 00) {
									sendMessageLesson();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							}
						} else {
							if (now.getHour() == 12 && now.getMinute() == 00 && now.getSecond() == 00) {
								sendMessageLesson();
								try {
									Thread.sleep(1000 * 60 * 5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		};
		t.start();

	}

	public void sendMessageLesson() {
		// falta guardar archivo de fechas y horas
		// averiguar si puede ser un properties

		Set<ServerTextChannel> allChannels = api.getServerTextChannelsByName("general");

		AllowedMentions allowedMentions = new AllowedMentionsBuilder().setMentionEveryoneAndHere(true).build();

		for (ServerTextChannel s : allChannels) {
//			new MessageBuilder().setAllowedMentions(allowedMentions)
//					.setContent("Recuerda que la clase es de 8 a 9 PM, los dias Lunes, Martes y Miercoles ")
//					.append("@here").send(s);
			new MessageBuilder().setAllowedMentions(allowedMentions)
					.setContent(
							"Recuerda que nuestros entrenamientos son de 8 a 9 PM, los dias Lunes, Martes y Miercoles ")
					.send(s);
		}

		System.out.println("Enviando mensaje de recordatorio de clase " + LocalTime.now().toString());

	}

}
