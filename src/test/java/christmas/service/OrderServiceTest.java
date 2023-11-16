package christmas.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.constant.DiscountPolicyName;
import christmas.domain.ChristmasEvent;
import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.service.dto.OrderDto;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderServiceTest {
    private OrderService orderService;
    private ChristmasEvent christmasEvent;
    Map<Menu, Integer> menus;
    LocalDate date;
    private Order order;

    @BeforeEach
    void setUp() {
        christmasEvent = new ChristmasEvent();
        orderService = new OrderService(christmasEvent);
        menus = new EnumMap<>(Menu.class);
        menus.put(Menu.CAESAR_SALAD, 1);
        menus.put(Menu.T_BONE_STEAK, 2);
        menus.put(Menu.CHAMPAGNE, 1);
        menus.put(Menu.ICE_CREAM, 1);
        date = LocalDate.of(2023, 12, 25);
        order = new Order(date, menus);
    }

    @DisplayName("날짜와 메뉴를 받아 Order를 생성한다.")
    @Test
    void createOrderDtoTest() {
        LocalDate date = LocalDate.now();
        Map<Menu, Integer> menus = new HashMap<>();
        menus.put(Menu.MUSHROOM_SOUP, 1);
        menus.put(Menu.T_BONE_STEAK, 1);
        menus.put(Menu.RED_WINE, 1);
        Order newOrder = orderService.createOrder(date, menus);

        assertThat(newOrder.getDate()).isEqualTo(date);
        assertThat(newOrder.getMenus()).isEqualTo(menus);
    }

    @DisplayName("할인 내역 별로 이벤트 명과 금액을 반환한다.")
    @Test
    void getDiscountDetailsTest() {
        OrderDto orderDto = orderService.convertToDto(order);
        Map<DiscountPolicyName, Integer> discountDetails = new EnumMap<>(DiscountPolicyName.class);
        discountDetails.put(DiscountPolicyName.CHRISTMAS_DAILY_DISCOUNT, 3400);
        discountDetails.put(DiscountPolicyName.SPECIAL_DISCOUNT, 1000);
        discountDetails.put(DiscountPolicyName.WEEKDAY_DISCOUNT, 2023);
        discountDetails.put(DiscountPolicyName.GIFT_EVENT, Menu.CHAMPAGNE.getPrice());
        Map<DiscountPolicyName, Integer> actualDiscountDetails = orderService.getDiscountDetails(orderDto);

        assertThat(actualDiscountDetails).isEqualTo(discountDetails);
    }

    @DisplayName("할인 총액을 반환 할 수 있다.")
    @Test
    void getTotalBenefitAmount() {
        OrderDto orderDto = orderService.convertToDto(order);
        int totalBenefitAmount = 31423;
        int actualTotalBenefitAmount = orderService.getTotalBenefitAmount(orderDto);

        assertThat(actualTotalBenefitAmount).isEqualTo(totalBenefitAmount);
    }

    @DisplayName("증정 이벤트 상품을 가져온다.")
    @Test
    void getGiftMenuTest() {
        OrderDto orderDto = orderService.convertToDto(order);
        Map<Menu, Integer> giftMenu = new EnumMap<>(Menu.class);
        giftMenu.put(Menu.CHAMPAGNE, 1);
        Map<Menu, Integer> actualGiftMenu = orderService.getGiftMenu(orderDto);

        assertThat(actualGiftMenu).isEqualTo(giftMenu);
    }

    @DisplayName("증정품을 제외한 할인 총액을 반환한다.")
    @Test
    void calculatePaymentDueTest() {
        OrderDto orderDto = orderService.convertToDto(order);
        int paymentDue = orderService.calculatePaymentDue(orderDto);

        assertThat(paymentDue).isEqualTo(31423 - Menu.CHAMPAGNE.getPrice());
    }
}