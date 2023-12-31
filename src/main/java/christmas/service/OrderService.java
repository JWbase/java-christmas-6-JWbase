package christmas.service;

import christmas.constant.DiscountPolicyName;
import christmas.domain.ChristmasEvent;
import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import java.time.LocalDate;
import java.util.Map;

public class OrderService {
    private final ChristmasEvent christmasEvent;

    public OrderService(final ChristmasEvent christmasEvent) {
        this.christmasEvent = christmasEvent;
    }

    public OrderDto convertToDto(final Order order) {
        return new OrderDto(order);
    }

    public Order createOrder(final LocalDate date, final Map<Menu, Integer> menus) {
        return new Order(date, menus);
    }

    public Map<DiscountPolicyName, Integer> getDiscountDetails(final OrderDto order) {
        return christmasEvent.calculateBenefitDetails(order);
    }

    public int getTotalBenefitAmount(final OrderDto order) {
        return christmasEvent.calculateTotalDiscount(order);
    }

    public Map<Menu, Integer> getGiftMenu(final OrderDto order) {
        return christmasEvent.getGiftMenu(order);
    }

    public int calculatePaymentDue(final OrderDto order) {
        Map<DiscountPolicyName, Integer> discountPolicy = getDiscountDetails(order);
        return discountPolicy.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(DiscountPolicyName.GIFT_EVENT))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }
}