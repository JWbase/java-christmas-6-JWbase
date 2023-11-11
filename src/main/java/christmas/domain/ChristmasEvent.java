package christmas.domain;

import christmas.domain.discount.ChristmasDailyDiscountPolicy;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.SpecialDiscountPolicy;
import christmas.domain.discount.WeekdayDiscountPolicy;
import christmas.domain.discount.WeekendDiscountPolicy;
import christmas.domain.order.Order;
import java.util.List;

public class ChristmasEvent {
    private List<DiscountPolicy> discountPolicies;

    public ChristmasEvent() {
        this.discountPolicies = List.of(
                new ChristmasDailyDiscountPolicy(),
                new WeekdayDiscountPolicy(),
                new WeekendDiscountPolicy(),
                new SpecialDiscountPolicy());
    }

    public int calculateDiscount(Order order) {
        if (order.calculateBeforeDiscountTotalPrice() > 10000) {
            int totalDiscountAmount = 0;
            for (DiscountPolicy discountPolicy : discountPolicies) {
                totalDiscountAmount += discountPolicy.discount(order);
            }
            return totalDiscountAmount;
        }
        return 0;
    }
}
