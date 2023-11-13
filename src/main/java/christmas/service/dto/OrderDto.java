package christmas.service.dto;

import christmas.domain.Menu;
import christmas.domain.order.Order;
import java.time.LocalDate;
import java.util.Map;

public class OrderDto {
    private final LocalDate date;
    private final Map<Menu, Integer> menus;
    private final int totalPrice;

    public OrderDto(Order order) {
        this.date = order.getDate();
        this.menus = order.getMenus();
        this.totalPrice = order.calculateBeforeDiscountTotalPrice();
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Menu, Integer> getMenus() {
        return menus;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}