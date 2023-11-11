package christmas.domain.discount;

import christmas.domain.order.Order;

public interface DiscountPolicy {

    int discount(Order order);
}
