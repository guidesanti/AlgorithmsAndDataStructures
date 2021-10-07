package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ParallelProcessingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/parallel-processing.csv";

  public ParallelProcessingTest() {
    super(new ParallelProcessing());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
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
