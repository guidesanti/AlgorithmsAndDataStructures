package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.function.Consumer;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalAlignmentInLinearSpaceTest extends PATestBase {

    private static final Logger LOGGER = Logger.getLogger(GlobalAlignmentInLinearSpaceTest.class.getName());

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/global-alignment-in-linear-space.csv";

    private static int matchScore;

    private static int mismatchScore;

    private static int gapScore;

    public GlobalAlignmentInLinearSpaceTest() {
        super(new GlobalAlignmentInLinearSpace());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
        test(input, expectedOutput, aVoid -> pa.trivialSolution());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        test(input, expectedOutput, aVoid -> pa.finalSolution());
    }

    @Test
    public void compareTest2() {
        if (!settings.isCompareTestEnabled()) {
            LOGGER.warning("Stress limit test skipped");
            return;
        }
        long startTime = System.currentTimeMillis();
        for (long i = 0; true; i++) {
            String input = generateInput(PATestType.COMPARE_TEST, null);

            String[] values = input.split(" ");
            matchScore = Integer.parseInt(values[0]);
            mismatchScore = -Integer.parseInt(values[1]);
            gapScore = -Integer.parseInt(values[2]);

            // Run and compare results
            reset(input);
            pa.trivialSolution();
            String result1 = getActualOutput();
            values = result1.split("\n");
            int result1ActualScore = Integer.parseInt(values[0]);
            String result1ActualAlignment1 = values[1];
            String result1ActualAlignment2 = values[2];
            int result1Score = score(result1ActualAlignment1, result1ActualAlignment2);

            reset(input);
            pa.finalSolution();
            String result2 = getActualOutput();
            values = result2.split("\n");
            int result2ActualScore = Integer.parseInt(values[0]);
            String result2ActualAlignment1 = values[1];
            String result2ActualAlignment2 = values[2];
            int result2Score = score(result2ActualAlignment1, result2ActualAlignment2);

            if (result1Score == result1ActualScore &&
                    result2Score == result2ActualScore &&
                    result1Score == result2Score) {
                LOGGER.info("Stress test " + i + " status: PASSED");
            } else {
                LOGGER.info("Stress test " + i + " status: FAILED");
                LOGGER.info("Stress test " + i + " input: " + input);
                LOGGER.info("Stress test " + i + " result 1:  " + result1);
                LOGGER.info("Stress test " + i + " result 2:  " + result2);
                throw new RuntimeException("Stress test failed");
            }

            // Check elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime > settings.getCompareTestDuration()) {
                return;
            }
        }
    }

    private void test(String input, String expectedOutput, Consumer<Void> f) {
        String[] values = input.split(" ");
        matchScore = Integer.parseInt(values[0]);
        mismatchScore = -Integer.parseInt(values[1]);
        gapScore = -Integer.parseInt(values[2]);

        values = expectedOutput.split("%");
        int expectedScore = Integer.parseInt(values[0]);
        String expectedAlignment1 = values[1];
        String expectedAlignment2 = values[2];

        reset(input);
        f.accept(null);

        values = getActualOutput().split("\n");
        int actualScore = Integer.parseInt(values[0]);
        String actualAlignment1 = values[1];
        String actualAlignment2 = values[2];

        assertEquals(expectedScore, actualScore, "Score not match 1");
        assertEquals(expectedScore, score(expectedAlignment1, expectedAlignment2), "Score not match 2: " + actualAlignment1 + " / " + actualAlignment2);
        assertEquals(expectedScore, score(actualAlignment1, actualAlignment2), "Score not match 3");
    }

    private static int score(String alignment1, String alignment2) {
        int score = 0;
        for (int i = 0; i < alignment1.length(); i++) {
            if (alignment1.charAt(i) == '-' || alignment2.charAt(i) == '-') {
                score += gapScore;
            } else if (alignment1.charAt(i) != alignment2.charAt(i)) {
                score += mismatchScore;
            } else {
                score += matchScore;
            }
        }
        return score;
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        input.append(Utils.getRandomInteger(1, 10))
                .append(" ")
                .append(Utils.getRandomInteger(1, 10))
                .append(" ")
                .append(Utils.getRandomInteger(1, 10))
                .append(" ");
        switch (type) {
            case TIME_LIMIT_TEST:
                input.append(Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10000))
                        .append(" ")
                        .append(Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10000));
            default:
                input.append(Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10))
                        .append(" ")
                        .append(Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10));
        }
        return input.toString();
    }
}
