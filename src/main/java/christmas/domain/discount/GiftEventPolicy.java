package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.domain.Menu;
import christmas.domain.order.Order;
import java.time.LocalDate;

public class GiftEventPolicy implements DiscountPolicy {
    private static final int MINIMUM_AMOUNT_FOR_GIFT = 120000;

    @Override
    public int discount(Order order) {
        LocalDate orderDate = order.getDate();
        if (isWithinDiscountPeriod(orderDate) && isOrderAboveThreshold(order)) {
            order.addGiftItem(Menu.CHAMPAGNE);
            return Menu.CHAMPAGNE.getPrice();
        }
        return DiscountConstants.NO_DISCOUNT;
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

    private boolean isOrderAboveThreshold(Order order) {
        return order.calculateBeforeDiscountTotalPrice() > MINIMUM_AMOUNT_FOR_GIFT;
    }
}