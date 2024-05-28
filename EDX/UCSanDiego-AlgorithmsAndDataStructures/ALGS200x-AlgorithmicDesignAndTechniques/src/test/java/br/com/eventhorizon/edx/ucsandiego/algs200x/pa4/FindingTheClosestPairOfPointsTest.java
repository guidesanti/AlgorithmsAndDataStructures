package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class FindingTheClosestPairOfPointsTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/finding-the-closest-pair-of-points.csv";

  public FindingTheClosestPairOfPointsTest() {
    super(new FindingTheClosestPairOfPoints(), PATestSettings.builder()
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

  @Test
  public void testFinalSolutionWithPathologicalCase() {
    StringBuilder input = new StringBuilder();
    int n = 100000;
    input.append(n);
    for (int i = 0; i < n; i++) {
      input.append(" 0 0");
    }
    super.testSolution(PASolution.FINAL, input.toString(), "0.0000");
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    int n;

    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(2, 100000);
        input.append(n);
        for (int i = 0; i < n; i++) {
          input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
          input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
        }
        break;

      case STRESS_TEST:
      default:
        n = getRandomInteger(2, 10000);
        input.append(n);
        for (int i = 0; i < n; i++) {
          input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
          input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
        }
        break;
    }

    return input.toString();
  }
}
