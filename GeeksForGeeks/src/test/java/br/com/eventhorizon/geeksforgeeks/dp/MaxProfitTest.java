package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class MaxProfitTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/max-profit.csv";

    protected MaxProfitTest() {
        super(new MaxProfit(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        // k
        input.append(Utils.getRandomInteger(1, 200));

        // n
        int n;
        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            n = Utils.getRandomInteger(1, 1000);
        } else {
            n = Utils.getRandomInteger(1, 30);
        }
        input.append(" ").append(n);

        // Prices
        for (int i = 0; i < n; i++) {
            input.append(" ").append(Utils.getRandomInteger(1, 1000));
        }

        return input.toString();
    }
}
