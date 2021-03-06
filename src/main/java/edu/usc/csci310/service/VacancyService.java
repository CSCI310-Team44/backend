package edu.usc.csci310.service;

import edu.usc.csci310.model.Booking;
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
import java.util.List;

@Service
@EnableScheduling
public class VacancyService {

    // Schedule 3 days in advance
    public static final int SCHEDULE_IN_ADVANCE = 3;

    @Autowired
    private VacancyRepository vr;

    /**
     * Increments vacancy by 1 if it does not exceed center limit.
     * Useful when a user has cancelled his booking.
     *
     * @param center
     * @param dateTime
     * @return 0 on success, -1 on failure
     */
    public int incrementVacancyIfNotMax(int center, LocalDateTime dateTime) {
        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(
                center, dateTime
        );

        int currentVacant = vacancy.getNumVacant();
        if(currentVacant < RecCenter.getRecCenter(center).getDefaultVacancy()) {
            vacancy.setNumVacant(currentVacant + 1);
            vr.save(vacancy);
            return 0;
        }
        return -1;
    }

    /**
     * Decrements vacancy by 1 if it does not reach negative vacancy.
     * Useful when a user has made a new booking.
     *
     * @param center
     * @param dateTime
     * @return 0 on success, -1 on failure
     */
    public int decrementVacancyIfNotZero(int center, LocalDateTime dateTime) {
        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(
                center, dateTime
        );

        int currentVacant = vacancy.getNumVacant();
        if(currentVacant > 0) {
            vacancy.setNumVacant(currentVacant - 1);
            vr.save(vacancy);
            return 0;
        }
        return -1;
    }

    public List<Booking> findAvailableSlotInWaitList(long userId) {
        return null;
    }

    /**
     * Adds to the database all vacancies of a recreation center
     * on given date.
     *
     * @param center {@code Enum} of center.
     * @param date Date to add.
     */
    public void addTimeslots(RecCenter.Name center, LocalDate date) {
        RecCenter recCenter = RecCenter.getRecCenter(center);

        for(LocalTime timeSlot : recCenter.getOperatingHoursOfDate(date)) {

            Vacancy vacancy = new Vacancy(
                    0L,
                    center.value,
                    LocalDateTime.of(date, timeSlot),
                    recCenter.getDefaultVacancy()
            );

            vr.save(vacancy);
        }
    }

    /**
     * Automatic scheduler. At 23:59:00 daily, adds to the database
     * all vacancies of all recreation centers at date in advance.
     *
     * (cron = SS MM HH dd ww yy)
     */
    @Scheduled(cron = "00 59 23 * * *")
    public void scheduleTimeslots() {
        for(var center : RecCenter.Name.values()) {
            addTimeslots(center, LocalDate.now().plusDays(SCHEDULE_IN_ADVANCE));
        }
    }
}
