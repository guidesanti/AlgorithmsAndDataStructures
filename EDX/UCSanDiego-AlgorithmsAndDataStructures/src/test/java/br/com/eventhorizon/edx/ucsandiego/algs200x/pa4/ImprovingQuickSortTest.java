package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ImprovingQuickSortTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/improving-quick-sort.csv";

  public ImprovingQuickSortTest() {
    super(new ImprovingQuickSort());
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
    int n;

    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(1, 100000);
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 1000);
        break;
    }

    input.append(n);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(getRandomInteger(1, 1000000000));
    }

    return input.toString();
  }
}
