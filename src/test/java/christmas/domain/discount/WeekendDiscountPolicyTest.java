package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekendDiscountPolicyTest {
    private WeekendDiscountPolicy weekendDiscountPolicy;
    Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        menus = new EnumMap<>(Menu.class);
        weekendDiscountPolicy = new WeekendDiscountPolicy();
    }

    @DisplayName("주말에 메인 메뉴를 주문할 경우 개당 2,023원씩 할인된다.")
    @Test
    void discountOnWeekdayAndDessertOrderTest() {
        Date date = new Date(16);
        menus.put(Menu.T_BONE_STEAK, 1);
        menus.put(Menu.BBQ_RIB, 3);
        Order order = new Order(date, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekendDiscountPolicy.discount(orderDto);
        assertThat(discount).isEqualTo(8092);
    }

    @DisplayName("주말에 메인 메뉴가 아닌 메뉴를 주문할 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountOnWeekdayAndNonDessertOrderTest() {
        Date date = new Date(2);
        menus.put(Menu.CAESAR_SALAD, 1);
        Order order = new Order(date, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekendDiscountPolicy.discount(orderDto);
        assertThat(discount).isZero();
    }

    @DisplayName("평일에 주문할 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountOnWeekendTest() {
        Date date = new Date(4);
        menus.put(Menu.CHOCOLATE_CAKE, 1);
        menus.put(Menu.ICE_CREAM, 1);
        menus.put(Menu.T_BONE_STEAK, 1);
        menus.put(Menu.BBQ_RIB, 3);
        Order order = new Order(date, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekendDiscountPolicy.discount(orderDto);
        assertThat(discount).isZero();
    }
}