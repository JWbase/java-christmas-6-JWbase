package christmas.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.menu.Menu;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    private LocalDate date;
    private Map<Menu, Integer> menus;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2023, 12, 25);
        menus = new EnumMap<>(Menu.class);
    }

    @DisplayName("주문 한 메뉴가 없으면 예외를 발생 시킨다.")
    @Test
    void validateIsEmptyTest() {
        assertThatThrownBy(() -> new Order(date, menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("주문은 음료만 할 수 없다.")
    @Test
    void validateOnlyDrinksTest() {
        menus.put(Menu.CHAMPAGNE, 1);
        menus.put(Menu.RED_WINE, 2);
        menus.put(Menu.ZERO_COLA, 1);

        assertThatThrownBy(() -> new Order(date, menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료만 주문할 수 없습니다.");
    }

    @DisplayName("주문은 한번에 20개 까지만 가능합니다.")
    @Test
    void validateTotalQuantityTest() {
        menus.put(Menu.BBQ_RIB, 11);
        menus.put(Menu.RED_WINE, 2);
        menus.put(Menu.ZERO_COLA, 1);
        menus.put(Menu.CAESAR_SALAD, 7);

        assertThatThrownBy(() -> new Order(date, menus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 20개의 메뉴만 주문 가능합니다.");
    }

    @DisplayName("주문 한 메뉴 가격의 총 합을 구할 수 있다.")
    @Test
    void calculateBeforeDiscountTotalPriceTest() {
        menus.put(Menu.CAESAR_SALAD, 2);
        menus.put(Menu.T_BONE_STEAK, 3);
        Order order = new Order(date, menus);
        int expectedTotalPrice = order.calculateBeforeDiscountTotalPrice();
        int actualTotalPrice = (Menu.CAESAR_SALAD.getPrice() * 2 + Menu.T_BONE_STEAK.getPrice() * 3);

        assertThat(actualTotalPrice).isEqualTo(expectedTotalPrice);
    }
}