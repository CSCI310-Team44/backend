package edu.usc.csci310.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates {@link Temporal} instances, i.e.,
 * {@link LocalDateTime}, for a given time range.
 */
public class DateTimeInflater<D extends Temporal> {
    private final D begin;
    private final D end;

    /**
     * Generates a {@link Temporal} instance for
     * every interval within a time range.
     * @param interval in seconds
     */
    public List<D> generate(int interval) {
        List<D> lldt = new ArrayList<>();

        D current = begin;

        long difference = current.until(end, ChronoUnit.SECONDS);

        for(int i = 0; i < difference; i += interval) {
            lldt.add(current);
            current = (D) current.plus(interval, ChronoUnit.SECONDS);
        }

        return lldt;
    }

    /**
     * Generates {@link Temporal} instances that are
     * within the given time range.
     * @param begin inclusive
     * @param end exclusive
     */
    public DateTimeInflater(D begin, D end) {
        this.begin = begin;
        this.end = end;
    }
}
