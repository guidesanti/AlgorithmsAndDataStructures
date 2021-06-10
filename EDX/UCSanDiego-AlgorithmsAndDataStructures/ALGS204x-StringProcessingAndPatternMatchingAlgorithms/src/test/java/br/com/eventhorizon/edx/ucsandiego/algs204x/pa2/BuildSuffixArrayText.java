package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BuildSuffixArrayText extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/build-suffix-array.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public BuildSuffixArrayText() {
    super(new BuildSuffixArray(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    return Utils.getRandomString(ALPHABET_SYMBOLS, 10000) + "$";
  }
}
