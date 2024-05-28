package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Objects;

public class BuildSuffixArrayOfLongStringTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/build-suffix-array-of-long-string.csv";

    private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

    private static final char EOF = '$';

    public BuildSuffixArrayOfLongStringTest() {
        super(new BuildSuffixArrayOfLongString(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        super.testSolution(PASolution.FINAL, input, expectedOutput);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        if (Objects.requireNonNull(type) == PATestType.TIME_LIMIT_TEST) {
            return Utils.getRandomString(ALPHABET_SYMBOLS, 200000) + EOF;
        }
        return Utils.getRandomString(ALPHABET_SYMBOLS, 1000) + EOF;
    }
}
