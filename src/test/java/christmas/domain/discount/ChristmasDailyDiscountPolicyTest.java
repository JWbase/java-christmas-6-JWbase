package christmas.domain.discount;

import static org.assertj.core.api.Assertions.assertThat;

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

class ChristmasDailyDiscountPolicyTest {
    private ChristmasDailyDiscountPolicy christmasDailyDiscountPolicy;
    private Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        christmasDailyDiscountPolicy = new ChristmasDailyDiscountPolicy();
        menus = new EnumMap<>(Menu.class);
        menus.put(Menu.T_BONE_STEAK, 1);
    }

    @DisplayName("할인금액이 1일 1,000원부터 하루에 100원씩 증가 한다.")
    @ParameterizedTest
    @MethodSource("testCasesForChristmasDailyDiscount")
    void christmasDailyDiscount(int day, int expectedDiscount) {
        Date date = new Date(day);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = christmasDailyDiscountPolicy.discount(orderDto);

        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @DisplayName("12월 25일에는 할인 금액이 3400원이다.")
    @Test
    void maxDiscountAmountTest() {
        Date date = new Date(25);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = christmasDailyDiscountPolicy.discount(orderDto);

        assertThat(discount).isEqualTo(3400);
    }

    @DisplayName("12월 26일 이후 할인 금액은 0원이다.")
    @Test
    void noDiscountTest() {
        Date date = new Date(26);
        LocalDate localDate = EventDateUtil.getLocalDateFromDay(date.getDate());
        Order order = new Order(localDate, menus);
        OrderDto orderDto = new OrderDto(order);
        int discount = christmasDailyDiscountPolicy.discount(orderDto);

        assertThat(discount).isZero();
    }

    private static Stream<Arguments> testCasesForChristmasDailyDiscount() {
        return Stream.of(
                Arguments.of(1, 1000),
                Arguments.of(5, 1400),
                Arguments.of(24, 3300)
        );
    }
}