package edx.pa2;

import edx.common.PATest;
import edx.common.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class ModOfLargeFibonacciNumberTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/mod-of-large-fibonacci-number.csv";

  public ModOfLargeFibonacciNumberTest() {
    super(new ModOfLargeFibonacciNumber());
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

  @ParameterizedTest
  @CsvSource({
    "239 1000,161",
    "2816213588 239,151"
  })
  public void testFinalSolutionWithBigDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    int a;
    int b;
    switch (type) {
      case TIME_LIMIT_TEST:
        a = getRandomInteger(1, 1000000000);
        b = getRandomInteger(2, 100000);
        break;
      case STRESS_TEST:
      default:
        a = getRandomInteger(1, 50);
        b = getRandomInteger(2, 100000);
        break;
    }
    return a + " " + b;
  }
}
