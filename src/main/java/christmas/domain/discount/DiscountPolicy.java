package christmas.domain.discount;

import christmas.constant.DiscountPolicyName;
import christmas.service.dto.DiscountPolicyDto;

public interface DiscountPolicy {

    int discount(DiscountPolicyDto discountPolicyDto);

    DiscountPolicyName getDiscountPolicyName();
}