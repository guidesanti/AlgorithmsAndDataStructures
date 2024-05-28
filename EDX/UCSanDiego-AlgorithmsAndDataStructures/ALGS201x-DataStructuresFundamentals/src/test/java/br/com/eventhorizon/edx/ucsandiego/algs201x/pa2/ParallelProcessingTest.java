package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ParallelProcessingTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/parallel-processing.csv";

  public ParallelProcessingTest() {
    super(new ParallelProcessing(), PATestSettings.builder()
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
    StringBuilder input = new StringBuilder();
    int n;
    int m;
    if (type == PATestType.TIME_LIMIT_TEST) {
      n = Utils.getRandomInteger(1, 100000);
      m = Utils.getRandomInteger(1, 100000);
    } else {
      n = Utils.getRandomInteger(1, 100);
      m = Utils.getRandomInteger(1, 100);
    }
    input.append(n).append(" ").append(m);
    for (int i = 0; i < m; i++) {
      input.append(" ").append(Utils.getRandomLong(0, 1000000000));
    }
    return input.toString();
  }
}
