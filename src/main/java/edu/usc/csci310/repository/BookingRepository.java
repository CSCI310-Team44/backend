package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findBookingByUserIdAndTimeslotBeforeAndIsWaitListFalse(
            Long userId,
            LocalDateTime timeSlot
    );
    List<Booking> findBookingByUserIdAndTimeslotAfter(
            Long userId,
            LocalDateTime timeSlot
    );

}
