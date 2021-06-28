package br.com.eventhorizon.string.misc;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

abstract class ShortestNonSharedPatternTest {

  private static final Logger LOGGER = Logger.getLogger(ShortestNonSharedPatternTest.class.getName());

  private static final String DATA_SET = "/string/misc/shortest-non-shared-substring.csv";

  private final ShortestNonSharedPattern shortestNonSharedPattern;

  ShortestNonSharedPatternTest(ShortestNonSharedPattern shortestNonSharedPattern) {
    this.shortestNonSharedPattern = shortestNonSharedPattern;
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  void testFind(String text1, String text2, int expectedShortestNonSharedPatternLength) {
    String result = shortestNonSharedPattern.find(text1, text2);
    if (expectedShortestNonSharedPatternLength == 0) {
      assertNull(result);
    } else {
      assertEquals(expectedShortestNonSharedPatternLength, result.length());
      assertTrue(match(text1, result));
      assertFalse(match(text2, result));
    }
  }

  @Test
  void testFind() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      String text1 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 2000);
      String text2 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 2000);
      String result = null;
      Exception exception = null;
      try {
        result = shortestNonSharedPattern.find(text1, text2);
      } catch (Exception e) {
        exception = e;
      }
      if (exception != null || (result != null && match(text1, result) && match(text2, result))) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text1: " + text1);
        LOGGER.info("Stress test " + test + " text2: " + text2);
        LOGGER.info("Stress test " + test + " result: " + result);
        if (exception != null) {
          LOGGER.info("Stress test " + test + " exception: " + exception);
          exception.printStackTrace();
        }
        fail();
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  @Test
  void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    if (shortestNonSharedPattern instanceof NaiveShortestNonSharedPattern) {
      LOGGER.info("Skipping stress test for NaiveShortestNonSharedSubstring to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      NaiveShortestNonSharedPattern
          naiveShortestNonSharedSubstring = new NaiveShortestNonSharedPattern();
      String text1 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10);
      String text2 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10);
      String result1 = naiveShortestNonSharedSubstring.find(text1, text2);
      String result2 = shortestNonSharedPattern.find(text1, text2);
      if (result1 == null && result2 != null || result1 != null && result2 == null
          || result1 != null && result1.length() != result2.length()) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text1: " + text1);
        LOGGER.info("Stress test " + test + " text2: " + text2);
        LOGGER.info("Stress test " + test + " result1: " + result1);
        LOGGER.info("Stress test " + test + " result2: " + result2);
        fail();
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  private boolean match(String text, String pattern) {
    for (int textIndex = 0; textIndex <= text.length() - pattern.length(); textIndex++) {
      boolean match = true;
      for (int offset = 0; offset < pattern.length(); offset++) {
        if (text.charAt(textIndex + offset) != pattern.charAt(offset)) {
          match = false;
          break;
        }
      }
      if (match) {
        return true;
      }
    }
    return false;
  }
}
