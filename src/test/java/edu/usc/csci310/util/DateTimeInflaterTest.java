package edu.usc.csci310.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DateTimeInflaterTest {

    /**
     * Generates every timestamp from 00:00 to 23:59 with 30 min interval.
     */
    @Test
    public void generateEveryHalfHourBetween0000And2359() {
        DateTimeInflater<LocalTime> dtf = new DateTimeInflater<>(
                LocalTime.of(0, 0),
                LocalTime.of(23, 59)
        );
        List<LocalTime> gen = dtf.generate(1800);

        assertEquals(48, gen.size());
        assertEquals(LocalTime.of(0, 0), gen.get(0));
        assertEquals(LocalTime.of(23, 30), gen.get(gen.size() - 1));
    }

    /**
     * Generates nothing if start is before end.
     */
    @Test
    public void generateStartBeforeEnd() {
        DateTimeInflater<LocalTime> dtf = new DateTimeInflater<>(
                LocalTime.of(23, 0),
                LocalTime.of(22, 0)
        );
        List<LocalTime> gen = dtf.generate(1);

        assertEquals(0, gen.size());
    }
}
