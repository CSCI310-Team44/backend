package edu.usc.csci310.repository;

import edu.usc.csci310.entity.Booking;
import org.springframework.data.repository.Repository;

public interface BookingRepository extends Repository<Booking, Long> {
    Booking findByBookingId(Long bookingId);
    Booking findByUserId(Long userId);
}
