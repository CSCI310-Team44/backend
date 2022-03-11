package edu.usc.csci310.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacancyid", nullable = false, unique = true)
    private Long vacancyId;
    @Column(name = "reccenterid", nullable = false)
    private Integer recCenterId;
    @Column(name = "timeslot", nullable = false)
    private LocalDateTime timeslot;

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Integer getRecCenterId() {
        return recCenterId;
    }

    public void setRecCenterId(Integer recCenterId) {
        this.recCenterId = recCenterId;
    }

    public LocalDateTime getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(LocalDateTime timeSlot) {
        this.timeslot = timeSlot;
    }

    public Integer getNumVacant() {
        return numVacant;
    }

    public void setNumVacant(Integer numVacant) {
        this.numVacant = numVacant;
    }

    @Column(name = "numvacant", nullable = false)
    private Integer numVacant;

    public Vacancy(Long vacancyId, Integer recCenterId, LocalDateTime timeslot, Integer numVacant) {
        this.vacancyId = vacancyId;
        this.recCenterId = recCenterId;
        this.timeslot = timeslot;
        this.numVacant = numVacant;
    }

    public Vacancy() {
    }
}
