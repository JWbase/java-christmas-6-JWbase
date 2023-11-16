package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constant.DiscountPolicyName;
import christmas.domain.menu.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import christmas.util.EventDateUtil;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChristmasEventTest {
    private ChristmasEvent christmasEvent;
    private Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        christmasEvent = new ChristmasEvent();
        menus = new EnumMap<>(Menu.class);
    }

    @DisplayName("총 주문 금액이 10,000원 이상 이면 할인 혜택이 적용된다.")
    @Test
    void applyDiscountPolicyTest() {
        menus.put(Menu.ICE_CREAM, 2);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);

        Map<DiscountPolicyName, Integer> benefitDetails = christmasEvent.calculateBenefitDetails(
                orderDto);

        assertThat(benefitDetails).isNotEmpty();
    }

    @DisplayName("총 주문 금액이 10,000원 미만 이면 할인 혜택이 적용되지 않는다.")
    @Test
    void notApplyDiscountPolicyTest() {
        menus.put(Menu.ICE_CREAM, 1);
        menus.put(Menu.ZERO_COLA, 1);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);

        Map<DiscountPolicyName, Integer> benefitDetails = christmasEvent.calculateBenefitDetails(
                orderDto);

        assertThat(benefitDetails).isEmpty();
    }

    @DisplayName("총 주문 금액이 120,000원 이상 이면 증정 이벤트를 적용한다.")
    @Test
    void applyGiftDiscountTest() {
        menus.put(Menu.T_BONE_STEAK, 2);
        menus.put(Menu.ICE_CREAM, 2);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);

        Map<DiscountPolicyName, Integer> benefitDetails = christmasEvent.calculateBenefitDetails(
                orderDto);

        assertThat(benefitDetails).isNotEmpty();
        assertThat(benefitDetails.get(DiscountPolicyName.GIFT_EVENT)).isNotZero();
    }

    @DisplayName("총 주문 금액이 120,000원 미만 이면 증정 이벤트를 적용하지 않는다.")
    @Test
    void notApplyGiftDiscountTest() {
        menus.put(Menu.T_BONE_STEAK, 2);
        menus.put(Menu.ICE_CREAM, 1);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);
        Map<DiscountPolicyName, Integer> benefitDetails = christmasEvent.calculateBenefitDetails(
                orderDto);

        assertThat(benefitDetails).isNotEmpty();
        assertThat(benefitDetails.get(DiscountPolicyName.GIFT_EVENT)).isNull();
    }

    @DisplayName("증정 이벤트 상품은 샴페인 1개다.")
    @Test
    void getGiftMenuTest() {
        menus.put(Menu.T_BONE_STEAK, 2);
        menus.put(Menu.ICE_CREAM, 2);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);
        Map<Menu, Integer> giftMenu = christmasEvent.getGiftMenu(orderDto);

        assertThat(giftMenu).isNotEmpty();
        assertThat(giftMenu.get(Menu.CHAMPAGNE)).isNotNull();
    }

    @DisplayName("할인 금액 총액을 계산 할 수 있다.")
    @Test
    void calculateTotalDiscountAmount() {
        menus.put(Menu.T_BONE_STEAK, 2);
        menus.put(Menu.ICE_CREAM, 2);
        Date date = new Date(25);
        Order order = new Order(EventDateUtil.getLocalDateFromDay(date.getDate()), menus);
        OrderDto orderDto = new OrderDto(order);
        int totalDiscountAmount = christmasEvent.calculateTotalDiscount(orderDto);

        assertThat(totalDiscountAmount).isEqualTo(33446);
    }
}