package br.com.eventhorizon.edx.ucsandiego.algs202x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MinimumNumberOfFlightSegmentsTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/minimum-number-of-flight-segments.csv";

  public MinimumNumberOfFlightSegmentsTest() {
    super(new MinimumNumberOfFlightSegments());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
