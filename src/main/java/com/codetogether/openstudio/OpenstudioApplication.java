package com.codetogether.openstudio;

import com.codetogether.openstudio.util.InitService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OpenstudioApplication {

	private final InitService initService;

	public OpenstudioApplication(InitService initService) {
		this.initService = initService;
	}

	public static void main(String[] args) {
		SpringApplication.run(OpenstudioApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		this.initService.initSubjectTable();
		this.initService.createWeeklyPools();
	}
}
