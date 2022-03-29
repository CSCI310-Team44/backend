package edu.usc.csci310.service;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Notifies users if their wait-listed booking has a vacancy.
 * The notification is sent using server-sent events (SSE).
 */
@Service
public class NotificationService {

    @Autowired
    private BookingRepository br;

    private static final Lock lock = new ReentrantLock();

    /**
     * Map each userId to the user's notifier.
     */
    private static final HashMap<Long, SseEmitter> userNotifier = new HashMap<>();

    /**
     * Adds the SseEmitter to the pool of notifiers.
     */
    public void addNotifier(long userId, SseEmitter emitter) {
        lock.lock();
        userNotifier.put(userId, emitter);
        lock.unlock();
    }

    /**
     * Notifies all users who have a wait-listed booking at
     * the given recreation center and timeslot.
     *
     * @param center
     * @param timeslot
     */
    public void notifyVacancy(int center, LocalDateTime timeslot) {
        List<Booking> tbNotified = br.findWaitListedByNewAvailable(center, timeslot);

        String newAvailable = center + "," + timeslot;
        SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data(newAvailable);

        for(Booking booking : tbNotified) {
            System.out.println(booking.getBookingId());

            SseEmitter notifier = userNotifier.get(booking.getUserId());
            try {
                notifier.send(event);
            }
            // notifier failed to sent, reset notifier just in case
            catch (IOException ioe) {
                ioe.printStackTrace();
                lock.lock();
                userNotifier.remove(booking.getUserId());
                lock.unlock();
            }
            BufferedReader br;
        }
    }
}
