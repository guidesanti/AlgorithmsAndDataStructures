package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FindPatternInStringTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/find-pattern-in-string.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public FindPatternInStringTest() {
    super(new FindPatternInString(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .timeLimit(18000)
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
    String text = Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000000);
    String pattern = Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000000);
    return pattern + " " + text;
  }
}
