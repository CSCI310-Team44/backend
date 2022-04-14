package edu.usc.csci310.service;

import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class VacancyServiceTest {

    @Autowired
    VacancyService vs;
    @Autowired
    VacancyRepository vr;

    @Mock
    RecCenter recCenter;

    static LocalDate testDate = LocalDate.of(2097, 12, 31);
    static LocalDateTime maxDateTime = LocalDateTime.of(testDate, LocalTime.of(11, 0));
    static LocalDateTime zeroDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 0));

    @BeforeEach
    public void init() {
        Vacancy vacancyMax = new edu.usc.csci310.model.Vacancy(
                (long)0,
                0,
                maxDateTime,
                3
        );
        Vacancy vacancyZero = new edu.usc.csci310.model.Vacancy(
                (long)0,
                0,
                zeroDateTime,
                0
        );
        vr.save(vacancyMax);
        vr.save(vacancyZero);
    }

    /**
     * Try increment when the recreation center is full.
     */
    @Test
    public void incrementVacancyWhenMax() {
        // Mockito simulation of recreation center with maximum 3 vacant spots.
        try (MockedStatic<RecCenter> rcClass = mockStatic(RecCenter.class)) {
            rcClass
                    .when(() -> RecCenter.getRecCenter(0))
                    .thenReturn(recCenter);
            when(recCenter.getDefaultVacancy())
                    .thenReturn(3);

            int result = vs.incrementVacancyIfNotMax(0, maxDateTime);

            assertEquals(-1, result);
        }

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, maxDateTime);

        assertEquals(3, vacancy.getNumVacant());
    }

    /**
     * Try increment when the recreation center is NOT full.
     */
    @Test
    public void incrementVacancyWhenNotMax() {
        int result = vs.incrementVacancyIfNotMax(0, zeroDateTime);
        assertEquals(0, result);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, zeroDateTime);
        assertEquals(1, vacancy.getNumVacant());
    }

    /**
     * Try decrement when the recreation center is empty.
     */
    @Test
    public void decrementVacancyWhenZero() {
        int result = vs.decrementVacancyIfNotZero(0, zeroDateTime);
        assertEquals(-1, result);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, zeroDateTime);
        assertEquals(0, vacancy.getNumVacant());
    }

    /**
     * Try decrement when the recreation center is NOT empty.
     */
    @Test
    public void decrementVacancyWhenNotZero() {
        int result = vs.decrementVacancyIfNotZero(0, maxDateTime);
        assertEquals(0, result);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, maxDateTime);
        assertEquals(2, vacancy.getNumVacant());
    }
}
