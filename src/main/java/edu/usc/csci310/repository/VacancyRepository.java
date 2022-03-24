package edu.usc.csci310.repository;

import edu.usc.csci310.model.Vacancy;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {

    /**
     * JPA auto-generated method.
     *
     * @param timeslot1 00:00 of a given date.
     * @param timeslot2 23:59 of a given date.
     */
    List<Vacancy> findByRecCenterIdAndTimeslotBetween(
            Integer recCenterId,
            LocalDateTime timeslot1,
            LocalDateTime timeslot2
    );
}
