package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Transactional
public class BookingRepositoryTest {

    @Autowired
    BookingRepository br;

    static LocalDate pastDate = LocalDate.of(1997, 12, 31);
    static LocalDate futureDate = LocalDate.of(2097, 12, 31);

    @BeforeEach
    public void init() {
        Booking pastok1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(pastDate, LocalTime.of(11, 0)),
                false
        );
        Booking pastwait1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(pastDate, LocalTime.of(12, 0)),
                true
        );
        Booking futureok1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(futureDate, LocalTime.of(11, 0)),
                false
        );
        Booking futureok2 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(futureDate, LocalTime.of(12, 0)),
                false
        );
        Booking futurewait1 = new Booking(
                (long)0,
                (long)1_000_000_001,
                0,
                LocalDateTime.of(futureDate, LocalTime.of(13, 0)),
                true
        );
        Booking futurewait2 = new Booking(
                (long)0,
                (long)1_000_000_002,
                0,
                LocalDateTime.of(futureDate, LocalTime.of(13, 0)),
                true
        );
        br.save(pastok1);
        br.save(pastwait1);
        br.save(futureok1);
        br.save(futureok2);
        br.save(futurewait1);
        br.save(futurewait2);
    }

    /**
     * 1 previous booking.
     * 1 previous wait-listed booking, not included.
     */
    @Test
    public void findPreviousBookings() {
        List<Booking> bookings = br.findBookingByUserIdAndTimeslotBeforeAndIsWaitListFalse(
                (long)1_000_000_001,
                LocalDateTime.now()
        );
        assertEquals(1, bookings.size());
    }

    /**
     * 3 future bookings, including wait list.
     */
    @Test
    public void findFutureBookings() {
        List<Booking> bookings = br.findBookingByUserIdAndTimeslotAfter(
                (long)1_000_000_001,
                LocalDateTime.now()
        );
        assertEquals(3, bookings.size());
    }

    /**
     * 2 wait-listed bookings at given time.
     */
    @Test
    void findWaitListedByNewAvailable() {
        List<Booking> bookings = br.findWaitListedByNewAvailable(
                0,
                LocalDateTime.of(futureDate, LocalTime.of(13, 0))
        );
        assertEquals(2, bookings.size());
    }
}
