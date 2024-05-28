package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Objects;

public class SuffixArrayPatternMatchingTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/suffix-array-pattern-matching.csv";

    private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

    public SuffixArrayPatternMatchingTest() {
        super(new SuffixArrayPatternMatching(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .timeLimit(5000)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    @Disabled("Trivial solution is not implemented")
    public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.TRIVIAL, input, expectedOutput == null ? "" : expectedOutput);
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.FINAL, input, expectedOutput == null ? "" : expectedOutput);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        int numberOfPatterns;
        if (Objects.requireNonNull(type) == PATestType.TIME_LIMIT_TEST) {
            input.append(Utils.getRandomString(ALPHABET_SYMBOLS, 500000));
            numberOfPatterns = Utils.getRandomInteger(1, 10000);
            input.append(" ").append(numberOfPatterns);
            for (int i = 0; i < numberOfPatterns; i++) {
                input.append(" ").append(Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000));
            }
        }
        input.append(Utils.getRandomString(ALPHABET_SYMBOLS, 1000));
        numberOfPatterns = Utils.getRandomInteger(1, 100);
        input.append(" ").append(numberOfPatterns);
        for (int i = 0; i < numberOfPatterns; i++) {
            input.append(" ").append(Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000));
        }
        return input.toString();
    }
}
