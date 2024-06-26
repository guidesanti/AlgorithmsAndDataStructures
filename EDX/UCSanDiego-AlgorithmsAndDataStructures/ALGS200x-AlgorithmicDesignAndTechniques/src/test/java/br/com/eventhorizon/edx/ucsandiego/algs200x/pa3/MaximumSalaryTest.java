package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaximumSalaryTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/maximum-salary.csv";

  public MaximumSalaryTest() {
    super(new MaximumSalary(), PATestSettings.builder()
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
  @CsvSource({
      "2 391 3,3913",
      "2 3 391,3913",
      "2 397 39,39739",
      "2 39 397,39739",
      "2 722 72,72722",
      "2 72 722,72722",
      "8 722 740 72 588 539 756 710 363,75674072722710588539363"
  })
  public void testNaiveSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
      "2 9 998,9998",
      "3 9 99 998,999998",
      "2 1 11,111",
      "2 29 292,29292",
      "2 292 29,29292",
      "2 929 92,92992",
      "2 92 929,92992",
      "2 391 3,3913",
      "2 3 391,3913",
      "2 397 39,39739",
      "2 39 397,39739",
      "2 722 72,72722",
      "2 72 722,72722",
      "8 722 740 72 588 539 756 710 363,75674072722710588539363",
      "11 1 2 3 4 5 6 7 8 9 0 00,987654321000",
      "12 99 1 2 3 4 5 6 7 8 9 0 00,99987654321000"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder stringBuilder = new StringBuilder();
    int n;
    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(1, 100);
        stringBuilder.append(n);
        for (int i = 0; i < n; i++) {
          stringBuilder.append(" ").append(getRandomInteger(1, 1000));
        }
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 9);
        stringBuilder.append(n);
        for (int i = 0; i < n; i++) {
          stringBuilder.append(" ").append(getRandomInteger(1, 1000));
        }
        break;
    }
    return stringBuilder.toString();
  }
}
