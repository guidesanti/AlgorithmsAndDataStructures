package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FindPatternInStringTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/find-pattern-in-string.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public FindPatternInStringTest() {
    super(new FindPatternInString(), false, false);
    TestProperties.setTimeLimit(18000);
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
    String text = Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000000);
    String pattern = Utils.getRandomString(ALPHABET_SYMBOLS, 1, 1000000);
    return pattern + " " + text;
  }
}
