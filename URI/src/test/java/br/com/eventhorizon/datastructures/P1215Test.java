package br.com.eventhorizon.datastructures;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P1215Test extends PATest {

  private static final String SIMPLE_DATA_SET = "/datastructures/p1215.csv";

  public P1215Test() {
    super(new P1215(), false, true);
    TestProperties.setTimeLimit(1000);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }
}
