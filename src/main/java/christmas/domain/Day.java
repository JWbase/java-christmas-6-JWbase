package christmas.domain;

public class Day {
    private final int day;

    public Day(int day) {
        validate(day);
        this.day = day;
    }

    private void validate(int day) {
        validateRange(day);
    }

    private void validateRange(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }
}
