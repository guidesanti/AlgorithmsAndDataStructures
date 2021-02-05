package br.com.eventhorizon.edx.ucsandiego.algs200x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SumOfTwoDigitsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/sum-of-two-digits.csv";

  public SumOfTwoDigitsTest() {
    super(new SumOfTwoDigits());
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
    int n = getRandomInteger(0, 2);
    int a = getRandomInteger(1, Integer.MAX_VALUE);
    if (n > 0) {
      a = -a;
    }
    n = getRandomInteger(0, 2);
    int b = getRandomInteger(1, Integer.MAX_VALUE);
    if (n > 0) {
      b = -b;
    }
    return a + " " + b;
  }
}
