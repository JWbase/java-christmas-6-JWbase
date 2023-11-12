package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.order.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DateTest {

    @DisplayName("날짜는 1 ~ 31 사이의 숫자만 입력할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 16, 31})
    void validateRange_success(int date) {
        assertThatCode(() -> new Date(date))
                .doesNotThrowAnyException();
    }

    @DisplayName("1 ~ 31 사이의 숫자가 아니면 예외를 발생 시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32})
    void validateRange(int date) {
        assertThatThrownBy(() -> new Date(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }
}