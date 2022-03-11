package edu.usc.csci310.repository;

import edu.usc.csci310.model.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {


}
