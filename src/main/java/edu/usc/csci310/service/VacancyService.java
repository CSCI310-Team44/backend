package edu.usc.csci310.service;

import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@EnableScheduling
public class VacancyService {

    // Schedule 3 days in advance
    public static final int SCHEDULE_IN_ADVANCE = 3;

    @Autowired
    private VacancyRepository vacancyRepository;

    /**
     * Adds to the database all vacancies of a recreation center
     * on given date.
     *
     * @param center {@code Enum} of center.
     * @param date Date to add.
     */
    public void addVacancy(RecCenter.Name center, LocalDate date) {
        RecCenter recCenter = RecCenter.getRecCenter(center);

        for(LocalTime timeSlot : recCenter.getOperatingHoursOfDate(date)) {

            Vacancy vacancy = new Vacancy(
                    0L,
                    center.value,
                    LocalDateTime.of(date, timeSlot),
                    recCenter.getVacancy()
            );

            vacancyRepository.save(vacancy);
        }
    }

    /**
     * Automatic scheduler. At 23:59:00 daily, adds to the database
     * all vacancies of all recreation centers at date in advance.
     *
     * (cron = SS MM HH dd ww yy)
     */
    @Scheduled(cron = "00 59 23 * * *")
    public void scheduleAddVacancy() {
        for(var center : RecCenter.Name.values()) {
            addVacancy(center, LocalDate.now().plusDays(SCHEDULE_IN_ADVANCE));
            // TODO: Remove break once RecCenter has been completely implemented
            break;
        }
    }
}
