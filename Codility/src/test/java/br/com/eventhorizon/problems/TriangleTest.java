package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class TriangleTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/triangle.csv";

  public TriangleTest() {
    super(new Triangle(), PATestSettings.builder()
        .compareTestEnabled(true)
        .stressTestEnabled(false)
        .timeLimitTestEnabled(true)
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
    StringBuilder sb = new StringBuilder();

    int n;

    if (type == PATestType.TIME_LIMIT_TEST) {
      n = 100_000;
    } else {
      n = getRandomInteger(0, 1000);
    }

    sb.append(n).append("\n");
    for (int i = 0; i < n; i++) {
      sb.append(getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE)).append(" ");
    }
    sb.append("\n");

    return sb.toString();
  }
}
