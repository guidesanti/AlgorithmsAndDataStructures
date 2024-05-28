package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class LongestCommonSubsequenceOfThreeSequencesTest extends PATestBase {
  private static final String SIMPLE_DATA_SET =
      "/test-dataset/pa5/longest-common-subsequence-of-three-sequences.csv";

  public LongestCommonSubsequenceOfThreeSequencesTest() {
    super(new LongestCommonSubsequenceOfThreeSequences(), PATestSettings.builder()
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

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    int n;
    int m;
    int l;
    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(1, 100);
        m = getRandomInteger(1, 100);
        l = getRandomInteger(1, 100);
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 5);
        m = getRandomInteger(1, 5);
        l = getRandomInteger(1, 5);
        break;
    }
    input.append(n);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
    }
    input.append(" ").append(m);
    for (int i = 0; i < m; i++) {
      input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
    }
    input.append(" ").append(l);
    for (int i = 0; i < l; i++) {
      input.append(" ").append(getRandomInteger(1, 2000000001) - 1000000001);
    }
    return input.toString();
  }
}
