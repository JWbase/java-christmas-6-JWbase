package christmas.domain;

import java.util.Map;

public class Order {
    private static final int MAXIMUM_QUANTITY = 20;
    private static final String INVALID_ORDER_MESSAGE = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String TOTAL_QUANTITY_ERROR_MESSAGE = "최대 20개의 메뉴만 주문 가능합니다.";
    private static final String ONLY_DRINKS_ORDERED_ERROR_MESSAGE = "음료만 주문할 수 없습니다.";
    private final Date date;
    private final Map<Menu, Integer> menus;

    public Order(final Date date, final Map<Menu, Integer> menus) {
        validateMenus(menus);
        this.date = date;
        this.menus = menus;
    }

    private void validateMenus(final Map<Menu, Integer> menus) {
        validateIsEmpty(menus);
        validateTotalQuantity(menus);
        validateOnlyDrinksOrdered(menus);
    }

    private static void validateIsEmpty(final Map<Menu, Integer> menus) {
        if (menus.isEmpty()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private static void validateTotalQuantity(final Map<Menu, Integer> menus) {
        int totalQuantity = menus.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        if (totalQuantity > MAXIMUM_QUANTITY) {
            throw new IllegalArgumentException(TOTAL_QUANTITY_ERROR_MESSAGE);
        }
    }

    private static void validateOnlyDrinksOrdered(final Map<Menu, Integer> menus) {
        if (menus.keySet().stream()
                .allMatch(menu -> menu.getMenuCategory() == MenuCategory.DRINK)) {
            throw new IllegalArgumentException(ONLY_DRINKS_ORDERED_ERROR_MESSAGE);
        }
    }

    public Date getDate() {
        return date;
    }

    public Map<Menu, Integer> getMenus() {
        return menus;
    }
}
