package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class CheckBracketsInTheCodeTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/check-brackets-in-the-code.csv";

  public CheckBracketsInTheCodeTest() {
    super(new CheckBracketsInTheCode(), PATestSettings.builder()
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

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    char[] brackets = { '[', ']', '{', '}', '(', ')' };
    int n = Utils.getRandomInteger(1, 100000);
    StringBuilder input = new StringBuilder();
    for (int i = 0; i < n; i++) {
      input.append(Utils.getRandomChar(Utils.CharType.ALL_ASCII));
    }
    return input.toString();
  }
}
