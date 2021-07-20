package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MiddleEdgeInAlignmentGraphTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/middle-edge-in-alignment-graph.csv";

  public MiddleEdgeInAlignmentGraphTest() {
    super(new MiddleEdgeInAlignmentGraph(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1, delimiterString = ";")
  public void testNaivelSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1, delimiterString = ";")
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    return Utils.getRandomInteger(1,10) + " " +
        Utils.getRandomInteger(1, 10) + " " +
        Utils.getRandomInteger(1, 10) + " " +
        Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000) + " " +
        Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000);
  }
}
