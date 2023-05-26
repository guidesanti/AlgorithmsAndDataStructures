package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BinaryGapTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/binary-gap.csv";

  public BinaryGapTest() {
    super(new BinaryGap(), PATestSettings.builder().timeLimitTestEnabled(false).build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet1(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
