package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BuildSuffixArrayOfLongStringTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/build-suffix-array-of-long-string.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  private static final char EOF = '$';

  public BuildSuffixArrayOfLongStringTest() {
    super(new BuildSuffixArrayOfLongString(), false, false);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    switch (type) {
      case TIME_LIMIT_TEST:
        return Utils.getRandomString(ALPHABET_SYMBOLS, 200000) + EOF;
      case STRESS_TEST:
      default:
        return Utils.getRandomString(ALPHABET_SYMBOLS, 1000) + EOF;
    }
  }
}
