package christmas.util;

public class SplitUtil {

    private SplitUtil() {
    }

    private static final String COMMA_DELIMITER = ",";
    private static final String DASH_DELIMITER = "-";

    public static String[] splitByComma(String input) {
        return input.split(COMMA_DELIMITER);
    }

    public static String[] splitByDash(String input) {
        return input.split(DASH_DELIMITER);
    }
}

