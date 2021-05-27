package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CheckBracketsInTheCodeTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/check-brackets-in-the-code.csv";

  public CheckBracketsInTheCodeTest() {
    super(new CheckBracketsInTheCode());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n"));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n"));
  }

  @Override
  protected String generateInput(PATestType type) {
    char[] brackets = { '[', ']', '{', '}', '(', ')' };
    int n = Utils.getRandomInteger(1, 100000);
    StringBuilder input = new StringBuilder();
    for (int i = 0; i < n; i++) {
      input.append(Utils.getRandomChar(Utils.CharType.ALL_ASCII));
    }
    return input.toString();
  }
}
