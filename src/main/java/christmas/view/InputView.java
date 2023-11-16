package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.menu.Menu;
import christmas.util.SplitUtil;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

public class InputView {
    private static final String VISIT_PROMPT_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String ORDER_MENU_MESSAGE = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String INVALID_DATE_MESSAGE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final String INVALID_ORDER_MESSAGE = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final Pattern ORDER_PATTERN = Pattern.compile(
            "^([가-힣a-zA-Z]+-[1-9]\\d*,)*([가-힣a-zA-Z]+-[1-9]\\d*)$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    public int readDate() {
        System.out.println(VISIT_PROMPT_MESSAGE);
        String input = getInput();
        validateDate(input);
        return parseInt(input);
    }

    private void validateDate(final String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
        }
    }

    public Map<Menu, Integer> readOrder() {
        System.out.println(ORDER_MENU_MESSAGE);
        String orderMenu = getInput();
        validateOrder(orderMenu);
        return processOrderMenus(orderMenu);
    }

    private static void validateOrder(final String orderMenu) {
        if (!ORDER_PATTERN.matcher(orderMenu).matches()) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private Map<Menu, Integer> processOrderMenus(final String orderMenu) {
        Map<Menu, Integer> orderMenus = new EnumMap<>(Menu.class);
        String[] orders = SplitUtil.splitByComma(orderMenu);
        for (String order : orders) {
            processOrder(order, orderMenus);
        }
        return orderMenus;
    }

    private void processOrder(String order, Map<Menu, Integer> orderMenus) {
        String[] menuAndQuantity = SplitUtil.splitByDash(order);
        String menuName = menuAndQuantity[0];
        int quantity = parseInt(menuAndQuantity[1]);
        Menu menu = Menu.findByName(menuName);
        validateDuplicateMenuName(orderMenus, menu);
        orderMenus.put(menu, quantity);
    }

    private void validateDuplicateMenuName(final Map<Menu, Integer> orderMenus, final Menu menu) {
        if (orderMenus.containsKey(menu)) {
            throw new IllegalArgumentException(INVALID_ORDER_MESSAGE);
        }
    }

    private int parseInt(final String input) {
        return Integer.parseInt(input);
    }

    private String getInput() {
        return Console.readLine().trim();
    }
}