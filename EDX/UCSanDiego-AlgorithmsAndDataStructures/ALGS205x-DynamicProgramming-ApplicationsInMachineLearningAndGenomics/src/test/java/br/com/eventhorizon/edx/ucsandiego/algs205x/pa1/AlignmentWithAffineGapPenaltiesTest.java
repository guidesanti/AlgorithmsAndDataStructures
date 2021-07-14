package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlignmentWithAffineGapPenaltiesTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/alignment-with-affine-gap-penalties.csv";

  private static int matchScore;

  private static int mismatchScore;

  private static int gapOpeningScore;

  private static int gapExtensionScore;

  public AlignmentWithAffineGapPenaltiesTest() {
    super(new AlignmentWithAffineGapPenalties(), false, false);
    TestProperties.setTimeLimit(2500);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    String[] values = input.split(" ");
    matchScore = Integer.parseInt(values[0]);
    mismatchScore = -Integer.parseInt(values[1]);
    gapOpeningScore = -Integer.parseInt(values[2]);
    gapExtensionScore = -Integer.parseInt(values[3]);

    values = expectedOutput.split("%");
    int expectedScore = Integer.parseInt(values[0]);
    String expectedAlignment1 = values[1];
    String expectedAlignment2 = values[2];

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();

    values = getActualOutput().split("\n");
    int actualScore = Integer.parseInt(values[0]);
    String actualAlignment1 = values[1];
    String actualAlignment2 = values[2];

    assertEquals(expectedScore, actualScore);
    assertEquals(expectedScore, score(expectedAlignment1, expectedAlignment2));
    assertEquals(expectedScore, score(actualAlignment1, actualAlignment2));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    String[] values = input.split(" ");
    matchScore = Integer.parseInt(values[0]);
    mismatchScore = -Integer.parseInt(values[1]);
    gapOpeningScore = -Integer.parseInt(values[2]);
    gapExtensionScore = -Integer.parseInt(values[3]);

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

//    assertEquals(expectedScore, actualScore);
    assertEquals(expectedScore, score(expectedAlignment1, expectedAlignment2));
    assertEquals(expectedScore, score(actualAlignment1, actualAlignment2));
  }

  private static int score(String alignment1, String alignment2) {
    int score = 0;
    for (int i = 0; i < alignment1.length(); i++) {
      if ((charAt(alignment1, i - 1) != '-' && charAt(alignment1, i) == '-') ||
          (charAt(alignment2, i - 1) != '-' && charAt(alignment2, i) == '-')) {
        score += gapOpeningScore;
      } else if ((charAt(alignment1, i - 1) == '-' && charAt(alignment1, i) == '-') ||
          (charAt(alignment2, i - 1) == '-' && charAt(alignment2, i) == '-')) {
        score += gapExtensionScore;
      } else if (alignment1.charAt(i) != alignment2.charAt(i)) {
        score += mismatchScore;
      } else {
        score += matchScore;
      }
    }
    return score;
  }

  private static char charAt(String string, int index) {
    if (index < 0) {
      return ' ';
    }
    return string.charAt(index);
  }

  @Override
  protected String generateInput(PATestType type) {
    switch (type) {
      case TIME_LIMIT_TEST:
        return Utils.getRandomInteger(1,10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000) + " " +
            Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000);
      case STRESS_TEST:
      default:
        return Utils.getRandomInteger(1,10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomInteger(1, 10) + " " +
            Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 10) + " " +
            Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 10);
    }
  }
}
