package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.string.bwt.ImprovedBurrowsWheelerTransform1;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BWTMatchingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/bwt-matching.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public BWTMatchingTest() {
    super(new BWTMatching(), false, false);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int textLength;
    int n = Utils.getRandomInteger(1, 5000);
    switch (type) {
      case TIME_LIMIT_TEST:
        textLength = 10000;
        break;
      case STRESS_TEST:
      default:
        textLength = 1000;
        break;
    }
    String text = Utils.getRandomString(ALPHABET_SYMBOLS, 1, textLength);
    String bwt = new ImprovedBurrowsWheelerTransform1().transform(text);
    input.append(bwt).append(" ").append(n);
    for (int i = 0; i < n; i++) {
      int patternLength = Utils.getRandomInteger(1, 1000);
      if (patternLength > text.length()) {
        patternLength = (text.length() / 2) + 1;
      }
      if (i % 2 == 0) {
        input.append(" ").append(Utils.getRandomString(ALPHABET_SYMBOLS, 1, patternLength));
      } else {
        int start = Utils.getRandomInteger(0, text.length() / 2);
        int end = Utils.getRandomInteger(start + 1, text.length());
        input.append(" ").append(text, start, end);
      }
    }
    return input.toString();
  }
}
