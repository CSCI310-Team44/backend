package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    /**
     * Find the given user's all previous bookings.
     */
    List<Booking> findBookingByUserIdAndTimeslotBeforeAndIsWaitListFalse(
            Long userId,
            LocalDateTime timeSlot
    );

    /**
     * Find the given user's all future bookings.
     */
    List<Booking> findBookingByUserIdAndTimeslotAfter(
            Long userId,
            LocalDateTime timeSlot
    );

    /**
     * Find the given user's specific bookings.
     */
    Booking findByUserIdAndRecCenterIdAndTimeslot(
            Long userId,
            Integer recCenterId,
            LocalDateTime timeslot
    );

    // See findWaitListedByNewAvailable() instead.
    List<Booking> findByRecCenterIdAndTimeslotAndIsWaitListIsTrue(
            Integer recCenterId,
            LocalDateTime timeslot
    );

    /**
     * Find all wait-listed bookings for given
     * recreation center and timeslot.
     */
    default List<Booking> findWaitListedByNewAvailable(
            Integer recCenterId,
            LocalDateTime timeslot
    ) {
        return findByRecCenterIdAndTimeslotAndIsWaitListIsTrue(recCenterId, timeslot);
    }
}
