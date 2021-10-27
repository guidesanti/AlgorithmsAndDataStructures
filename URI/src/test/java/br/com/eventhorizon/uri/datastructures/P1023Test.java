package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P1023Test extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/datastructures/p1023/p1023.csv";

  public P1023Test() {
    super(new P1023(), PATestSettings.builder()
        .timeLimitTestEnabled(true)
        .inputFormatFile("datastructures/p1023/p1023-input-format.json")
        .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet1(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet1(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
