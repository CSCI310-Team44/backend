package edu.usc.csci310.service;

import edu.usc.csci310.model.Vacancy;
import edu.usc.csci310.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class VacancyService {

    // Schedule 3 days in advance
    private static final int SCHEDULE_IN_ADVANCE = 3;

    @Autowired
    private VacancyRepository vacancyRepository;

    /**
     * Adds vacancy of a given center at a given date to database.
     * @param center
     * @param date
     */
    public void addVacancy(RecCenterData.Name center, LocalDate date) {
        RecCenterData.OperatingInfo info = RecCenterData.getOperatingInfo(center, date);
        List<Vacancy> vacancies = getHourlyVacancies(info);
        vacancyRepository.saveAll(vacancies);
    }

    /**
     * Automatic scheduler. At 00:05:00 daily, adds all vacancies
     * of all centers at date in advance.
     */
    @Scheduled(cron = "00 05 00 * * *")
    public void scheduleAddVacancy() {
        for(RecCenterData.Name center : RecCenterData.Name.values()) {
            addVacancy(center, LocalDate.now().plusDays(SCHEDULE_IN_ADVANCE));
            // TODO: Remove break once RecCenter has been completely implemented
            break;
        }
    }

    public void populateDatabase() {
        for(RecCenterData.Name center : RecCenterData.Name.values()) {
            System.out.println(center);
        }
    }

    /**
     * From a recreation center's operating info:
     * open time, close time, numVacant,
     * generate {@link Vacancy} entities for one day.
     * @param operatingInfo
     * @return
     */
    private static List<Vacancy> getHourlyVacancies(RecCenterData.OperatingInfo operatingInfo) {
        List<Vacancy> retVal = new ArrayList<>();

        LocalDateTime current = operatingInfo.open;
        while(current.isBefore(operatingInfo.close)) {
            Vacancy vacancy = new Vacancy(
                    (long)0,
                    operatingInfo.center.value,
                    current,
                    operatingInfo.numVacant
            );
            retVal.add(vacancy);
            current = current.plusHours(1);
        }

        return retVal;
    }
}
