package christmas.util;

import java.time.LocalDate;

public class EventDateUtil {
    private static final int YEAR = 2023;
    private static final int MONTH = 12;

    private EventDateUtil() {
    }

    public static LocalDate getLocalDateFromDay(final int day) {
        return LocalDate.of(YEAR, MONTH, day);
    }
}
