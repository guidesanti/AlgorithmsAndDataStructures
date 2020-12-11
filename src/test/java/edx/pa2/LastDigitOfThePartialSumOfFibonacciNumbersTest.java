package edx.pa2;

import edx.common.PATest;
import edx.common.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class LastDigitOfThePartialSumOfFibonacciNumbersTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/last-digit-of-the-partial-sum-of-fibonacci-numbers.csv";

  public LastDigitOfThePartialSumOfFibonacciNumbersTest() {
    super(new LastDigitOfThePartialSumOfFibonacciNumbers());
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
      "10 200,2"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    long a;
    long b;
    switch (type) {
      case TIME_LIMIT_TEST:
        a = getRandomLong(0, 1000000000000000000L - 1);
        b = getRandomLong(a + 1, 1000000000000000000L);
        break;
      case STRESS_TEST:
      default:
        a = getRandomLong(0, 49);
        b = getRandomLong(a + 1, 50);
        break;
    }

    return a + " " + b;
  }
}
