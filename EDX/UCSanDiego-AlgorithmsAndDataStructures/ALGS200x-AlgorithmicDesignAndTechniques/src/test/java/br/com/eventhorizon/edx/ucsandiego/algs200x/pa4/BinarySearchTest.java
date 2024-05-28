package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class BinarySearchTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/binary-search.csv";

  public BinarySearchTest() {
    super(new BinarySearch(), PATestSettings.builder()
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
    StringBuilder stringBuilder = new StringBuilder();
    int n = getRandomInteger(1, 10000);
    int start = 1;
    stringBuilder.append(n);
    for (int i = 0; i < n; i++) {
      stringBuilder.append(" ").append(start);
      start++;
      start = getRandomInteger(start, start + getRandomInteger(1, 100));
    }
    int k = getRandomInteger(1, 10000);
    stringBuilder.append(" ").append(k);
    for (int i = 0; i < k; i++) {
      stringBuilder.append(" ").append(getRandomInteger(1, 100));
    }
    return stringBuilder.toString();
  }
}
