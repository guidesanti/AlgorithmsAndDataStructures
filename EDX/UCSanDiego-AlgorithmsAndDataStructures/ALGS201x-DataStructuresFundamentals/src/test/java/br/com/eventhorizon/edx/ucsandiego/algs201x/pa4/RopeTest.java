package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class RopeTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/rope.csv";

  public RopeTest() {
    super(new Rope(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .timeLimit(6000)
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
    StringBuilder input = new StringBuilder();
    int textLength;
    int numberOfQueries;
    switch (type) {
      case TIME_LIMIT_TEST:
        textLength = Utils.getRandomInteger(1, 300000);
        numberOfQueries = 100000;
        break;
      case STRESS_TEST:
      default:
        textLength = Utils.getRandomInteger(1, 1000);
        numberOfQueries = 100;
        break;
    }
    input
        .append(Utils.getRandomString(Utils.CharType.LOWERCASE_ALPHABETICAL_CHARS, textLength))
        .append(" ").append(numberOfQueries);
    while (numberOfQueries-- > 0) {
      int i = Utils.getRandomInteger(0, textLength - 1);
      int j = Utils.getRandomInteger(i, textLength - 1);
      int k = Utils.getRandomInteger(0, textLength - (j - i + 1));
      input.append(" ").append(i);
      input.append(" ").append(j);
      input.append(" ").append(k);
    }
    return input.toString();
  }
}
