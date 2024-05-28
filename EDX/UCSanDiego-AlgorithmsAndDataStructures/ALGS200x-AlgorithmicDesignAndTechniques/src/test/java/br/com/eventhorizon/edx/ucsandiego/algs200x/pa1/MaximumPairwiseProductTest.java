package br.com.eventhorizon.edx.ucsandiego.algs200x.pa1;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaximumPairwiseProductTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/maximum-pairwise-product.csv";

  public MaximumPairwiseProductTest() {
    super(new MaximumPairwiseProduct(), PATestSettings.builder()
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
    int n = getRandomInteger(2, 10000);
    int[] numbers = new int[n];
    for (int j = 0; j < n; j++) {
      numbers[j] = getRandomInteger(0, 200000);
    }
    StringBuilder inputBuilder = new StringBuilder();
    inputBuilder.append(n);
    for (int j = 0; j < n; j++) {
      inputBuilder.append(" ").append(numbers[j]);
    }
    return inputBuilder.toString().trim();
  }
}
