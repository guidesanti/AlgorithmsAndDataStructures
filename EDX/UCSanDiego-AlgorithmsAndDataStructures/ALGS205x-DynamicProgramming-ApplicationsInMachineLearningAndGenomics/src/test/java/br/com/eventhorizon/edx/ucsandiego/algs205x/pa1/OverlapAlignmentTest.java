package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OverlapAlignmentTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/overlap-alignment.csv";

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  public OverlapAlignmentTest() {
    super(new OverlapAlignment());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    String[] values = input.split(" ");
    matchScore = Integer.parseInt(values[0]);
    mismatchScore = -Integer.parseInt(values[1]);
    gapScore = -Integer.parseInt(values[2]);

    values = expectedOutput.split("%");
    int expectedScore = Integer.parseInt(values[0]);
    String expectedAlignment1 = values[1];
    String expectedAlignment2 = values[2];

    reset(input);
    pa.finalSolution();

    values = getActualOutput().split("\n");
    int actualScore = Integer.parseInt(values[0]);
    String actualAlignment1 = values[1];
    String actualAlignment2 = values[2];

    assertEquals(expectedScore, actualScore);
    assertEquals(expectedScore, score(expectedAlignment1, expectedAlignment2));
    assertEquals(expectedScore, score(actualAlignment1, actualAlignment2));
  }

  private static int score(String alignment1, String alignment2) {
    int score = 0;
    for (int i = 0; i < alignment1.length(); i++) {
      if (alignment1.charAt(i) == '-' || alignment2.charAt(i) == '-') {
        score += gapScore;
      } else if (alignment1.charAt(i) != alignment2.charAt(i)) {
        score += mismatchScore;
      } else {
        score += matchScore;
      }
    }
    return score;
  }
}
