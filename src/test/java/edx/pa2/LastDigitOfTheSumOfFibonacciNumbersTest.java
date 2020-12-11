package edx.pa2;

import edx.common.PATest;
import edx.common.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.naming.OperationNotSupportedException;

public class LastDigitOfTheSumOfFibonacciNumbersTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/last-digit-of-the-sum-of-fibonacci-numbers.csv";

  public LastDigitOfTheSumOfFibonacciNumbersTest() {
    super(new LastDigitOfTheSumOfFibonacciNumbers());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testIntermediateSolution1WithSimpleDataSet(String input, String expectedOutput)
      throws OperationNotSupportedException {
    super.testIntermediateSolution1(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testIntermediateSolution2WithSimpleDataSet(String input, String expectedOutput)
      throws OperationNotSupportedException {
    super.testIntermediateSolution2(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
    "100,5"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    long a;
    switch (type) {
      case TIME_LIMIT_TEST:
        a = getRandomLong(0, 100000000000000L);
        break;
      case STRESS_TEST:
      default:
        a = getRandomLong(0, 50);
        break;
    }

    return "" + a;
  }
}
