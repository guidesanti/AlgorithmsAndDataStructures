package br.com.eventhorizon.string.misc;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

abstract class LongestCommonSubsequenceOfTwoStringsTest {

  private static final Logger LOGGER = Logger.getLogger(LongestCommonSubsequenceOfTwoStringsTest.class.getName());

  private static final String DATA_SET = "/string/misc/longest-common-subsequence-of-two-strings.csv";

  private final LongestCommonSubsequenceOfTwoStrings longestCommonSubsequenceOfTwoStrings;

  LongestCommonSubsequenceOfTwoStringsTest(LongestCommonSubsequenceOfTwoStrings longestCommonSubsequenceOfTwoStrings) {
    this.longestCommonSubsequenceOfTwoStrings = longestCommonSubsequenceOfTwoStrings;
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  void testFind(String string1, String string2, String expectedLongestCommonSubsequence) {
    String result = longestCommonSubsequenceOfTwoStrings.find(string1, string2);
    assertEquals(expectedLongestCommonSubsequence.length(), result.length());
    assertTrue(isSubsequence(string1, result));
  }

  @Test
  void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    if (longestCommonSubsequenceOfTwoStrings instanceof NaiveLongestCommonSubsequenceOfTwoStrings) {
      LOGGER.info("Skipping stress test for NaiveLongestCommonSubsequenceOfTwoStrings to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      LongestCommonSubsequenceOfTwoStrings naiveLongestCommonSubsequenceOfTwoStrings = new NaiveLongestCommonSubsequenceOfTwoStrings();
      String string1 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 0, 20);
      String string2 = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 0, 20);
      String result1 = naiveLongestCommonSubsequenceOfTwoStrings.find(string1, string2);
      String result2 = longestCommonSubsequenceOfTwoStrings.find(string1, string2);
      assertNotNull(result1);
      assertNotNull(result2);
      assertTrue(isSubsequence(string1, result1));
      assertTrue(isSubsequence(string2, result1));
      assertTrue(isSubsequence(string1, result2));
      assertTrue(isSubsequence(string2, result2));
      if (result1.length() != result2.length()) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " string1: " + string1);
        LOGGER.info("Stress test " + test + " string2: " + string2);
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

  private boolean isSubsequence(String string, String subsequence) {
    assertTrue(subsequence.length() <= string.length());
    int stringIndex = 0;
    int subsequenceIndex = 0;
    while (stringIndex < string.length() && subsequenceIndex < subsequence.length()) {
      if (subsequence.charAt(subsequenceIndex) == string.charAt(stringIndex)) {
        subsequenceIndex++;
      }
      stringIndex++;
    }
    return subsequenceIndex == subsequence.length();
  }
}
