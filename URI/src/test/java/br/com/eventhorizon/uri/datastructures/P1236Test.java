package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P1236Test extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/datastructures/p1236/p1236.csv";

  public P1236Test() {
    super(new P1236(), PATestSettings.builder()
        .timeLimitTestEnabled(false)
        .timeLimit(3000)
        .compareTestEnabled(false)
        .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1, ignoreLeadingAndTrailingWhitespace = false)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
