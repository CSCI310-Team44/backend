package edu.usc.csci310.model;

import edu.usc.csci310.util.DateTimeInflater;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Non-persisting model. Contains RecCenter info,
 * such as daily operating hours, vacancy, etc.
 */
public class RecCenter {

    // ==================== RecCenter Constants ====================

    public enum Name {
        LYON_CENTER(0),
        VILLAGE_CENTER(1),
        HSC_CENTER(2);

        public final int value;

        private Name(int value) {
            this.value = value;
        }
    };

    private static final RecCenter LYON_CENTER = new RecCenter(
            "Lyon Center",
            Arrays.asList(
                    HourRange.LYOMF,
                    HourRange.LYOMF,
                    HourRange.LYOMF,
                    HourRange.LYOMF,
                    HourRange.LYOMF,
                    HourRange.LYOSS,
                    HourRange.LYOSS),
            50
    );

    private static final RecCenter VILLAGE_CENTER = new RecCenter(
            "Village Center",
            Arrays.asList(
                    HourRange.VLGMF,
                    HourRange.VLGMF,
                    HourRange.VLGMF,
                    HourRange.VLGMF,
                    HourRange.VLGMF,
                    HourRange.VLGSS,
                    HourRange.VLGSS),
            50
    );

    private static final RecCenter HSC_CENTER = new RecCenter(
            "HSC Center",
            Arrays.asList(
                    HourRange.HSCMF,
                    HourRange.HSCMF,
                    HourRange.HSCMF,
                    HourRange.HSCMF,
                    HourRange.HSCMF,
                    HourRange.HSCSA,
                    HourRange.CLOSED),
            50
    );

    private static final List<RecCenter> recCenters = Arrays.asList(
            LYON_CENTER, VILLAGE_CENTER, HSC_CENTER);

    public static RecCenter getRecCenter(RecCenter.Name center) {
        return recCenters.get(center.value);
    }

    public static RecCenter getRecCenter(int center) {
        return recCenters.get(center);
    }

    // ==================== RecCenter Impl ====================

    private enum HourRange {
        CLOSED(0, 0, 0, 0),
        LYOMF(6, 0, 22, 0),
        LYOSS(10, 0, 18, 30),
        VLGMF(11, 0, 23, 59),
        VLGSS(15, 0, 21, 15),
        HSCMF(6, 0, 21, 0),
        HSCSA(9, 0, 13, 0);

        public final LocalTime open;
        public final LocalTime close;

        private HourRange(int hh1, int mm1, int hh2, int mm2) {
            this.open = LocalTime.of(hh1, mm1);
            this.close = LocalTime.of(hh2, mm2);
        }
    }

    private final String name;
    private final List<List<LocalTime>> operatingHours;
    private final int defaultVacancy;

    public String getName() {
        return name;
    }

    /**
     * Returns the operating hours for a given date.
     */
    public List<LocalTime> getOperatingHoursOfDate(LocalDate date) {
        // DayOfWeek starts from 1 ...
        return operatingHours.get(date.getDayOfWeek().getValue() - 1);
    }

    public int getDefaultVacancy() {
        return defaultVacancy;
    }

    /**
     * Constructor.
     *
     * @param name Recreation center name.
     * @param hourRanges Operating hours for each day of the week
     * @param defaultVacancy Recreation center default vacancy.
     */
    private RecCenter(String name, List<HourRange> hourRanges, int defaultVacancy) {
        this.name = name;

        this.operatingHours = new ArrayList<>();
        for(HourRange ea : hourRanges) {
            this.operatingHours.add(new DateTimeInflater(ea.open, ea.close).generate(3600));
        }

        this.defaultVacancy = defaultVacancy;
    }
}
