package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class PartitioningSouvenirsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa6/partitioning-souvenirs.csv";

  public PartitioningSouvenirsTest() {
    super(new PartitioningSouvenirs());
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
        n = getRandomInteger(1, 20);
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 9);
        break;
    }
    input.append(n);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(getRandomInteger(1, 30));
    }
    return input.toString();
  }
}
