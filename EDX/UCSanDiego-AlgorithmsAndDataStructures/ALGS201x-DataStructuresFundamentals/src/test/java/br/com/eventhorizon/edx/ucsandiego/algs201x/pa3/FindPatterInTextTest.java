package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class FindPatterInTextTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/find-pattern-in-text.csv";

    public FindPatterInTextTest() {
        super(new FindPatterInText(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
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

    @Test
    public void testFinalSolutionWithWorstCase() {
        String input = "a".repeat(10000) +
                " " +
                "a".repeat(500000);
        reset(input);
        assertTimeoutPreemptively(ofMillis(6000), pa::finalSolution);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        int textLength;
        int patternLength;
        switch (type) {
            case TIME_LIMIT_TEST:
                textLength = Utils.getRandomInteger(1, 500000);
                patternLength = textLength == 1 ? textLength : Utils.getRandomInteger(1, textLength);
                return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, patternLength) +
                        " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, textLength);
            case STRESS_TEST:
            default:
                textLength = Utils.getRandomInteger(1, 100);
                patternLength = textLength == 1 ? textLength : Utils.getRandomInteger(1, textLength);
                String pattern = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, patternLength);
                String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, textLength);
                if (textLength > patternLength) {
                    StringBuilder str = new StringBuilder(text);
                    int n = Utils.getRandomInteger(0, 1000);
                    for (int i = 0; i < n; i++) {
                        int index = Utils.getRandomInteger(0, textLength - patternLength);
                        str.replace(index, index + patternLength, pattern);
                    }
                    text = str.toString();
                }
                return pattern + " " + text;
        }
    }
}
