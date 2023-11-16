package christmas.view;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class InputViewTest {
    InputView inputView;

    @BeforeEach
    void setUp() {
        inputView = new InputView();
    }

    @DisplayName("날짜는 숫자만 입력 가능하다.")
    @Test
    void validateDateTest() {
        try (MockedStatic<Console> mockedConsole = Mockito.mockStatic(Console.class)) {
            mockedConsole.when(Console::readLine).thenReturn("10일");

            assertThatThrownBy(inputView::readDate)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    @DisplayName("메뉴는 [메뉴명-갯수,메뉴명-갯수] 형식으로 입력가능하다.")
    @Test
    void validateMenuTest() {
        try (MockedStatic<Console> mockedConsole = Mockito.mockStatic(Console.class)) {
            mockedConsole.when(Console::readLine).thenReturn("스테이크1개,제로콜라3개");

            assertThatThrownBy(inputView::readOrder)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }

    @DisplayName("같은 메뉴를 나눠서 주문하면 예외를 발생 시킨다.")
    @Test
    void validateDuplicateMenuTest() {
        try (MockedStatic<Console> mockedConsole = Mockito.mockStatic(Console.class)) {
            mockedConsole.when(Console::readLine).thenReturn("초코케이크-1,초코케이크-1");

            assertThatThrownBy(inputView::readOrder)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("유효하지 않은 주문입니다. 다시 입력해 주세요.");
        }
    }
}