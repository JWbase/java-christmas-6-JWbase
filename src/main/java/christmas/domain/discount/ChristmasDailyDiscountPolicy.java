package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.domain.order.Order;
import java.time.LocalDate;

public class ChristmasDailyDiscountPolicy implements DiscountPolicy {

    private static final int INITIAL_DISCOUNT_AMOUNT = 1000;
    private static final int INCREMENT_AMOUNT_PER_DAY = 100;

    @Override
    public int discount(Order order) {
        LocalDate orderDate = order.getDate();

        if (isWithinDiscountPeriod(orderDate)) {
            return calculateDiscountAmount(orderDate);
        }
        return DiscountConstants.NO_DISCOUNT;
    }

    private int calculateDiscountAmount(LocalDate date) {
        return INITIAL_DISCOUNT_AMOUNT + calculateIncrementAmount(date);
    }

    private int calculateIncrementAmount(LocalDate date) {
        return INCREMENT_AMOUNT_PER_DAY * (date.getDayOfMonth() - 1);
    }

    private boolean isWithinDiscountPeriod(LocalDate date) {
        return isNotBeforeStartDate(date) && isNotAfterChristmas(date);
    }

    private boolean isNotBeforeStartDate(LocalDate date) {
        return !date.isBefore(DiscountConstants.START_DAY_OF_MONTH);
    }

    private boolean isNotAfterChristmas(LocalDate date) {
        return !date.isAfter(DiscountConstants.CHRISTMAS);
    }
}