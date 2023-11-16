package christmas.domain.menu;

import java.util.Arrays;

public enum Menu {

    MUSHROOM_SOUP("양송이수프", 6_000, MenuCategory.APPETIZER),
    TAPAS("타파스", 5_500, MenuCategory.APPETIZER),
    CAESAR_SALAD("시저샐러드", 8_000, MenuCategory.APPETIZER),

    T_BONE_STEAK("티본스테이크", 55_000, MenuCategory.MAIN),
    BBQ_RIB("바비큐립", 54_000, MenuCategory.MAIN),
    SEAFOOD_PASTA("해산물파스타", 35_000, MenuCategory.MAIN),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000, MenuCategory.MAIN),

    CHOCOLATE_CAKE("초코케이크", 15_000, MenuCategory.DESSERT),
    ICE_CREAM("아이스크림", 5_000, MenuCategory.DESSERT),

    ZERO_COLA("제로콜라", 3_000, MenuCategory.DRINK),
    RED_WINE("레드와인", 60_000, MenuCategory.DRINK),
    CHAMPAGNE("샴페인", 25_000, MenuCategory.DRINK);

    private static final String INVALID_ORDER_MESSAGE = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private final String name;
    private final int price;
    private final MenuCategory menuCategory;

    Menu(final String name, final int price, final MenuCategory menuCategory) {
        this.name = name;
        this.price = price;
        this.menuCategory = menuCategory;
    }

    public static Menu findByName(final String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_ORDER_MESSAGE));
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getMenuCategory() {
        return menuCategory;
    }
}