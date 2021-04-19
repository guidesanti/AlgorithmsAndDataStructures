package br.com.eventhorizon.edx.ucsandiego.algs202x.pa4;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class DetectingAnomaliesTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/detecting-anomalies.csv";

  public DetectingAnomaliesTest() {
    super(new DetectingAnomalies(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    return null;
  }
}
