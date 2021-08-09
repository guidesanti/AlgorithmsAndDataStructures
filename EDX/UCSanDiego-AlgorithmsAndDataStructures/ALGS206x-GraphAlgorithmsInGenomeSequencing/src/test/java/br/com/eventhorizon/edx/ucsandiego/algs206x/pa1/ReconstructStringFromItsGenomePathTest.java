package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ReconstructStringFromItsGenomePathTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/reconstruct-string-from-its-genome-path.csv";

  public ReconstructStringFromItsGenomePathTest() {
    super(new ReconstructStringFromItsGenomePath(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
