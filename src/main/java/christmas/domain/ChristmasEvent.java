package christmas.domain;

import christmas.domain.discount.ChristmasDailyDiscountPolicy;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.SpecialDiscountPolicy;
import christmas.domain.discount.WeekdayDiscountPolicy;
import christmas.domain.discount.WeekendDiscountPolicy;
import christmas.domain.order.Order;
import christmas.service.dto.DiscountPolicyDto;
import java.util.List;

public class ChristmasEvent {
    private static final int MINIMUM_AMOUNT_FOR_DISCOUNT = 10000;
    private List<DiscountPolicy> discountPolicies;

    public ChristmasEvent() {
        this.discountPolicies = List.of(
                new ChristmasDailyDiscountPolicy(),
                new WeekdayDiscountPolicy(),
                new WeekendDiscountPolicy(),
                new SpecialDiscountPolicy());
    }

    public int calculateDiscount(DiscountPolicyDto discountPolicyDto) {
        if (discountPolicyDto.getTotalPrice() > MINIMUM_AMOUNT_FOR_DISCOUNT) {
            int totalDiscountAmount = 0;
            for (DiscountPolicy discountPolicy : discountPolicies) {
                totalDiscountAmount += discountPolicy.discount(discountPolicyDto);
            }
            return totalDiscountAmount;
        }
        return 0;
    }
}
