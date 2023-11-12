package christmas.controller;

import christmas.domain.order.Date;
import christmas.domain.Menu;
import christmas.domain.order.Order;
import christmas.util.EventDateUtil;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.Map;

public class ChristmasController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Order order = createOrder();
        outputView.printPreviewBenefit(order.getDate());
        outputView.printOrderDetails(order.getMenus());
        outputView.printBeforeDiscountTotalPrice(order.calculateBeforeDiscountTotalPrice());
    }

    private Order createOrder() {
        while (true) {
            try {
                Date date = new Date(inputView.readDate());
                LocalDate orderDate = EventDateUtil.getLocalDateFromDay(date.getDate());
                Map<Menu, Integer> orderMenus = inputView.readOrder();
                return new Order(orderDate, orderMenus);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}