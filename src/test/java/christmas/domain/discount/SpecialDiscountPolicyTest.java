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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SpecialDiscountPolicyTest {
    private SpecialDiscountPolicy specialDiscountPolicy;
    private Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        specialDiscountPolicy = new SpecialDiscountPolicy();
        menus = new EnumMap<>(Menu.class);
        menus.put(Menu.T_BONE_STEAK, 1);
    }

    @DisplayName("일요일에는 특별 할인 1,000원이 적용된다.")
    @ParameterizedTest
    @MethodSource("testCasesForSpecialDiscount")
    void christmasDailyDiscount(int day, int expectedDiscount) {
        Date date = new Date(day);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = specialDiscountPolicy.discount(orderDto);

        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @DisplayName("크리스마스에는 특별 할인 1,000원이 적용된다.")
    @Test
    void maxDiscountAmountTest() {
        Date date = new Date(25);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = specialDiscountPolicy.discount(orderDto);

        assertThat(discount).isEqualTo(1000);
    }

    @DisplayName("할인 기간이 아닌 경우 할인이 적용되지 않는다.")
    @Test
    void noDiscountTest() {
        Date date = new Date(1);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = specialDiscountPolicy.discount(orderDto);

        assertThat(discount).isZero();
    }

    private static Stream<Arguments> testCasesForSpecialDiscount() {
        return Stream.of(
                Arguments.of(3, 1000),
                Arguments.of(10, 1000),
                Arguments.of(17, 1000),
                Arguments.of(24, 1000)
        );
    }

    @DisplayName("이벤트 명은 특별 할인 이다.")
    @Test
    void discountPolicyNameTest() {
        DiscountPolicyName discountPolicyName = specialDiscountPolicy.getDiscountPolicyName();

        assertThat(discountPolicyName).isEqualTo(DiscountPolicyName.SPECIAL_DISCOUNT);
    }
}