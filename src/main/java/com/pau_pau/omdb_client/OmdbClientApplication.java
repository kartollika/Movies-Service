package com.pau_pau.omdb_client;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.ParseException;

@SpringBootApplication
public class OmdbClientApplication {

	public static void main(String[] args) throws ParseException, InterruptedException {
		ConfigurableApplicationContext run = SpringApplication.run(OmdbClientApplication.class, args);
		run.getBean(TransferService.class).run();
		int exit = SpringApplication.exit(run, (ExitCodeGenerator) () -> 0);
		System.exit(exit);
	}

}
