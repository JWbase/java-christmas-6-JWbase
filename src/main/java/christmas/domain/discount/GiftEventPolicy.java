package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.Menu;
import christmas.service.dto.DiscountPolicyDto;
import java.time.LocalDate;

public class GiftEventPolicy implements DiscountPolicy {
    private static final int MINIMUM_AMOUNT_FOR_GIFT = 120000;

    @Override
    public int discount(DiscountPolicyDto discountPolicyDto) {
        LocalDate orderDate = discountPolicyDto.getOrderDate();
        if (isWithinDiscountPeriod(orderDate) && isOrderAboveThreshold(discountPolicyDto)) {
            return Menu.CHAMPAGNE.getPrice();
        }
        return DiscountConstants.NO_DISCOUNT;
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.GIFT_EVENT;
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

    private boolean isOrderAboveThreshold(DiscountPolicyDto discountPolicyDto) {
        return discountPolicyDto.getTotalPrice() > MINIMUM_AMOUNT_FOR_GIFT;
    }
}