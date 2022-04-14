package edu.usc.csci310.service;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.BookingRepository;
import edu.usc.csci310.repository.VacancyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class BookingServiceTest {

    @Autowired
    BookingService bs;
    @Autowired
    BookingRepository br;
    @Autowired
    VacancyRepository vr;

    static LocalDate testDate = LocalDate.of(2097, 12, 31);
    static LocalDateTime maxDateTime = LocalDateTime.of(testDate, LocalTime.of(11, 0));
    static LocalDateTime zeroDateTime = LocalDateTime.of(testDate, LocalTime.of(12, 0));

    @BeforeAll
    public static void init(
            @Autowired VacancyRepository vr
    ) {
        Vacancy vacancyFull = new Vacancy(
                (long)0,
                0,
                maxDateTime,
                3
        );
        Vacancy vacancyEmpty = new Vacancy(
                (long)0,
                0,
                zeroDateTime,
                0
        );
        vr.save(vacancyFull);
        vr.save(vacancyEmpty);
    }

    /**
     * Try adding booking when the recreation center has zero vacancy.
     */
    @Test
    public void addBookingWhenVacancyZero() {
        bs.addBooking(1_000_000_001, 0, zeroDateTime);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, zeroDateTime);
        assertNotNull(vacancy);
        assertEquals(0, vacancy.getNumVacant());

        Booking booking = br.findByUserIdAndRecCenterIdAndTimeslot(
                (long)1_000_000_001,
                0,
                zeroDateTime
        );
        assertNotNull(booking);
        assertTrue(booking.isWaitList());
    }

    /**
     * Try adding booking when the recreation center has available vacancy.
     */
    @Test
    public void addBookingWhenVacancyNotZero() {
        bs.addBooking(1_000_000_001, 0, maxDateTime);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, maxDateTime);
        assertNotNull(vacancy);
        assertEquals(2, vacancy.getNumVacant());

        Booking booking = br.findByUserIdAndRecCenterIdAndTimeslot(
                (long)1_000_000_001,
                0,
                maxDateTime
        );
        assertNotNull(booking);
        assertFalse(booking.isWaitList());
    }

    /*
     * When vacancy is max, there should be no bookings to be deleted.
     */

    /**
     * Try deleting wait-listed booking when the recreation center
     * has available vacancy.
     */
    @Test
    public void deleteBookingWaitListed() {
        Booking booking = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                zeroDateTime,
                true
        );
        br.save(booking);

        bs.deleteBooking(1_000_000_001, 0, zeroDateTime);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, zeroDateTime);
        assertNotNull(vacancy);
        assertEquals(0, vacancy.getNumVacant());

        Booking getBooking = br.findByUserIdAndRecCenterIdAndTimeslot(
                (long)1_000_000_001,
                0,
                zeroDateTime
        );
        assertNull(getBooking);
    }

    /**
     * Try deleting NON wait-listed booking when the recreation center
     * has available vacancy.
     */
    @Test
    public void deleteBookingNotWaitListed() {
        Booking booking = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                zeroDateTime,
                false
        );
        br.save(booking);

        bs.deleteBooking(1_000_000_001, 0, zeroDateTime);

        Vacancy vacancy = vr.findByRecCenterIdAndTimeslot(0, zeroDateTime);
        assertNotNull(vacancy);
        assertEquals(1, vacancy.getNumVacant());

        Booking getBooking = br.findByUserIdAndRecCenterIdAndTimeslot(
                (long)1_000_000_001,
                0,
                zeroDateTime
        );
        assertNull(getBooking);
    }
}
