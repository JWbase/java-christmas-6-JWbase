package christmas.service;

import christmas.constant.DiscountPolicyName;
import christmas.domain.ChristmasEvent;
import christmas.domain.Menu;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import java.util.Map;
import java.util.Optional;

public class OrderService {
    private final ChristmasEvent christmasEvent;

    public OrderService(final ChristmasEvent christmasEvent) {
        this.christmasEvent = christmasEvent;
    }

    public OrderDto convertToDto(Order order) {
        return new OrderDto(order);
    }

    public Map<DiscountPolicyName, Integer> getDiscountDetails(final OrderDto order) {
        return christmasEvent.calculateBenefitDetails(order);
    }

    public int getTotalBenefitAmount(OrderDto order) {
        return christmasEvent.calculateTotalDiscount(order);
    }

    public Optional<Menu> getGiftMenu(OrderDto order) {
        return christmasEvent.getGiftMenu(order);
    }

    public int calculatePaymentDue(OrderDto order) {
        Map<DiscountPolicyName, Integer> discountPolicy = getDiscountDetails(order);
        discountPolicy.remove(DiscountPolicyName.GIFT_EVENT);

        return discountPolicy.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}