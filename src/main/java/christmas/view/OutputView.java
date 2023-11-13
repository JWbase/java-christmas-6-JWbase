package christmas.view;

import christmas.constant.DiscountPolicyName;
import christmas.domain.Menu;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

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

    public void printPreviewBenefit(final LocalDate date) {
        System.out.printf(PREVIEW_EVENT_BENEFIT_MESSAGE + NEW_LINE + NEW_LINE, date.getDayOfMonth());
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
        System.out.println();
    }

    public void printGiftMenu(Optional<Menu> giftMenu) {
        System.out.println("<증정 메뉴>");
        giftMenu.map(menu -> menu.getName() + " 1개")
                .ifPresentOrElse(System.out::println, () -> System.out.println("없음"));
        System.out.println();
    }

    public void printDiscount(Map<DiscountPolicyName, Integer> discountOrder) {
        System.out.println("<혜택 내역>");
        Optional<String> discounts = discountOrder.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(entry -> entry.getKey().getName() + ": -" + formatNumber(entry.getValue()) + "원")
                .reduce((first, second) -> first + "\n" + second);
        discounts.ifPresentOrElse(System.out::println, () -> System.out.println("없음"));
    }

    public void printTotalBenefitAmount(int totalDiscountAmount) {
        System.out.println();
        System.out.println("<총혜택 금액>");
        Optional.of(totalDiscountAmount)
                .filter(amount -> amount != 0)
                .map(amount -> "-" + formatNumber(amount) + "원")
                .ifPresentOrElse(System.out::println, () -> System.out.println("없음"));
        System.out.println();
    }

    public void printDecemberBadge(String name) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(name);
    }

    public void printPaymentDue(int paymentAmount) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(formatNumber(paymentAmount) + "원");
        System.out.println();
    }

    private String formatNumber(final int number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
}