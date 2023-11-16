package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.menu.Menu;
import christmas.service.dto.OrderDto;
import java.time.LocalDate;

public class GiftEventPolicy {

    public int discountGiftPrice(final OrderDto order) {
        LocalDate orderDate = order.getDate();
        if (isWithinDiscountPeriod(orderDate)) {
            return Menu.CHAMPAGNE.getPrice();
        }
        return DiscountConstants.NO_DISCOUNT;
    }

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
}