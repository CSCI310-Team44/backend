package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.model.Vacancy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VacancyRepositoryTest {

    @Autowired
    VacancyRepository vr;
    @Autowired
    BookingRepository br;

    static LocalDate testingDate = LocalDate.of(2095, 12, 31);

    @BeforeEach
    public void init() {
        Vacancy vacancy1 = new Vacancy(
                (long)0,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(11, 0)),
                10
        );
        Vacancy vacancy2 = new Vacancy(
                (long)0,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(12, 0)),
                10
        );
        Vacancy vacancy3 = new Vacancy(
                (long)0,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(13, 0)),
                0
        );
        vr.save(vacancy1);
        vr.save(vacancy2);
        vr.save(vacancy3);
    }

    /**
     * 3 timeslots.
     */
    @Test
    public void findDailyHours() {

        List<Vacancy> vacancies = vr.findByRecCenterIdAndTimeslotBetween(
                0,
                LocalDateTime.of(testingDate, LocalTime.of(0, 0)),
                LocalDateTime.of(testingDate, LocalTime.of(23, 59))
        );
        assertEquals(3, vacancies.size());
    }

    /**
     * 1 wait-listed booking becomes avilable.
     * 1 booking is not wait-listed.
     * 1 booking is not available.
     */
    @Test
    public void findByUserWaitListBecomesAvailable() {
        Booking futureok1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(11, 0)),
                false
        );
        Booking futurewait1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(12, 0)),
                true
        );
        Booking futurewait2 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(testingDate, LocalTime.of(13, 0)),
                true
        );
        br.save(futureok1);
        br.save(futurewait1);
        br.save(futurewait2);

        List<Vacancy> vacancies = vr.findUserWaitListBecomesAvailable((long)1_000_000_001);
        assertEquals(1, vacancies.size());
        assertEquals(
                LocalDateTime.of(testingDate, LocalTime.of(12, 0)),
                vacancies.get(0).getTimeslot()
        );
    }

}
