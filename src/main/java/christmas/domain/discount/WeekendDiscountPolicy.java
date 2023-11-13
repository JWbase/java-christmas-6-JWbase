package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuCategory;
import christmas.service.dto.OrderDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;


public class WeekendDiscountPolicy implements DiscountPolicy {
    private static final int WEEKEND_DISCOUNT_AMOUNT = 2023;

    @Override
    public int discount(final OrderDto order) {
        LocalDate orderDate = order.getDate();
        if (isWithinDiscountPeriod(orderDate) && isWeekend(orderDate)) {
            return calculateTotalDiscount(order.getMenus());
        }
        return DiscountConstants.NO_DISCOUNT;
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.WEEKEND_DISCOUNT;
    }

    private int calculateTotalDiscount(Map<Menu, Integer> menus) {
        int totalDiscount = 0;
        for (Entry<Menu, Integer> menu : menus.entrySet()) {
            totalDiscount += calculateDiscountForMenu(menu);
        }
        return totalDiscount;
    }

    private int calculateDiscountForMenu(Entry<Menu, Integer> menu) {
        if (isMainCategory(menu.getKey())) {
            return calculateMenuDiscount(menu);
        }
        return 0;
    }

    private boolean isMainCategory(Menu menu) {
        return menu.getMenuCategory() == MenuCategory.MAIN;
    }

    private int calculateMenuDiscount(Map.Entry<Menu, Integer> menu) {
        return WEEKEND_DISCOUNT_AMOUNT * menu.getValue();
    }

    private boolean isWithinDiscountPeriod(LocalDate date) {
        return isNotBeforeStartDate(date) && isNotAfterEndDate(date);
    }

    private boolean isNotBeforeStartDate(LocalDate date) {
        return !date.isBefore(DiscountConstants.START_DAY_OF_MONTH);
    }

    private boolean isNotAfterEndDate(LocalDate date) {
        return !date.isAfter(DiscountConstants.END_DAY_OF_MONTH);
    }

    private boolean isWeekend(LocalDate date) {
        return isFriday(date) || isSaturday(date);
    }

    private boolean isFriday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY;
    }

    private boolean isSaturday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
}