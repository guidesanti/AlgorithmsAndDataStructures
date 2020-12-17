package edx.pa1;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MaximumPairwiseProductTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/maximum-pairwise-product.csv";

  public MaximumPairwiseProductTest() {
    super(new MaximumPairwiseProduct());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    int n = getRandomInteger(2, 10000);
    int[] numbers = new int[n];
    for (int j = 0; j < n; j++) {
      numbers[j] = getRandomInteger(0, 200000);
    }
    StringBuilder inputBuilder = new StringBuilder();
    inputBuilder.append(n);
    for (int j = 0; j < n; j++) {
      inputBuilder.append(" " + numbers[j]);
    }
    return inputBuilder.toString().trim();
  }
}
