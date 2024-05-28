package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaximizingArithmeticExpressionTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa6/maximizing-arithmetic-expression.csv";

  public MaximizingArithmeticExpressionTest() {
    super(new MaximizingArithmeticExpression(), PATestSettings.builder()
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
    char[] operations = { '+', '-', '*' };
    int n;

    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(1, 14);
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 5);
        break;
    }

    for (int i = 0; i < n - 1; i++) {
      input.append(getRandomInteger(0, 9));
      input.append(operations[getRandomInteger(0, 2)]);
    }
    input.append(getRandomInteger(0, 9));
    return input.toString();
  }
}
