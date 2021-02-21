package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MergingTablesTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/merging-tables.csv";

  public MergingTablesTest() {
    super(new MergingTables());
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
    switch (type) {
      case TIME_LIMIT_TEST:
        n = Utils.getRandomInteger(1, 100000);
        m = Utils.getRandomInteger(1, 100000);
        break;
      case STRESS_TEST:
      default:
        n = Utils.getRandomInteger(1, 100);
        m = Utils.getRandomInteger(1, 100);
        break;
    }
    input.append(n).append(" ").append(m);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(Utils.getRandomInteger(0, 10000));
    }
    for (int i = 0; i < m; i++) {
      input.append(" ").append(Utils.getRandomInteger(1, n))
          .append(" ").append(Utils.getRandomInteger(1, n));
    }
    return input.toString();
  }
}
