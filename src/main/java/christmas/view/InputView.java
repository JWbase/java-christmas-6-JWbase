package christmas.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String VISIT_PROMPT_MESSAGE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";

    public int readDate() {
        System.out.println(VISIT_PROMPT_MESSAGE);
        String date = getInput();
        return parseInt(date);
    }

    private String getInput() {
        return Console.readLine().trim();
    }

    private int parseInt(String date) {
        try {
            return Integer.parseInt(date);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_PREFIX + "유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }
}
