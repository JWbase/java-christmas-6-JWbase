package christmas.domain;

public class Date {
    private static final int MINIMUM_DATE = 1;
    private static final int MAXIMUM_DATE = 31;
    private static final String INVALID_DATE_MESSAGE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private final int date;

    public Date(int date) {
        validate(date);
        this.date = date;
    }

    private void validate(int date) {
        validateRange(date);
    }

    private void validateRange(int date) {
        if (date < MINIMUM_DATE || date > MAXIMUM_DATE) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    public int getDate() {
        return date;
    }
}
