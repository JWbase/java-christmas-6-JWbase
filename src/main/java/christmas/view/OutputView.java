package christmas.view;

import christmas.constant.CommentConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.menu.Menu;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final String PREVIEW_EVENT_BENEFIT_MESSAGE = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU_MESSAGE = "<주문 메뉴>";
    private static final String BEFORE_DISCOUNT_TOTAL_ORDER_PRICE_MESSAGE = "<할인 전 총주문 금액>";
    private static final String GIFT_MENU_MESSAGE = "<증정 메뉴>";
    private static final String BENEFIT_LIST_MESSAGE = "<혜택 내역>";
    private static final String TOTAL_BENEFIT_AMOUNT_MESSAGE = "<총혜택 금액>";
    private static final String PAYMENT_AMOUNT_AFTER_DISCOUNT_MESSAGE = "<할인 후 예상 결제 금액>";
    private static final String DECEMBER_EVENT_BADGE_MESSAGE = "<12월 이벤트 배지>";
    private static final String CURRENCY_SYMBOL = "원";
    private static final String QUANTITY_UNIT = "개";
    private static final String NOTHING = "없음";
    private static final String MINUS = "-";
    private static final String NEW_LINE = "\n";
    private static final String SPACE = " ";
    private static final String ERROR_PREFIX = "[ERROR] ";

    public void printEventInformation() {
        System.out.print(CommentConstants.EVENT_INFORMATION_MESSAGE);
    }

    public void printPreviewBenefit(final LocalDate date) {
        System.out.printf(PREVIEW_EVENT_BENEFIT_MESSAGE + NEW_LINE + NEW_LINE, date.getDayOfMonth());
    }

    public void printOrderDetails(final Map<Menu, Integer> menus) {
        System.out.println(ORDER_MENU_MESSAGE);
        for (Map.Entry<Menu, Integer> menu : menus.entrySet()) {
            System.out.println(menu.getKey().getName() + SPACE + menu.getValue() + QUANTITY_UNIT);
        }
        System.out.println();
    }

    public void printBeforeDiscountTotalPrice(final int totalPrice) {
        System.out.println(BEFORE_DISCOUNT_TOTAL_ORDER_PRICE_MESSAGE);
        System.out.println(formatNumber(totalPrice) + CURRENCY_SYMBOL);
        System.out.println();
    }

    public void printGiftMenu(final Map<Menu, Integer> giftMenu) {
        System.out.println(GIFT_MENU_MESSAGE);
        if (giftMenu.isEmpty()) {
            System.out.println(NOTHING);
            System.out.println();
            return;
        }
        for (Map.Entry<Menu, Integer> menu : giftMenu.entrySet()) {
            System.out.println(menu.getKey().getName() + " " + menu.getValue() + QUANTITY_UNIT);
        }
        System.out.println();
    }

    public void printDiscount(final Map<DiscountPolicyName, Integer> discountOrder) {
        System.out.println(BENEFIT_LIST_MESSAGE);
        Optional<String> discounts = discountOrder.entrySet().stream()
                .filter(entry -> entry.getValue() != 0)
                .map(entry -> entry.getKey().getName() + ": -" + formatNumber(entry.getValue()) + CURRENCY_SYMBOL)
                .reduce((first, second) -> first + NEW_LINE + second);
        discounts.ifPresentOrElse(System.out::println, () -> System.out.println(NOTHING));
    }

    public void printTotalBenefitAmount(final int totalDiscountAmount) {
        System.out.println();
        System.out.println(TOTAL_BENEFIT_AMOUNT_MESSAGE);
        Optional.of(totalDiscountAmount)
                .filter(amount -> amount != 0)
                .map(amount -> MINUS + formatNumber(amount) + CURRENCY_SYMBOL)
                .ifPresentOrElse(System.out::println, () -> System.out.println(NOTHING));
        System.out.println();
    }

    public void printDecemberBadge(final String name) {
        System.out.println(DECEMBER_EVENT_BADGE_MESSAGE);
        System.out.println(name);
    }

    public void printPaymentAmountAfterDiscount(final int paymentAmount) {
        System.out.println(PAYMENT_AMOUNT_AFTER_DISCOUNT_MESSAGE);
        System.out.println(formatNumber(paymentAmount) + CURRENCY_SYMBOL);
        System.out.println();
    }

    public void printError(final String message) {
        System.out.println(ERROR_PREFIX + message + System.lineSeparator());
    }

    private String formatNumber(final int number) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(number);
    }
}