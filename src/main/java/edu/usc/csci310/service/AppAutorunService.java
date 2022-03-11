package edu.usc.csci310.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 *
 */
@Service
public class AppAutorunService {

    @Autowired
    private VacancyService vacancyService;

    // Method on startup
    // https://stackoverflow.com/questions/27405713/running-code-after-spring-boot-starts

    @EventListener(ApplicationReadyEvent.class)
    public void generateVacancy() {
        vacancyService.populateDatabase();
    }
}
