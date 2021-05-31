package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class GeneralizedMultiplePatternMatchingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/generalized-multiple-pattern-matching.csv";

  public GeneralizedMultiplePatternMatchingTest() {
    super(new GeneralizedMultiplePatternMatching(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    if (expectedOutput == null) {
      expectedOutput = "";
    }
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
