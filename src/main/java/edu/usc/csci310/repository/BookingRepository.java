package edu.usc.csci310.repository;

import edu.usc.csci310.model.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {

}
