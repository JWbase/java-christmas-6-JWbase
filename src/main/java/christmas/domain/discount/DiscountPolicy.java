package christmas.domain.discount;

import christmas.constant.DiscountPolicyName;
import christmas.service.dto.OrderDto;

public interface DiscountPolicy {

    int discount(final OrderDto order);

    DiscountPolicyName getDiscountPolicyName();
}