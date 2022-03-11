package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.springframework.data.repository.Repository;

public interface BookingRepository extends Repository<Booking, Long> {
    Booking findByBookingId(Long bookingId);
    Booking findByUserId(Long userId);
}
