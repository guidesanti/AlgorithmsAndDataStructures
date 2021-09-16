package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TipRemovalTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/tip-removal.csv";

  public TipRemovalTest() {
    super(new TipRemoval(), true, true);
    TestProperties.setTimeLimit(30000);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }
}
