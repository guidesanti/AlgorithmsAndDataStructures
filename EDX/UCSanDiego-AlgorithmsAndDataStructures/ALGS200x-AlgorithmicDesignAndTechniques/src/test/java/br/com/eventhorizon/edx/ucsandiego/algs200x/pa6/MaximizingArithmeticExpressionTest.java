package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MaximizingArithmeticExpressionTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa6/maximizing-arithmetic-expression.csv";

  public MaximizingArithmeticExpressionTest() {
    super(new MaximizingArithmeticExpression());
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
