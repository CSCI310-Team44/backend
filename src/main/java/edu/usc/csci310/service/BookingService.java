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

    public void createBooking(long userId, int center, LocalDateTime dateTime) {

        Booking booking = new Booking(
                0L,
                userId,
                center,
                dateTime,
                false
        );

        if(vs.decrementVacancyIfNotZero(center, dateTime) == -1) {
            booking.setWaitList(true);
        }
        br.save(booking);
    }

    /**
     * Creates a new booking for the current user at center at datetime
     * if one does not already exist .
     *
     * @param userid
     * @param center
     * @param dateTime
     * @return {@code true} on success, {@code false} on fail.
     */
    public boolean createBookingIfNotExist(long userid, int center, LocalDateTime dateTime) {
        Booking found = br
                .findByUserIdAndRecCenterIdAndTimeslot(userid, center, dateTime);
        if(found == null) {
            createBooking(userid, center, dateTime);
            return true;
        }
        else {
            return false;
        }
    }

    public void deleteBooking(long userId, int center, LocalDateTime dateTime) {
        Booking tbDeleted = br
                .findByUserIdAndRecCenterIdAndTimeslot(userId, center, dateTime);

        // If not wait-listed, increment vacancy and notify other users
        if(!tbDeleted.isWaitList()) {
            vs.incrementVacancyIfNotMax(center, dateTime);
            ns.notifyVacancy(center, dateTime);
        }

        br.delete(tbDeleted);
    }
}
