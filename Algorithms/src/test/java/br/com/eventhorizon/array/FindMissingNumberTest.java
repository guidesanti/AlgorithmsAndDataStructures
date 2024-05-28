package br.com.eventhorizon.array;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Objects;

public class FindMissingNumberTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/array/find-missing-number.csv";

    protected FindMissingNumberTest() {
        super(new FindMissingNumber(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .stressTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.FINAL, input, expectedOutput);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        var input = new StringBuilder();
        var n = type == PATestType.TIME_LIMIT_TEST
                ? Utils.getRandomInteger(2, 1_000_000)
                : Utils.getRandomInteger(2, 1_000);
        var skippedValue = Utils.getRandomInteger(1, n);

        input.append(n).append(" ");
        for (int i = 1; i <= n; i++) {
            if (i == skippedValue) {
                continue;
            }
            input.append(i).append(" ");
        }
        if (Objects.nonNull(expectedOutput)) {
            expectedOutput.append(skippedValue);
        }

        return input.toString();
    }
}
