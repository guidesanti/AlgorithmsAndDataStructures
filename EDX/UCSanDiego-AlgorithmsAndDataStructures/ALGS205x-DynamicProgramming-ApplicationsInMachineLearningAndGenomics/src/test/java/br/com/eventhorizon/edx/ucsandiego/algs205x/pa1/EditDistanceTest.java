package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class EditDistanceTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/edit-distance.csv";

  public EditDistanceTest() {
    super(new EditDistance());
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
    switch (type) {
      case TIME_LIMIT_TEST:
        return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000) +
            " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
      case STRESS_TEST:
      default:
        return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10) +
            " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10);
    }
  }
}
