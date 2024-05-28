package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class GeneralizedMultiplePatternMatchingTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/generalized-multiple-pattern-matching.csv";

  public GeneralizedMultiplePatternMatchingTest() {
    super(new GeneralizedMultiplePatternMatching());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  @Disabled("Trivial solution is not implemented")
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    if (expectedOutput == null) {
      expectedOutput = "";
    }
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }
}
