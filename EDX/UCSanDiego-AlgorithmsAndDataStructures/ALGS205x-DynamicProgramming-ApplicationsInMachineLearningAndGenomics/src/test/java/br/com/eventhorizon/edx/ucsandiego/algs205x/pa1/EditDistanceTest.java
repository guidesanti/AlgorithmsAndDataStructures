package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class EditDistanceTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/edit-distance.csv";

  public EditDistanceTest() {
    super(new EditDistance(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
