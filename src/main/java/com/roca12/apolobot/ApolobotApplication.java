package com.roca12.apolobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.roca12.apolobot.controller.AplMain;

@SpringBootApplication
public class ApolobotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApolobotApplication.class, args);
		AplMain am= new AplMain();
		am.run();
	}

}
