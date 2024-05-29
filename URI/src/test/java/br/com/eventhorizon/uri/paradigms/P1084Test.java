package br.com.eventhorizon.uri.paradigms;

import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class P1084Test extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/paradigms/p1084/p1084.csv";

    public P1084Test() {
        super(new P1084(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(1000)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        int n;
        int d;
        if (type == PATestType.TIME_LIMIT_TEST) {
            d = Utils.getRandomInteger(1, 99998);
            n = Utils.getRandomInteger(d + 1, 100000);
        } else {
            d = Utils.getRandomInteger(1, 98);
            n = Utils.getRandomInteger(d + 1, 100);
        }
        input.append(n).append(" ").append(d).append("\n");
        input.append(Utils.getRandomString(Utils.CharType.NUMERICAL_CHARS, n)).append("\n");
        return input.toString();
    }
}
