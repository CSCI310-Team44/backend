package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {

    Booking findByUserIdAndRecCenterIdAndTimeslot(
            Long userId,
            Integer recCenterId,
            LocalDateTime timeslot
    );
}
