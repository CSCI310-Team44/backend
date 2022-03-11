package edu.usc.csci310.entity;

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
    private LocalDateTime timeSlot;
    @Column(name = "numvacant", nullable = false)
    private Integer numVacant;
}
