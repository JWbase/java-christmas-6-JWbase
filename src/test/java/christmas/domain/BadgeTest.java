package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadgeTest {

    @DisplayName("가격에 따른 뱃지를 반환 한다.")
    @Test
    void getBadgeByDiscountPriceTest() {
        assertThat(Badge.getBadgeByDiscountPrice(0)).isEqualTo(Badge.NONE);
        assertThat(Badge.getBadgeByDiscountPrice(5000)).isEqualTo(Badge.STAR);
        assertThat(Badge.getBadgeByDiscountPrice(10000)).isEqualTo(Badge.TREE);
        assertThat(Badge.getBadgeByDiscountPrice(20000)).isEqualTo(Badge.SANTA);
        assertThat(Badge.getBadgeByDiscountPrice(25000)).isEqualTo(Badge.SANTA);
    }
}