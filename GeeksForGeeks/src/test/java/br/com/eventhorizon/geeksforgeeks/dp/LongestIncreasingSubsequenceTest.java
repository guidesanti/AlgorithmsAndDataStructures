package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class LongestIncreasingSubsequenceTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/longest-increasing-subsequence.csv";

    protected LongestIncreasingSubsequenceTest() {
        super(new LongestIncreasingSubsequence(), PATestSettings.builder()
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
            n = 1_000;
        } else {
            n = 100;
        }

        input.append(n);

        for (int i = 0; i < n; i++) {
            input.append(" ").append(Utils.getRandomInteger(1, 1_000));
        }

        return input.toString();
    }
}
