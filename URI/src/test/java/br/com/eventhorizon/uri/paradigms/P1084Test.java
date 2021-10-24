package br.com.eventhorizon.uri.paradigms;

import br.com.eventhorizon.common.pa.*;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P1084Test extends PAv2TestBase {

  private static final String SIMPLE_DATA_SET = "/paradigms/p1084.csv";

  public P1084Test() {
    super(new P1084(), PATestSettings.builder()
        .skipTimeLimitTest(false)
        .timeLimit(1000)
        .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    int n;
    int d;
    switch (type) {
      case TIME_LIMIT_TEST:
        d = Utils.getRandomInteger(1, 99998);
        n = Utils.getRandomInteger(d + 1, 100000);
        break;
      case STRESS_TEST:
      default:
        d = Utils.getRandomInteger(1, 98);
        n = Utils.getRandomInteger(d + 1, 100);
        break;
    }
    input.append(n).append(" ").append(d).append("\n");
    input.append(Utils.getRandomString(Utils.CharType.NUMERICAL_CHARS, n)).append("\n");
    return input.toString();
  }
}
