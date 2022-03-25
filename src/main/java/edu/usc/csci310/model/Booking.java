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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void setTimeslot(LocalDateTime timeslot) {
        this.timeslot = timeslot;
    }

    public Boolean getWaitList() {
        return isWaitList;
    }

    public void setWaitList(Boolean waitList) {
        isWaitList = waitList;
    }

    public Booking(Long bookingId, Long userId, Integer recCenterId, LocalDateTime timeslot, Boolean isWaitList) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.recCenterId = recCenterId;
        this.timeslot = timeslot;
        this.isWaitList = isWaitList;
    }

    public Booking() {
    }
}
