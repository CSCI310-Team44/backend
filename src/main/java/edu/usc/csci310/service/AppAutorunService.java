package edu.usc.csci310.service;

import edu.usc.csci310.model.RecCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    /**
     * In-memory database refreshes after each startup.
     * Pre-populate the entries from startup date to auto
     * scheduler date.
     *
     * !!! REMOVE IF USING PERSISTING DATABASE !!!
     */
    @EventListener(ApplicationReadyEvent.class)
    public void populateVacancyToday() {
        LocalDate toDate = LocalDate.now();

        LocalDate current = toDate;
        LocalDate maxDate = toDate.plusDays(VacancyService.SCHEDULE_IN_ADVANCE);
        while(current.isBefore(maxDate)) {
            for(var center : RecCenter.Name.values()) {
                vacancyService.addTimeslots(center, current);
                // TODO: Remove break once RecCenter has been completely implemented
                break;
            }
            current = current.plusDays(1);
        }
    }
}
