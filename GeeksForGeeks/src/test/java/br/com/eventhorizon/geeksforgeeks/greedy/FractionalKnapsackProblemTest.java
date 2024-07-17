package br.com.eventhorizon.geeksforgeeks.greedy;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class FractionalKnapsackProblemTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/greedy/fractional-knapsack-problem.csv";

    protected FractionalKnapsackProblemTest() {
        super(new FractionalKnapsackProblem(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
