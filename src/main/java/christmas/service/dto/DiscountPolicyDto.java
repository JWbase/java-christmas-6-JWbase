package christmas.service.dto;

import christmas.domain.Menu;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class DiscountPolicyDto {
    private final LocalDate orderDate;
    private final Map<Menu, Integer> menus;
    private final int totalPrice;

    public DiscountPolicyDto(LocalDate orderDate, Map<Menu, Integer> menus, int totalPrice) {
        this.orderDate = orderDate;
        this.menus = menus;
        this.totalPrice = totalPrice;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Map<Menu, Integer> getMenus() {
        return Collections.unmodifiableMap(menus);
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
