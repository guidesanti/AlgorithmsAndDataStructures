package br.com.eventhorizon.edx.ucsandiego.algs202x.pa1;

import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class AddingExitsToMazeTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/adding-exits-to-maze.csv";

  public AddingExitsToMazeTest() {
    super(new AddingExitsToMaze());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
