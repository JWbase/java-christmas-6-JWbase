package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constant.DiscountPolicyName;
import christmas.domain.menu.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import christmas.util.EventDateUtil;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekdayDiscountPolicyTest {
    private WeekdayDiscountPolicy weekdayDiscountPolicy;
    Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        menus = new EnumMap<>(Menu.class);
        weekdayDiscountPolicy = new WeekdayDiscountPolicy();
    }

    @DisplayName("평일에 디저트를 주문할 경우 개당 2,023원씩 할인된다.")
    @Test
    void discountOnWeekdayAndDessertOrderTest() {
        Date date = new Date(4);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        menus.put(Menu.CHOCOLATE_CAKE, 1);
        menus.put(Menu.ICE_CREAM, 1);
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekdayDiscountPolicy.discount(orderDto);
        assertThat(discount).isEqualTo(4046);
    }

    @DisplayName("평일에 디저트가 아닌 메뉴를 주문할 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountOnWeekdayAndNonDessertOrderTest() {
        Date date = new Date(4);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        menus.put(Menu.T_BONE_STEAK, 1);
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekdayDiscountPolicy.discount(orderDto);
        assertThat(discount).isZero();
    }

    @DisplayName("주말에 주문할 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountOnWeekendTest() {
        Date date = new Date(2);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        menus.put(Menu.CHOCOLATE_CAKE, 1);
        menus.put(Menu.ICE_CREAM, 1);
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekdayDiscountPolicy.discount(orderDto);
        assertThat(discount).isZero();
    }

    @DisplayName("이벤트 기간이 아닌 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountOutOfRangeTest() {
        LocalDate localDate = LocalDate.of(2023, 11, 7);
        menus.put(Menu.CHOCOLATE_CAKE, 1);
        menus.put(Menu.ICE_CREAM, 1);
        menus.put(Menu.T_BONE_STEAK, 1);
        menus.put(Menu.BBQ_RIB, 3);
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = weekdayDiscountPolicy.discount(orderDto);
        assertThat(discount).isZero();
    }

    @DisplayName("이벤트 명은 평일 할인 이다.")
    @Test
    void discountPolicyNameTest() {
        DiscountPolicyName discountPolicyName = weekdayDiscountPolicy.getDiscountPolicyName();

        assertThat(discountPolicyName).isEqualTo(DiscountPolicyName.WEEKDAY_DISCOUNT);
    }
}