package com.roca12.apolobot.controller.handler;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.repository.TrainingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.javacord.api.DiscordApi;

/**
 * Responsible for announcing upcoming training sessions in Discord channels.
 * This class periodically checks for upcoming training sessions and sends
 * announcement messages to notify users about them. It uses a timer to
 * check for upcoming sessions every minute.
 * 
 * @author roca12
 * @version 2025-I
 */
public class TrainingAnnouncer {

  /** Repository for accessing training data */
  private TrainingRepository trainingRepository;

  /** Discord API instance */
  private DiscordApi api;

  /**
   * Constructor for TrainingAnnouncer.
   * Initializes the Discord API, gets the training repository from the application context,
   * and schedules the announcement timer.
   * 
   * @param api The Discord API instance
   */
  public TrainingAnnouncer(DiscordApi api) {
    this.api = api;
    trainingRepository = ApplicationContextHolder.getContext().getBean(TrainingRepository.class);
    scheduleAnnouncements();
  }

  /**
   * Schedules the periodic announcement task.
   * This method sets up a timer that runs the announce method every minute.
   * The timer runs as a daemon thread, so it won't prevent the application from exiting.
   */
  private void scheduleAnnouncements() {
    // Create a daemon timer
    Timer timer = new Timer(true);

    // Schedule the announcement task to run every minute (60000 ms)
    timer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
            announce();
          }
        },
        0,  // Start immediately
        60000);  // Run every minute
  }

  /**
   * Announces upcoming training sessions.
   * This method checks for training sessions that are scheduled to start within the next hour
   * and haven't been announced yet. It then sends announcement messages to the system channel
   * of each server, mentioning relevant roles.
   * 
   * After sending the announcements, it marks the training sessions as announced to prevent
   * duplicate announcements.
   */
  private void announce() {
    // Log the announcement attempt
    System.out.println("Intentando anunciar...");

    // Get the current time and one hour from now
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime oneHourLater = now.plusHours(1);
    System.out.print("A las " + now);

    // Find upcoming training sessions that haven't been announced yet
    List<Training> upcomingTrainings =
        trainingRepository.findByDateTimeBetweenAndAnnounced(now, oneHourLater, false);

    // Mark all found trainings as announced
    upcomingTrainings.forEach(
        training -> {
          training.setAnnounced(true);
        });

    // Define the roles to mention in the announcement
    String[] roleNames = {"Avanzada", "Intermedia", "Básica", "Aprendiz", "admin", "Student UEB"};

    // Log the found trainings
    System.out.println("Se obtiene: " + upcomingTrainings);

    // Build the role mentions string
    StringBuilder rolesMention = new StringBuilder();

    // Add mention tags for each role across all servers
    for (String role : roleNames)
      api.getServers()
          .forEach(
              server -> {
                server.getRolesByName(role).stream()
                    .findFirst()
                    .ifPresent(r -> rolesMention.append(r.getMentionTag()).append(" "));
              });

    // Send announcement for each upcoming training to each server's system channel
    for (Training training : upcomingTrainings) {
      api.getServers()
          .forEach(
              server -> {
                server
                    .getTextChannelById(server.getSystemChannel().get().getId())
                    .ifPresent(
                        channel -> {
                          channel.sendMessage(
                              "¡Atención!, recuerden la clase '"
                                  + training.getTrainingName()
                                  + "' con el coach "
                                  + training.getCoach()
                                  + " será a las "
                                  + training.getDateTime().toString()
                                  + " no falten!"
                                  + rolesMention);
                        });
              });
    }

    // Save the updated training sessions (marked as announced)
    trainingRepository.saveAll(upcomingTrainings);
  }
}
