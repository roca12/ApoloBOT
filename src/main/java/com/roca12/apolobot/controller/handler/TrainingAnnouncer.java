package com.roca12.apolobot.controller.handler;

import com.roca12.apolobot.controller.ApplicationContextHolder;
import com.roca12.apolobot.model.Training;
import com.roca12.apolobot.repository.TrainingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.javacord.api.DiscordApi;

public class TrainingAnnouncer {

  private TrainingRepository trainingRepository;

  private DiscordApi api;

  public TrainingAnnouncer(DiscordApi api) {
    this.api = api;
    trainingRepository = ApplicationContextHolder.getContext().getBean(TrainingRepository.class);
    scheduleAnnouncements();
  }

  private void scheduleAnnouncements() {
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(
        new TimerTask() {
          public void run() {
            announce();
          }
        },
        0,
        60000);
  }

  private void announce() {
    System.out.println("Intentando anunciar...");
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime oneHourLater = now.plusHours(1);
    System.out.print("A las " + now);
    List<Training> upcomingTrainings =
        trainingRepository.findByDateTimeBetweenAndAnnounced(now, oneHourLater, false);
    upcomingTrainings.forEach(
        training -> {
          training.setAnnounced(true);
        });
    String[] roleNames = {"Avanzada", "Intermedia", "Básica", "Aprendiz", "admin", "Student UEB"};

    System.out.println("Se obtiene: " + upcomingTrainings);

    StringBuilder rolesMention = new StringBuilder();

    for (String role : roleNames)
      api.getServers()
          .forEach(
              server -> {
                server.getRolesByName(role).stream()
                    .findFirst()
                    .ifPresent(r -> rolesMention.append(r.getMentionTag()).append(" "));
              });

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

    trainingRepository.saveAll(upcomingTrainings);
  }
}
