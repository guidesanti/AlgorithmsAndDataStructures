package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class FrogJumpTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/frog-jump.csv";

  public FrogJumpTest() {
    super(new FrogJump(), PATestSettings.builder()
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

    int x = getRandomInteger(1, 1_000_000_000);
    int y = getRandomInteger(x, 1_000_000_000);
    int d = getRandomInteger(1, 1_000_000_000);

    switch (type) {
      case COMPARE_TEST:
        x = getRandomInteger(1, 1_000_000);
        y = getRandomInteger(x, 1_000_000);
        d = getRandomInteger(1, 1_000_000);
        break;
      case STRESS_TEST:
      case TIME_LIMIT_TEST:
        x = getRandomInteger(1, 1_000_000_000);
        y = getRandomInteger(x, 1_000_000_000);
        d = getRandomInteger(1, 1_000_000_000);
        break;
    }

    sb.append(x).append(" ").append(y).append(" ").append(d).append("\n");

    return sb.toString();
  }
}
