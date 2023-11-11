package christmas.view;

import christmas.domain.Date;
import christmas.domain.Menu;
import java.text.NumberFormat;
import java.util.Map;

public class OutputView {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String NEW_LINE = "\n";
    private static final String PREVIEW_EVENT_BENEFIT_MESSAGE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU_MESSAGE = "<주문 메뉴>";
    private static final String BEFORE_DISCOUNT_TOTAL_ORDER_PRICE_MESSAGE = "<할인 전 총주문 금액>";
    private static final String CURRENCY_SYMBOL = "원";

    public void printError(final String message) {
        System.out.println(ERROR_PREFIX + message + System.lineSeparator());
    }

    public void printPreviewBenefit(final Date date) {
        System.out.printf(PREVIEW_EVENT_BENEFIT_MESSAGE + NEW_LINE + NEW_LINE, date.getDate());
    }

    public void printOrderDetails(final Map<Menu, Integer> menus) {
        System.out.println(ORDER_MENU_MESSAGE);
        for (Map.Entry<Menu, Integer> menu : menus.entrySet()) {
            System.out.println(menu.getKey().getName() + " " + menu.getValue() + "개");
        }
        System.out.println();
    }

    public void printBeforeDiscountTotalPrice(final int totalPrice) {
        System.out.println(BEFORE_DISCOUNT_TOTAL_ORDER_PRICE_MESSAGE);
        System.out.println(formatNumber(totalPrice) + CURRENCY_SYMBOL);
    }

    private String formatNumber(final int number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
}
