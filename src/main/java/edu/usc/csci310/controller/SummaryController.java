package edu.usc.csci310.controller;

import edu.usc.csci310.model.Booking;
import edu.usc.csci310.repository.BookingRepository;
import edu.usc.csci310.repository.VacancyRepository;
import edu.usc.csci310.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/summary/")
public class SummaryController {

    @Autowired
    BookingService bs;

    @Autowired
    BookingRepository br;
    @Autowired
    VacancyRepository vr;

    /**
     * Permanently deletes booking from DB.
     *
     * @param userId
     * @param center
     * @param dateTime yyyy-MM-dd HH:mm
     * @return 1 on success, 0 on failure.
     */
    @GetMapping("delete")
    public String deleteBooking(
            @RequestParam(value = "userid") long userId,
            int center,
            @RequestParam(value = "datetime") String dateTime
    ) {
        // Convert date string to Java 8
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);

        bs.deleteBooking(userId, center, localDateTime);

        return "Success";
    }

    /**
     * Checks if any of the user's wait-listed bookings becomes
     * available.
     * Returns the newly available recreation center and datetime
     * as a string.
     *
     * @param userId
     * @return
     */
    @GetMapping("waitlist")
    public List<String> notifyWaitlist(
            @RequestParam(value = "userid") long userId
    ) {
        List<edu.usc.csci310.model.Vacancy> avilable = vr.findUserWaitListBecomesAvailable(userId);

        System.out.println(avilable.get(0).getTimeslot());

        return new ArrayList<>();
    }

    @GetMapping("previous")
    public String getPreviousBooking(long userid) {
        //todo get current time and parse
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateTime= dtf.format(now);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);

        List<Booking> book = br.findBookingByUserIdAndTimeslotBeforeAndIsWaitListFalse(userid, localDateTime);
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        for(Booking vac : book) {
            sb.append(vac.getTimeslot().format(dtfTime));
            sb.append(",");
            sb.append(vac.getRecCenterId());
            sb.append(",");
        }
        // Remove trailing comma
        if(sb.length() >= 1) {
            sb.setLength(sb.length()-1);
        }

        return sb.toString();
    }

    @GetMapping("future")
    public String getFutureBooking(long userid) {
        //todo get current time and parse
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateTime= dtf.format(now);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);
        List<Booking> book = br.findBookingByUserIdAndTimeslotAfter(userid, localDateTime);
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        for(Booking vac : book) {
            sb.append(vac.getTimeslot().format(dtfTime));
            sb.append(",");
            sb.append(vac.getRecCenterId());
            sb.append(",");
            sb.append(vac.isWaitList());
            sb.append(",");
        }
        // Remove trailing comma
        if(sb.length() >= 1) {
            sb.setLength(sb.length()-1);
        }
        return sb.toString();
    }
}
