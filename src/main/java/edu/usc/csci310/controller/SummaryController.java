package edu.usc.csci310.controller;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.model.RecCenter;
import edu.usc.csci310.repository.BookingRepository;
import edu.usc.csci310.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SummaryController {

    @Autowired
    BookingService bookingService;

    public String deleteBooking(
            @RequestParam(value = "userid") long userId,
            int center,
            @RequestParam(value = "datetime") String dateTime
    ) {
        // Convert date string to Java 8
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);

        bookingService.removeBooking(userId, center, localDateTime);

        return "Success";
    }
}
