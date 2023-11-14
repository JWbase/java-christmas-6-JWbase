package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeTest {

    @DisplayName("가격에 따른 뱃지를 반환 한다.")
    @ParameterizedTest
    @CsvSource({"0, NONE", "5000, STAR", "10000, TREE", "20000, SANTA", "25000, SANTA"})
    void getBadgeByDiscountPriceTest(int discountPrice, Badge expectedBadge) {
        assertThat(Badge.getBadgeByDiscountPrice(discountPrice)).isEqualTo(expectedBadge);
    }
}