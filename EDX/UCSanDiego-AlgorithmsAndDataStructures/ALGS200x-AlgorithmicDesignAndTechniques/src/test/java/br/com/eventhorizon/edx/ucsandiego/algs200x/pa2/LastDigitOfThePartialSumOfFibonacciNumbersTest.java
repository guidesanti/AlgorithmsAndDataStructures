package br.com.eventhorizon.edx.ucsandiego.algs200x.pa2;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomLong;

public class LastDigitOfThePartialSumOfFibonacciNumbersTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/last-digit-of-the-partial-sum-of-fibonacci-numbers.csv";

  public LastDigitOfThePartialSumOfFibonacciNumbersTest() {
    super(new LastDigitOfThePartialSumOfFibonacciNumbers(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
      "10 200,2"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
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
