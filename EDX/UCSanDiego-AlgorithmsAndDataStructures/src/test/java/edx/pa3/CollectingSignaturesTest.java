package edx.pa3;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class CollectingSignaturesTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/collecting-signatures.csv";

  public CollectingSignaturesTest() {
    super(new CollectingSignatures());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n"));
  }

  @ParameterizedTest
  @CsvSource({
      "3 1 3 2 5 3 6,1%3",
      "4 4 7 1 3 2 5 5 6,2%3 6",
      "5 1 3 3 5 4 6 5 7 6 7,2%3 6"
  })
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n"));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder stringBuilder = new StringBuilder();

    int n;
    switch (type) {
      case TIME_LIMIT_TEST:
        n = getRandomInteger(1, 100);
        stringBuilder.append(n);
        for (int i = 0; i < n; i++) {
          long a = getRandomLong(0, 1000000000);
          long b = getRandomLong(a, 1000000000);
          stringBuilder.append(" ").append(a);
          stringBuilder.append(" ").append(b);
        }
        break;
      case STRESS_TEST:
      default:
        n = getRandomInteger(1, 10);
        stringBuilder.append(n);
        for (int i = 0; i < n; i++) {
          long a = getRandomLong(0, 100);
          long b = getRandomLong(a, 100);
          stringBuilder.append(" ").append(a);
          stringBuilder.append(" ").append(b);
        }
        break;
    }

    return stringBuilder.toString();
  }
}
