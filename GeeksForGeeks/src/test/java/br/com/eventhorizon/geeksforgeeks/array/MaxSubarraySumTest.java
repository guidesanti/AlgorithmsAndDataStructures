package br.com.eventhorizon.geeksforgeeks.array;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MaxSubarraySumTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/array/max-subarray-sum.csv";

    protected MaxSubarraySumTest() {
        super(new MaxSubarraySum(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .stressTestEnabled(false)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.FINAL, input, expectedOutput);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        var input = new StringBuilder();
        var n = switch (type) {
            case TIME_LIMIT_TEST -> Utils.getRandomInteger(1, 1_000_000);
            case STRESS_TEST -> Utils.getRandomInteger(1, 1_000);
            default -> 0;
        };

        input.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            input.append(Utils.getRandomInteger(-10, 10)).append(" ");
        }

        return input.toString();
    }
}
