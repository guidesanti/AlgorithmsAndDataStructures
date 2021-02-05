package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ComputingEditDistanceBetweenTwoStringsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa5/computing-edit-distance-between-two-strings.csv";

  public ComputingEditDistanceBetweenTwoStringsTest() {
    super(new ComputingEditDistanceBetweenTwoStrings());
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
    int n1;
    int n2;

    switch (type) {
      case TIME_LIMIT_TEST:
        n1 = getRandomInteger(1, 100);
        n2 = getRandomInteger(1, 100);
        break;
      case STRESS_TEST:
      default:
        n1 = getRandomInteger(1, 10);
        n2 = getRandomInteger(1, 10);
        break;
    }

    StringBuilder input = new StringBuilder();
    for (int i = 0; i < n1; i++) {
      input.append((char) getRandomInteger(97, 122));
    }
    input.append(" ");
    for (int i = 0; i < n2; i++) {
      input.append((char) getRandomInteger(97, 122));
    }
    return input.toString();
  }
}
