package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class SuffixTreeFromSuffixArrayTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/suffix-tree-from-suffix-array.csv";

//  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public SuffixTreeFromSuffixArrayTest() {
    super(new SuffixTreeFromSuffixArray(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
