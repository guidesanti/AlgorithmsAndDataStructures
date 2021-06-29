package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class LengthOfLongestPathInGridTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/length-of-longest-path-in-grid.csv";

  public LengthOfLongestPathInGridTest() {
    super(new LengthOfLongestPathInGrid(), false, false);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int numberOfRows;
    int numberOfColumns;
    switch (type) {
      case TIME_LIMIT_TEST:
        numberOfRows = 20;
        numberOfColumns = 20;
        break;
      case STRESS_TEST:
      default:
        numberOfRows = Utils.getRandomInteger(1, 15);
        numberOfColumns = Utils.getRandomInteger(1, 15);
        break;
    }
    input.append(numberOfRows).append(" ");
    input.append(numberOfColumns).append(" ");
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j <= numberOfColumns; j++) {
        input.append(Utils.getRandomInteger(-100, 100)).append(" ");
      }
    }
    input.append("- ");
    for (int i = 0; i <= numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        input.append(Utils.getRandomInteger(-100, 100)).append(" ");
      }
    }
    return input.toString();
  }
}
