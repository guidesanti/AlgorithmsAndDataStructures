package edx.pa2;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.naming.OperationNotSupportedException;

public class SmallFibonacciNumberTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/small-fibonacci-number.csv";

  public SmallFibonacciNumberTest() {
    super(new SmallFibonacciNumber());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testIntermediateSolution1WithSimpleDataSet(String input, String expectedOutput)
      throws OperationNotSupportedException {
    super.testIntermediateSolution1(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    return "" + getRandomInteger(0, 45);
  }
}
