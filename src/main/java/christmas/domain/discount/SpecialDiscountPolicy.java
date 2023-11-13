package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.service.dto.OrderDto;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class SpecialDiscountPolicy implements DiscountPolicy {
    private static final int SPECIAL_DISCOUNT_AMOUNT = 1000;

    @Override
    public int discount(final OrderDto order) {
        LocalDate orderDate = order.getDate();
        if (isWithinDiscountPeriod(orderDate) && isSpecialDiscountDay(orderDate)) {
            return SPECIAL_DISCOUNT_AMOUNT;
        }
        return DiscountConstants.NO_DISCOUNT;
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.SPECIAL_DISCOUNT;
    }

    private boolean isWithinDiscountPeriod(LocalDate date) {
        return isNotBeforeStartDate(date) && isNotAfterEndDate(date);
    }

    private boolean isNotBeforeStartDate(LocalDate date) {
        return !date.isBefore(DiscountConstants.START_DAY_OF_MONTH);
    }

    private boolean isNotAfterEndDate(LocalDate date) {
        return !date.isAfter(DiscountConstants.END_DAY_OF_MONTH);
    }

    private boolean isSpecialDiscountDay(LocalDate date) {
        return isSunday(date) || isChristmas(date);
    }

    private boolean isSunday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isChristmas(LocalDate date) {
        return date.equals(DiscountConstants.CHRISTMAS);
    }
}