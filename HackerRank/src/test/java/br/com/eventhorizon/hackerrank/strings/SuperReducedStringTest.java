package br.com.eventhorizon.hackerrank.strings;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class SuperReducedStringTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/strings/super-reduced-string.csv";

    protected SuperReducedStringTest() {
        super(new SuperReducedString(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        int n;
        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            n = 1000_000;
        } else {
            n = 100_000;
        }

        while (input.length() < n) {
            var ch = Utils.getRandomChar(Utils.CharType.LOWERCASE_ALPHABETICAL_CHARS);
            var repeat = Utils.getRandomInteger(1, 10);
            input.append(String.valueOf(ch).repeat(Math.max(0, repeat)));
        }

        return input.toString();
    }
}
