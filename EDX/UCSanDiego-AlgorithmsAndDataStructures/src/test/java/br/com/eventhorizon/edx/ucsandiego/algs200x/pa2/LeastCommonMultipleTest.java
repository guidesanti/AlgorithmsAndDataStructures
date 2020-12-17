package br.com.eventhorizon.edx.ucsandiego.algs200x.pa2;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LeastCommonMultipleTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/least-common-multiple.csv";

  public LeastCommonMultipleTest() {
    super(new LeastCommonMultiple());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    int a;
    int b;
    switch (type) {
      case TIME_LIMIT_TEST:
        a = getRandomInteger(1, 2000000000);
        b = getRandomInteger(1, 2000000000);
        break;
      case STRESS_TEST:
      default:
        a = getRandomInteger(1, 1000);
        b = getRandomInteger(1, 1000);
        break;
    }
    return a + " " + b;
  }
}
