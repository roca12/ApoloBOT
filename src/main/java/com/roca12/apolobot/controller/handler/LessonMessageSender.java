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

/**
 * Responsible for sending scheduled lesson reminder messages to Discord channels.
 * This class runs a timer that sends reminder messages about class schedules
 * at specific times, depending on whether the bot is running in production
 * or development mode.
 * 
 * @author roca12
 * @version 2025-I
 */
public class LessonMessageSender {

  /** Discord API instance */
  private DiscordApi api;

  /** Flag indicating whether the bot is running in production mode */
  private boolean isProd;

  /**
   * Constructor for LessonMessageSender with parameters.
   * Initializes the Discord API and sets the production mode flag.
   * 
   * @param api The Discord API instance
   * @param isProd Whether the bot is running in production mode
   */
  public LessonMessageSender(DiscordApi api, boolean isProd) {
    this.api = api;
    this.isProd = isProd;
  }

  /**
   * Default constructor for LessonMessageSender.
   * This constructor is currently empty and marked as a TODO.
   */
  public LessonMessageSender() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Gets the Discord API instance.
   * 
   * @return The DiscordApi object
   */
  public DiscordApi getApi() {
    return api;
  }

  /**
   * Sets the Discord API instance.
   * 
   * @param api The DiscordApi object to set
   */
  public void setApi(DiscordApi api) {
    this.api = api;
  }

  /**
   * Checks if the bot is running in production mode.
   * 
   * @return true if in production mode, false if in development mode
   */
  public boolean isProd() {
    return isProd;
  }

  /**
   * Sets whether the bot is running in production mode.
   * 
   * @param isProd true for production mode, false for development mode
   */
  public void setProd(boolean isProd) {
    this.isProd = isProd;
  }

  /**
   * Sets up a timer to send lesson reminder messages at specific times.
   * This method creates and starts a background thread that continuously checks
   * the current time and sends reminder messages when appropriate.
   * 
   * In production mode, messages are sent at noon on Mondays during specific months.
   * In development mode, messages are sent at noon every day during specific months.
   */
  public void setTimer() {
    // Define the months when classes are in session
    Month[] months = {
      Month.FEBRUARY,
      Month.MARCH,
      Month.MAY,
      Month.JUNE,
      Month.AUGUST,
      Month.SEPTEMBER,
      Month.OCTOBER,
      Month.NOVEMBER
    };

    // Create and start a background thread for the timer
    Thread t =
        new Thread() {
          @Override
          public void run() {
            while (true) {
              // Get the current time in Bogota timezone
              LocalDateTime now = LocalDateTime.now();
              now.atZone(ZoneId.of("America/Bogota"));

              // Check if the current month is in the list of active months
              if (Arrays.binarySearch(months, now.getMonth()) != -1) {
                if (isProd) {
                  // In production mode, only send messages on Mondays at noon
                  if (now.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    if (now.getHour() == 12 && now.getMinute() == 00 && now.getSecond() == 00) {
                      sendMessageLesson();
                      try {
                        // Sleep to avoid sending multiple messages
                        Thread.sleep(2000);
                      } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                    }
                  }
                } else {
                  // In development mode, send messages every day at noon
                  if (now.getHour() == 12 && now.getMinute() == 00 && now.getSecond() == 00) {
                    sendMessageLesson();
                    try {
                      // Sleep for 5 minutes to avoid sending multiple messages
                      Thread.sleep(1000 * 60 * 5);
                    } catch (InterruptedException e) {
                      e.printStackTrace();
                    }
                  }
                }
              }

              // Safety check to eventually terminate the thread (after year 2050)
              if (now.getYear() > 2050) {
                break;
              }
            }
          }
        };
    t.start();
  }

  /**
   * Sends lesson reminder messages to all "general" channels in all servers.
   * This method constructs a message with information about training schedules
   * and sends it to all channels named "general" that the bot can access.
   */
  public void sendMessageLesson() {
    // TODO: falta guardar archivo de fechas y horas
    // TODO: averiguar si puede ser un properties

    // Get all channels named "general" across all servers
    Set<ServerTextChannel> allChannels = api.getServerTextChannelsByName("general");

    // Configure mentions to allow @everyone and @here
    AllowedMentions allowedMentions =
        new AllowedMentionsBuilder().setMentionEveryoneAndHere(true).build();

    // Send the message to each channel
    for (ServerTextChannel s : allChannels) {
      // Alternative message format (currently commented out)
      // new MessageBuilder().setAllowedMentions(allowedMentions)
      // .setContent("Recuerda que la clase es de 8 a 9 PM, los dias Lunes, Martes y
      // Miercoles
      // ")
      // .append("@here").send(s);

      // Build and send the reminder message
      new MessageBuilder()
          .setAllowedMentions(allowedMentions)
          .setContent(
              "Recuerda que nuestros entrenamientos son de 8 a 9 PM, los dias Lunes, Miercoles y"
                  + " Jueves, y en caso de no poder asistir siempre quedanlas grabaciones en"
                  + " #grabaciones")
          .send(s);
    }

    // Log that the message was sent
    System.out.println("Enviando mensaje de recordatorio de clase " + LocalTime.now().toString());
  }
}
