package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SequenceAlignmentWithProfileHMMTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/sequence-alignment-with-profile-hmm.csv";

  public SequenceAlignmentWithProfileHMMTest() {
    super(new SequenceAlignmentWithProfileHMM(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace(" ", "\n");
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
