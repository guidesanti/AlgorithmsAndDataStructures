package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SuffixArrayPatternMatchingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/suffix-array-pattern-matching.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public SuffixArrayPatternMatchingTest() {
    super(new SuffixArrayPatternMatching());
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
    StringBuilder input = new StringBuilder();
    int numberOfPatterns;
    switch (type) {
      case TIME_LIMIT_TEST:
        input.append(Utils.getRandomString(ALPHABET_SYMBOLS, 500000));
        numberOfPatterns = Utils.getRandomInteger(1, 10000);
        input.append(" ").append(numberOfPatterns);
        for (int i = 0; i < numberOfPatterns; i++) {
          input.append(" ").append(Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000));
        }
      case STRESS_TEST:
      default:
        input.append(Utils.getRandomString(ALPHABET_SYMBOLS, 1000));
        numberOfPatterns = Utils.getRandomInteger(1, 100);
        input.append(" ").append(numberOfPatterns);
        for (int i = 0; i < numberOfPatterns; i++) {
          input.append(" ").append(Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000));
        }
    }
    return input.toString();
  }
}
