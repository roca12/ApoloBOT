package com.roca12.apolobot;

import com.roca12.apolobot.controller.AplMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ApolobotApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApolobotApplication.class, args);
    AplMain am = new AplMain();
    am.run();
  }
}
