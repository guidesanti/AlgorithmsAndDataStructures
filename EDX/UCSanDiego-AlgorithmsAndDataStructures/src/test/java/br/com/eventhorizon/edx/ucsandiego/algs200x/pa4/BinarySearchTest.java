package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BinarySearchTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/binary-search.csv";

  public BinarySearchTest() {
    super(new BinarySearch());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n"));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n"));
  }

  @Override
  protected String generateInput(PATestType type) {
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
