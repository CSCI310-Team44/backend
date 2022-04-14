package edu.usc.csci310.repository;

import edu.usc.csci310.model.Vacancy;
import org.springframework.data.jpa.repository.Query;
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

    Vacancy findByRecCenterIdAndTimeslot(
            Integer recCenterId,
            LocalDateTime  timeslot
    );

    /**
     * Selects all available vacancies where given
     * user's has a wait-listed booking.
     */
    @Query(
            value = "SELECT * FROM VACANCY v " +
                    "INNER JOIN BOOKING b " +
                    "ON b.reccenterid = v.reccenterid AND b.timeslot = v.timeslot " +
                    "WHERE b.userid = ?1 AND b.iswaitlist = TRUE AND v.numvacant > 0 AND b.timeslot > current_timestamp ",
            nativeQuery = true)
    List<Vacancy> findUserWaitListBecomesAvailable(
            Long userId
    );
}
