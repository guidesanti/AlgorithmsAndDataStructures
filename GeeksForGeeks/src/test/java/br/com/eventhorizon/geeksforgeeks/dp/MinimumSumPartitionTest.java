package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.Arrays;
import java.util.Collections;

public class MinimumSumPartitionTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/minimum-sum-partition.csv";

    protected MinimumSumPartitionTest() {
        super(new MinimumSumPartition(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(5000)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        // N
        int n;
        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            n = 100_000;
        } else {
            n = Utils.getRandomInteger(1, 20);
        }
        input.append(n);

        // Elements
        for (int i = 0; i < n; i++) {
            input.append(" ").append(Utils.getRandomInteger(1, 100_000));
        }

        return input.toString();
    }
}
