package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class CoinChangeTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/coin-change.csv";

    protected CoinChangeTest() {
        super(new CoinChange(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        // Sum
        input.append(Utils.getRandomInteger(1, 10_000));

        // N
        int n;
        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            n = 1000;
        } else {
            n = Utils.getRandomInteger(1, 50);
        }
        input.append(" ").append(n);

        // Coins
        for (int i = 0; i < n; i++) {
            input.append(" ").append(Utils.getRandomInteger(1, 10_000));
        }

        return input.toString();
    }
}
