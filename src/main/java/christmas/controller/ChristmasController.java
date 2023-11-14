package christmas.controller;

import christmas.constant.DiscountPolicyName;
import christmas.domain.Badge;
import christmas.domain.ChristmasEvent;
import christmas.domain.menu.Menu;
import christmas.domain.order.Date;
import christmas.domain.order.Order;
import christmas.service.OrderService;
import christmas.service.dto.OrderDto;
import christmas.util.EventDateUtil;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.Map;

public class ChristmasController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderService orderService;

    public ChristmasController() {
        inputView = new InputView();
        outputView = new OutputView();
        orderService = new OrderService(new ChristmasEvent());
    }

    public void run() {
        Order order = createOrder();
        OrderDto requestOrder = orderService.convertToDto(order);
        displayOrderDetails(requestOrder);
        displayDiscountDetails(requestOrder);
        displayPaymentAmount(requestOrder);
        displayBadge(requestOrder);
    }

    private Order createOrder() {
        outputView.printEventInformation();
        LocalDate date = inputDate();
        while (true) {
            try {
                return orderService.createOrder(date, inputView.readOrder());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private LocalDate inputDate() {
        while (true) {
            try {
                Date date = new Date(inputView.readDate());
                return EventDateUtil.getLocalDateFromDay(date.getDate());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void displayOrderDetails(final OrderDto order) {
        outputView.printPreviewBenefit(order.getDate());
        outputView.printOrderDetails(order.getMenus());
        outputView.printBeforeDiscountTotalPrice(order.getTotalPrice());
    }

    private void displayDiscountDetails(final OrderDto requestOrder) {
        Map<Menu, Integer> giftMenu = orderService.getGiftMenu(requestOrder);
        outputView.printGiftMenu(giftMenu);
        Map<DiscountPolicyName, Integer> discountDetails = orderService.getDiscountDetails(requestOrder);
        outputView.printDiscount(discountDetails);
        int totalBenefitAmount = orderService.getTotalBenefitAmount(requestOrder);
        outputView.printTotalBenefitAmount(totalBenefitAmount);
    }

    private void displayPaymentAmount(final OrderDto requestOrder) {
        int paymentAmount = requestOrder.getTotalPrice() - orderService.calculatePaymentDue(requestOrder);
        outputView.printPaymentAmountAfterDiscount(paymentAmount);
    }

    private void displayBadge(final OrderDto requestOrder) {
        int totalBenefitAmount = orderService.getTotalBenefitAmount(requestOrder);
        Badge decemberBadge = Badge.getBadgeByDiscountPrice(totalBenefitAmount);
        outputView.printDecemberBadge(decemberBadge.getName());
    }
}