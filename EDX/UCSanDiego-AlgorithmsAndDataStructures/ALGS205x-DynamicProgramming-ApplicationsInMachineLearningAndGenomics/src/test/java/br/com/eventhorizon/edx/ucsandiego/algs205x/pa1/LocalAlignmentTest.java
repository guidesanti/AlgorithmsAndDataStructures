package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalAlignmentTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/local-alignment.csv";

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  public LocalAlignmentTest() {
    super(new LocalAlignment(), true, true);
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

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();

    values = getActualOutput().split("\n");
    int actualScore = Integer.parseInt(values[0]);
    String actualAlignment1 = values[1];
    String actualAlignment2 = values[2];

    assertEquals(expectedScore, actualScore);
    assertEquals(expectedAlignment1.length(), actualAlignment1.length());
    assertEquals(expectedAlignment2.length(), actualAlignment2.length());
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
