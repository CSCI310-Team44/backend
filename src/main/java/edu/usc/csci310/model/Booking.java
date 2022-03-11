package edu.usc.csci310.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingid", nullable = false, unique = true)
    private Long bookingId;
    @Column(name = "userid", nullable = false)
    private Long userId;
    @Column(name = "reccenterid", nullable = false)
    private Integer recCenterId;
    @Column(name = "timeslot", nullable = false)
    private LocalDateTime timeslot;
    @Column(name = "iswaitlist", nullable = false)
    private Boolean isWaitList;
}
