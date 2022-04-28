package edu.usc.csci310.controller;

import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import edu.usc.csci310.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/booking/")
public class BookingController {

    @Autowired
    VacancyRepository vr;

    @Autowired
    BookingService bs;

    /**
     * Returns vacancy of given recreation center, at given date.
     *
     * @param center
     * @param datetime yyyy-MM-dd
     * @return csv HH:mm, numVacant, HH:mm, numVacant, ...
     */
    @GetMapping("vacancy")
    public String getRecCenterVacancy(int center, String datetime) {

        // Convert date string to Java 8
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(datetime, dtfDate);
        // Query vacancy for given date
        List<Vacancy> vacancies = vr.findByRecCenterIdAndTimeslotBetween(
                center,
                LocalDateTime.of(localDate, LocalTime.of(0, 0)),
                LocalDateTime.of(localDate, LocalTime.of(23, 59))
        );

        // Format into CSV
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");
        StringBuilder sb = new StringBuilder();
        for(Vacancy vac : vacancies) {
            sb.append(vac.getTimeslot().format(dtfTime));
            sb.append(",");
            sb.append(vac.getNumVacant());
            sb.append(",");
        }
        // Remove trailing comma
        if(sb.length() >= 1) {
            sb.setLength(sb.length()-1);
        }
        return sb.toString();
    }

    /**
     * Adds new booking to DB.
     *
     * @param userid
     * @param center
     * @param datetime yyyy-MM-dd HH:mm
     * @return OK on success, CONFLICT on fail.
     */
    @GetMapping("book")
    // TODO: Add JWT login validation
    public ResponseEntity<Void> bookRecCenter(long userid, int center, String datetime) {
        // Convert date string to Java 8
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, dtf);

        boolean result = bs.createBookingIfNotExist(userid, center, localDateTime);

        if(result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
