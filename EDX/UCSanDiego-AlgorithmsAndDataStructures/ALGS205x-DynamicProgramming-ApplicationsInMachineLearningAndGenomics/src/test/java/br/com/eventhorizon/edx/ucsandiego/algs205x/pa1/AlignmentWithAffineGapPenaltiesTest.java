package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PASystemSettings;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AlignmentWithAffineGapPenaltiesTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/alignment-with-affine-gap-penalties.csv";

  private static int matchScore;

  private static int mismatchScore;

  private static int gapOpeningScore;

  private static int gapExtensionScore;

  public AlignmentWithAffineGapPenaltiesTest() {
    super(new AlignmentWithAffineGapPenalties(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .timeLimit(2500)
            .compareTestEnabled(true)
            .build());
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

    reset(input);
    pa.trivialSolution();

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

  @Test
  @Override
  public void compareTest() {
    if (!PASystemSettings.isCompareTestEnabled().orElse(settings.isCompareTestEnabled())) {
      log.warn("Compare test status: {}", Status.SKIPPED);
      return;
    }
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.COMPARE_TEST, null);
      log.info("Stress test " + i + " input: " + input);

      String[] values = input.split(" ");
      matchScore = Integer.parseInt(values[0]);
      mismatchScore = -Integer.parseInt(values[1]);
      gapOpeningScore = -Integer.parseInt(values[2]);
      gapExtensionScore = -Integer.parseInt(values[3]);

      // Run and compare results
      reset(input);
      pa.trivialSolution();
      String result1 = getActualOutput();
      values = result1.split("\n");
      int result1ActualScore = Integer.parseInt(values[0]);
      String result1ActualAlignment1 = values[1];
      String result1ActualAlignment2 = values[2];
      int result1Score = score(result1ActualAlignment1, result1ActualAlignment2);

      reset(input);
      pa.finalSolution();
      String result2 = getActualOutput();
      values = result2.split("\n");
      int result2ActualScore = Integer.parseInt(values[0]);
      String result2ActualAlignment1 = values[1];
      String result2ActualAlignment2 = values[2];
      int result2Score = score(result2ActualAlignment1, result2ActualAlignment2);

      if (result1Score == result1ActualScore &&
          result2Score == result2ActualScore &&
          result1Score == result2Score) {
        log.info("Stress test " + i + " status: PASSED");
      } else {
        log.info("Stress test " + i + " status: FAILED");
        log.info("Stress test " + i + " result 1:  " + result1);
        log.info("Stress test " + i + " result 2:  " + result2);
        throw new RuntimeException("Stress test failed");
      }

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > settings.getCompareTestDuration()) {
        return;
      }
    }
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
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    if (type == PATestType.TIME_LIMIT_TEST) {
      return Utils.getRandomInteger(1, 10) + " "
              + Utils.getRandomInteger(1, 10) + " "
              + Utils.getRandomInteger(1, 10) + " "
              + Utils.getRandomInteger(1, 10) + " "
              + Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000) + " "
              + Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000);
    }
    return Utils.getRandomInteger(1, 10) + " "
            + Utils.getRandomInteger(1, 10) + " "
            + Utils.getRandomInteger(1, 10) + " "
            + Utils.getRandomInteger(1, 10) + " "
            + Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 3) + " "
            + Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 2);
  }
}
