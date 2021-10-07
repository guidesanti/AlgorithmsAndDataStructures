package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class FindKUniversalCircularStringTestTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/find-k-universal-circular-string.csv";

  public FindKUniversalCircularStringTestTest() {
    super(new FindKUniversalCircularString(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(final String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }
}
