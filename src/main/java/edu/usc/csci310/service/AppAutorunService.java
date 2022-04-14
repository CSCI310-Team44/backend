package edu.usc.csci310.service;

import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 */
@Service
public class AppAutorunService {

    @Autowired
    VacancyService vs;
    @Autowired
    VacancyRepository vr;

    // Method on startup
    // https://stackoverflow.com/questions/27405713/running-code-after-spring-boot-starts

    /**
     * In-memory database refreshes after each startup.
     * Pre-populate the database with entries from startup
     * date to auto scheduler date.
     *
     * !!! REMOVE IF USING PERSISTING DATABASE !!!
     */
    @EventListener(ApplicationReadyEvent.class)
    public void prepopulateVacancy() {
        LocalDate toDate = LocalDate.now();

        LocalDate current = toDate;
        LocalDate maxDate = toDate.plusDays(VacancyService.SCHEDULE_IN_ADVANCE);
        while(current.isBefore(maxDate)) {
            for(var center : RecCenter.Name.values()) {
                vs.addTimeslots(center, current);
            }
            current = current.plusDays(1);
        }

        // For frontend testing, LYON CENTER next day 11:00 is set to 0
        Vacancy test0 = vr.findByRecCenterIdAndTimeslot(
                0,
                LocalDateTime.of(
                        LocalDate.now().plusDays(1),
                        LocalTime.of(11, 0)
                )
        );
        test0.setNumVacant(0);
        vr.save(test0);
    }
}
