package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaximumAmountOfGoldTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa6/maximum-amount-of-gold.csv";

  public MaximumAmountOfGoldTest() {
    super(new MaximumAmountOfGold(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
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
    int capacity;
    int n;
    switch (type) {
      case TIME_LIMIT_TEST:
        capacity = getRandomInteger(1, 10000);
        n = getRandomInteger(1, 300);
        break;
      case STRESS_TEST:
      default:
        capacity = getRandomInteger(1, 10000);
        n = getRandomInteger(1, 10);
        break;
    }
    input.append(capacity).append(" ").append(n);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(getRandomInteger(0, 100000));
    }
    return input.toString();
  }
}
