package edu.usc.csci310.service;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.repository.BookingRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VacancyService vacancyService;

    public void addBooking(long userId, int center, LocalDateTime dateTime) {

        Booking booking = new Booking(
                0L,
                userId,
                center,
                dateTime,
                false
        );
        if(vacancyService.decrementVacancyIfNotEmpty(center, dateTime) == -1) {
            booking.setWaitList(true);
        }
        bookingRepository.save(booking);
    }

    public void removeBooking(long userId, int center, LocalDateTime dateTime) {
        Booking tbDeleted = bookingRepository
                .findByUserIdAndRecCenterIdAndTimeslot(userId, center, dateTime);
        bookingRepository.delete(tbDeleted);
        vacancyService.incrementVacancyIfNotFull(center, dateTime);
    }
}
