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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GiftEventPolicyTest {
    private GiftEventPolicy giftEventPolicy;
    private Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        giftEventPolicy = new GiftEventPolicy();
        menus = new EnumMap<>(Menu.class);
        menus.put(Menu.T_BONE_STEAK, 1);
    }

    @DisplayName("이벤트 기간내에 샴페인 가격만큼 할인액을 반환 한다.")
    @Test
    void DiscountGiftPriceWithinPeriod() {
        Date date = new Date(1);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discountGiftPrice = giftEventPolicy.discountGiftPrice(orderDto);

        Assertions.assertThat(discountGiftPrice).isEqualTo(Menu.CHAMPAGNE.getPrice());
    }

    @DisplayName("이벤트 기간이 아니면 할인을 하지 않는다.")
    @Test
    void DiscountGiftPriceWithoutPeriod() {
        LocalDate localDate = LocalDate.of(2023, 11, 1);
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discountGiftPrice = giftEventPolicy.discountGiftPrice(orderDto);

        Assertions.assertThat(discountGiftPrice).isZero();
    }

    @DisplayName("이벤트 명은 증정 이벤트 다.")
    @Test
    void discountPolicyNameTest() {
        DiscountPolicyName discountPolicyName = giftEventPolicy.getDiscountPolicyName();

        assertThat(discountPolicyName).isEqualTo(DiscountPolicyName.GIFT_EVENT);
    }
}