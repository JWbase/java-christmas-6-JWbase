package christmas.domain;

import java.util.Arrays;
import java.util.Comparator;

public enum Badge {
    NONE(0, "없음"),
    STAR(5000, "별"),
    TREE(10000, "트리"),
    SANTA(20000, "산타");

    private final int minimumPrice;
    private final String name;

    Badge(final int minimumPrice, final String name) {
        this.minimumPrice = minimumPrice;
        this.name = name;
    }

    public int getMinimumPrice() {
        return minimumPrice;
    }

    public String getName() {
        return name;
    }

    public static Badge getBadgeByDiscountPrice(int benefit) {
        return Arrays.stream(Badge.values())
                .filter(badge -> benefit >= badge.getMinimumPrice())
                .max(Comparator.comparingInt(Badge::getMinimumPrice))
                .orElse(NONE);
    }
}
