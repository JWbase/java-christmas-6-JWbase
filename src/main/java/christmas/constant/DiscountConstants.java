package christmas.constant;

import java.time.LocalDate;

public final class DiscountConstants {
    public static final LocalDate START_DAY_OF_MONTH = LocalDate.of(2023, 12, 1);
    public static final LocalDate END_DAY_OF_MONTH = LocalDate.of(2023, 12, 31);
    public static final LocalDate CHRISTMAS = LocalDate.of(2023, 12, 25);
    public static final int NO_DISCOUNT = 0;

    private DiscountConstants() {
    }
}
