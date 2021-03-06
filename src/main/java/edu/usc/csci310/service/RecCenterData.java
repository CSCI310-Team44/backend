package edu.usc.csci310.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import static edu.usc.csci310.service.RecCenterData.OP_HOUR.*;

/**
 * Recreation center data, such as
 * daily operating hours, vacancies.
 * <p>
 * Should contain only constants, and retrieval methods.
 */
@Deprecated
public final class RecCenterData {

    public enum Name {
        LYON_CENTER(0),
        VILLAGE_CENTER(1),
        HSC_CENTER(2);

        public final int value;

        private Name(int value) {
            this.value = value;
        }
    };

    /**
     * Daily operating hours. Stored as a time range.
     */
    enum OP_HOUR {
        CLOSED(0, 0, 0, 0),
        LYONMF(6, 0, 18, 0),
        LYONSS(10, 0, 17, 0);

        public final LocalTime open;
        public final LocalTime close;

        private OP_HOUR(int hh1, int mm1, int hh2, int mm2) {
            this.open = LocalTime.of(hh1, mm1);
            this.close = LocalTime.of(hh2, mm2);
        }
    }

    /**
     * Weekly operating hours.
     */
    private static final Map<Name, OP_HOUR[]> OPERATING_HOURS = Map.ofEntries(
            Map.entry(
                    Name.LYON_CENTER,
                    new OP_HOUR[]{LYONMF, LYONMF, LYONMF, LYONMF, LYONMF, OP_HOUR.LYONSS, OP_HOUR.LYONSS}
            )
    );

    /**
     * Default vacancy.
     */
    private static final Map<Name, Integer> NUM_VACANT = Map.ofEntries(
            Map.entry(
                    Name.LYON_CENTER,
                    50
            )
    );

    /**
     * Operating information.
     */
    public static final class OperatingInfo {
        public final Name center;
        public final LocalDateTime open;
        public final LocalDateTime close;
        public final int numVacant;

        public OperatingInfo(Name center, LocalDateTime open, LocalDateTime close, int numVacant) {
            this.center = center;
            this.open = open;
            this.close = close;
            this.numVacant = numVacant;
        }
    }

    /**
     * Returns given recreation center operating information for given date.
     * @param center
     * @param date
     * @return List of operating hours in 1 hour interval.
     */
    public static OperatingInfo getOperatingInfo(Name center, LocalDate date) {
        OP_HOUR hours = OPERATING_HOURS.get(center)[date.getDayOfWeek().getValue()];
        int vacancy = NUM_VACANT.get(center);
        return new OperatingInfo(
                center,
                LocalDateTime.of(date, hours.open),
                LocalDateTime.of(date, hours.close),
                vacancy
        );
    }

    private RecCenterData() {
    }
}
