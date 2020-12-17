package edx.pa2;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class GreatestCommonDivisorTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/greatest-common-divisor.csv";

  public GreatestCommonDivisorTest() {
    super(new GreatestCommonDivisor());
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
    int a = getRandomInteger(1, Integer.MAX_VALUE);
    int b = getRandomInteger(1, Integer.MAX_VALUE);
    return a + " " + b;
  }
}
