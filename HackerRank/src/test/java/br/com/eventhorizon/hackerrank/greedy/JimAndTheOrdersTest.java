package br.com.eventhorizon.hackerrank.greedy;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class JimAndTheOrdersTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/greedy/jim-and-the-orders.csv";

    protected JimAndTheOrdersTest() {
        super(new JimAndTheOrders(), PATestSettings.builder()
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
            n = 100_000;
        } else {
            n = 100;
        }
        input.append(n);

        for (int i = 0; i < n; i++) {
            input
                    .append(" ").append(Utils.getRandomInteger(1, 1000_000))
                    .append(" ").append(Utils.getRandomInteger(1, 1000_000));
        }

        return input.toString();
    }
}
