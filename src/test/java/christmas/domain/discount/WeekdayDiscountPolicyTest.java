package christmas.domain.discount;

import christmas.domain.menu.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import christmas.util.EventDateUtil;
import java.util.EnumMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WeekdayDiscountPolicyTest {

    @DisplayName("평일에 디저트 1개당 2023원을 할인 한다.")
    @Test
    void christmasDailyDiscount() {
        Map<Menu, Integer> orderMenus = new EnumMap<>(Menu.class);
        orderMenus.put(Menu.findByName(Menu.CHOCOLATE_CAKE.getName()), 4);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(new Date(18).getDate()), orderMenus);
        OrderDto orderDto = new OrderDto(order);
        int discount = new WeekdayDiscountPolicy().discount(orderDto);

        Assertions.assertThat(discount).isEqualTo(2023 * 4);
    }
}