package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.PASolution;
import br.com.eventhorizon.common.pa.PAv2TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P1215Test extends PAv2TestBase {

  private static final String SIMPLE_DATA_SET = "/datastructures/p1215.csv";

  public P1215Test() {
    super(new P1215());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
