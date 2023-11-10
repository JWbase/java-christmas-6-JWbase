package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String ERROR_PREFIX = "[ERROR] ";

    public int readDate() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        try {
            return Integer.parseInt(Console.readLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PREFIX + "유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }
}
