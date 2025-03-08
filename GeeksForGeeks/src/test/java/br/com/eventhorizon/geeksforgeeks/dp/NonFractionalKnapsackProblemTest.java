package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class NonFractionalKnapsackProblemTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/non-fractional-knapsack-problem.csv";

    protected NonFractionalKnapsackProblemTest() {
        super(new NonFractionalKnapsackProblem(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        int n;
        int capacity = getRandomInteger(1, 1_000);

        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            n = 1_000;
        } else {
            n = 1_0;
        }

        input.append(n).append(" ").append(capacity);

        for (int i = 0; i < n; i++) {
            input
                    .append(" ").append(getRandomInteger(1, 1_000)) // profit
                    .append(" ").append(getRandomInteger(1, 1_000)); // weight
        }

        return input.toString();
    }
}
