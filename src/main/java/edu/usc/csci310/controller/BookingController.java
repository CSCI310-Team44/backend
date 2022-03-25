package edu.usc.csci310.controller;

import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import edu.usc.csci310.service.RecCenterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    VacancyRepository vacancyRepository;

    /**
     * Returns vacancy of given recreation center, at given date.
     *
     * @param name
     * @param date String, yyyy-MM-dd
     *
     * @return csv string: hh:mm, #vacant, hh:mm, #vacant, ...
     */
    @GetMapping("/api/booking/vacancy")
    public String getRecCenterVacancy(int name, String date) {

        // Query
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, dtfDate);
        List<Vacancy> vacancies = vacancyRepository.findByRecCenterIdAndTimeslotBetween(
                name,
                LocalDateTime.of(localDate, LocalTime.of(0, 0)),
                LocalDateTime.of(localDate, LocalTime.of(23, 59))
        );

        // Format
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");
        StringBuilder sb = new StringBuilder();
        for(Vacancy vac : vacancies) {
            sb.append(vac.getTimeslot().format(dtfTime));
            sb.append(",");
            sb.append(vac.getNumVacant());
            sb.append(",");
        }
        // Remove trailing comma
        sb.setLength(sb.length()-1);

        return sb.toString();
    }

    @GetMapping("/api/booking/reccenterbook")
    public void bookRecCenter(RecCenterData.Name name, String dateTime) {

    }
}
