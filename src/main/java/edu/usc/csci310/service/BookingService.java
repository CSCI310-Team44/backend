package edu.usc.csci310.service;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository br;

    @Autowired
    private NotificationService ns;
    @Autowired
    private VacancyService vs;

    public void addBooking(long userId, int center, LocalDateTime dateTime) {

        Booking booking = new Booking(
                0L,
                userId,
                center,
                dateTime,
                false
        );
        System.out.println(booking.getUserId());
        System.out.println(center);
        System.out.println(dateTime);
        if(vs.decrementVacancyIfNotEmpty(center, dateTime) == -1) {
            booking.setWaitList(true);
        }
        br.save(booking);
    }

    public void deleteBooking(long userId, int center, LocalDateTime dateTime) {
        Booking tbDeleted = br
                .findByUserIdAndRecCenterIdAndTimeslot(userId, center, dateTime);

        // If not wait-listed, increment vacancy and notify other users
        if(!tbDeleted.isWaitList()) {
            vs.incrementVacancyIfNotFull(center, dateTime);
            ns.notifyVacancy(center, dateTime);
        }

        br.delete(tbDeleted);
    }
}
