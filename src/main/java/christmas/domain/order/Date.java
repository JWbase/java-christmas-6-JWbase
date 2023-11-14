package christmas.domain.order;

import christmas.util.EventDateUtil;
import java.time.LocalDate;

public class Date {
    private static final int MINIMUM_DATE = 1;
    private static final int MAXIMUM_DATE = 31;
    private static final String INVALID_DATE_MESSAGE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private final LocalDate localDate;

    public Date(final int localDate) {
        validate(localDate);
        this.localDate = EventDateUtil.getLocalDateFromDay(localDate);
    }

    private void validate(final int date) {
        validateRange(date);
    }

    private void validateRange(final int date) {
        if (date < MINIMUM_DATE || date > MAXIMUM_DATE) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
