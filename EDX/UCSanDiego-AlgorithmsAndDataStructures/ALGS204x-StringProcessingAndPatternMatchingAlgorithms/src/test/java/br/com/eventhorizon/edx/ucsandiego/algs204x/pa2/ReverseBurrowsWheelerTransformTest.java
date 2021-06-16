package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.string.bwt.BurrowsWheelerTransform;
import br.com.eventhorizon.string.bwt.ImprovedBurrowsWheelerTransform1;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ReverseBurrowsWheelerTransformTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/reverse-burrows-wheeler-transform.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public ReverseBurrowsWheelerTransformTest() {
    super(new ReverseBurrowsWheelerTransform(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    BurrowsWheelerTransform bwt = new ImprovedBurrowsWheelerTransform1();
    return bwt.transform(Utils.getRandomString(ALPHABET_SYMBOLS, 1000000));
  }
}
