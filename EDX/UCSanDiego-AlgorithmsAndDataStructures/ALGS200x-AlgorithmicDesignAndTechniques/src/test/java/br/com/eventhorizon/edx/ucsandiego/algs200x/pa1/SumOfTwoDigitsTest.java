package br.com.eventhorizon.edx.ucsandiego.algs200x.pa1;

import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class SumOfTwoDigitsTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/sum-of-two-digits.csv";

  public SumOfTwoDigitsTest() {
    super(new SumOfTwoDigits(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
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
