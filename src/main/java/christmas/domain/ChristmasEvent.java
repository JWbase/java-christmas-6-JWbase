package christmas.domain;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.discount.ChristmasDailyDiscountPolicy;
import christmas.domain.discount.DiscountPolicy;
import christmas.domain.discount.GiftEventPolicy;
import christmas.domain.discount.SpecialDiscountPolicy;
import christmas.domain.discount.WeekdayDiscountPolicy;
import christmas.domain.discount.WeekendDiscountPolicy;
import christmas.service.dto.OrderDto;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChristmasEvent {
    private static final int MINIMUM_AMOUNT_FOR_DISCOUNT = 10000;
    private static final int MINIMUM_AMOUNT_FOR_GIFT = 120000;
    private final List<DiscountPolicy> discountPolicies;
    private final GiftEventPolicy giftEventPolicy;

    public ChristmasEvent() {
        this.discountPolicies = List.of(
                new ChristmasDailyDiscountPolicy(),
                new WeekdayDiscountPolicy(),
                new WeekendDiscountPolicy(),
                new SpecialDiscountPolicy());
        this.giftEventPolicy = new GiftEventPolicy();
    }

    public Map<DiscountPolicyName, Integer> calculateBenefitDetails(OrderDto order) {
        Map<DiscountPolicyName, Integer> benefitDetail = new EnumMap<>(DiscountPolicyName.class);
        if (order.getTotalPrice() >= MINIMUM_AMOUNT_FOR_DISCOUNT) {
            benefitDetail = applyDiscountPolicies(order);
            addGift(order, benefitDetail);
        }
        return benefitDetail;
    }

    public int calculateTotalDiscount(OrderDto order) {
        Map<DiscountPolicyName, Integer> discountDetail = calculateBenefitDetails(order);
        return discountDetail.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Optional<Menu> getGiftMenu(OrderDto order) {
        if (order.getTotalPrice() >= MINIMUM_AMOUNT_FOR_GIFT) {
            return Optional.of(Menu.CHAMPAGNE);
        }
        return Optional.empty();
    }

    private void addGift(OrderDto order, Map<DiscountPolicyName, Integer> benefitDetail) {
        if (order.getTotalPrice() >= MINIMUM_AMOUNT_FOR_GIFT) {
            int giftPrice = giftEventPolicy.discountGiftPrice(order);
            benefitDetail.put(DiscountPolicyName.GIFT_EVENT, giftPrice);
        }
    }

    private Map<DiscountPolicyName, Integer> applyDiscountPolicies(OrderDto order) {
        Map<DiscountPolicyName, Integer> discountDetail = new EnumMap<>(DiscountPolicyName.class);
        for (DiscountPolicy discountPolicy : discountPolicies) {
            int discountAmount = discountPolicy.discount(order);
            if (discountAmount != DiscountConstants.NO_DISCOUNT) {
                discountDetail.put(discountPolicy.getDiscountPolicyName(), discountAmount);
            }
        }
        return discountDetail;
    }
}