package br.com.eventhorizon.edx.ucsandiego.algs200x.pa2;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.naming.OperationNotSupportedException;

public class LastDigitOfLargeFibonacciNumberTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/last-digit-of-large-fibonacci-number.csv";

  public LastDigitOfLargeFibonacciNumberTest() {
    super(new LastDigitOfLargeFibonacciNumber());
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
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
      "331,9",
      "327305,5"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    long a;
    switch (type) {
      case TIME_LIMIT_TEST:
        a = getRandomLong(0, 10000000);
        break;
      case STRESS_TEST:
      default:
        a = getRandomLong(0, 50);
        break;
    }

    return "" + a;
  }
}
