package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.string.bwt.BurrowsWheelerTransform;
import br.com.eventhorizon.string.bwt.ImprovedBurrowsWheelerTransform1;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ReverseBurrowsWheelerTransformTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/reverse-burrows-wheeler-transform.csv";

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  public ReverseBurrowsWheelerTransformTest() {
    super(new ReverseBurrowsWheelerTransform(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  @Disabled("Trivial Solution is not implemented")
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    BurrowsWheelerTransform bwt = new ImprovedBurrowsWheelerTransform1();
    return bwt.transform(Utils.getRandomString(ALPHABET_SYMBOLS, 1000000));
  }
}
