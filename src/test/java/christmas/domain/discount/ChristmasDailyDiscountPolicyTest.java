package christmas.domain.discount;

import christmas.domain.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.dto.DiscountPolicyDto;
import christmas.util.EventDateUtil;
import java.util.EnumMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChristmasDailyDiscountPolicyTest {

    @DisplayName("12월 16일이면 할인금액은 2500원이다.")
    @Test
    void christmasDailyDiscount() {
        Map<Menu, Integer> orderMenus = new EnumMap<>(Menu.class);
        orderMenus.put(Menu.findByName(Menu.BBQ_RIB.getName()), 4);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(new Date(16).getDate()), orderMenus);
        DiscountPolicyDto discountPolicyDto = new DiscountPolicyDto(order.getDate(), order.getMenus(),
                order.calculateBeforeDiscountTotalPrice());
        int discount = new ChristmasDailyDiscountPolicy().discount(discountPolicyDto);

        Assertions.assertThat(discount).isEqualTo(2500);
    }

    @DisplayName("12월 26일 이후 할인 금액은 0원 이다.")
    @Test
    void noDiscount() {
        Map<Menu, Integer> orderMenus = new EnumMap<>(Menu.class);
        orderMenus.put(Menu.findByName(Menu.BBQ_RIB.getName()), 4);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(new Date(26).getDate()), orderMenus);
        DiscountPolicyDto discountPolicyDto = new DiscountPolicyDto(order.getDate(), order.getMenus(),
                order.calculateBeforeDiscountTotalPrice());
        int discount = new ChristmasDailyDiscountPolicy().discount(discountPolicyDto);

        Assertions.assertThat(discount).isZero();
    }
}