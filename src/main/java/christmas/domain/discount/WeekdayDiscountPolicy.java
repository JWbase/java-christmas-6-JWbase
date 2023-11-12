package christmas.domain.discount;

import christmas.constant.DiscountConstants;
import christmas.constant.DiscountPolicyName;
import christmas.domain.Menu;
import christmas.domain.MenuCategory;
import christmas.service.dto.DiscountPolicyDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class WeekdayDiscountPolicy implements DiscountPolicy {

    private static final int WEEKDAY_DISCOUNT_AMOUNT = 2023;

    @Override
    public int discount(DiscountPolicyDto discountPolicyDto) {
        LocalDate orderDate = discountPolicyDto.getOrderDate();
        if (isWithinDiscountPeriod(orderDate) && isWeekday(orderDate)) {
            return calculateTotalDiscount(discountPolicyDto);
        }
        return DiscountConstants.NO_DISCOUNT;
    }

    @Override
    public DiscountPolicyName getDiscountPolicyName() {
        return DiscountPolicyName.WEEKDAY_DISCOUNT;
    }

    private int calculateTotalDiscount(DiscountPolicyDto discountPolicyDto) {
        int totalDiscount = 0;
        Map<Menu, Integer> menus = discountPolicyDto.getMenus();

        for (Map.Entry<Menu, Integer> menu : menus.entrySet()) {
            totalDiscount += calculateDiscountForMenu(menu);
        }
        return totalDiscount;
    }

    private int calculateDiscountForMenu(Map.Entry<Menu, Integer> menu) {
        if (isDessertCategory(menu.getKey())) {
            return calculateMenuDiscount(menu);
        }
        return 0;
    }

    private boolean isDessertCategory(Menu menu) {
        return menu.getMenuCategory() == MenuCategory.DESSERT;
    }

    private int calculateMenuDiscount(Map.Entry<Menu, Integer> menu) {
        return WEEKDAY_DISCOUNT_AMOUNT * menu.getValue();
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

    private boolean isWeekday(LocalDate date) {
        return isNotFriday(date) && isNotSaturday(date);
    }

    private boolean isNotFriday(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.FRIDAY;
    }

    private boolean isNotSaturday(LocalDate date) {
        return date.getDayOfWeek() != DayOfWeek.SATURDAY;
    }
}