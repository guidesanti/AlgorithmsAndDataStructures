package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.pa.test.Defaults;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.utils.converters.StringToListOfIntegersConverter;
import br.com.eventhorizon.common.utils.converters.StringToListOfStringConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

abstract class PatternMatcherTest {

  private static final Logger LOGGER = Logger.getLogger(PatternMatcherTest.class.getName());

  private static final String PATTERN_MATCHING_DATA_SET = "/string/matching/pattern-matching.csv";

  private static final String MULTI_PATTERN_MATCHING_DATA_SET = "/string/matching/multi-pattern-matching.csv";

  private final PatternMatcher naivePatternMatcher;

  private final PatternMatcher patternMatcher;

  PatternMatcherTest(PatternMatcher patternMatcher) {
    this.naivePatternMatcher = new NaivePatternMatcher();
    this.patternMatcher = patternMatcher;
  }

  @ParameterizedTest
  @CsvFileSource(resources = PATTERN_MATCHING_DATA_SET, numLinesToSkip = 1)
  void testPatternMatch(String text, String pattern, @ConvertWith(StringToListOfIntegersConverter.class) List<Integer> expectedShifts) {
    Collection<Integer> actualShifts = patternMatcher.match(text, pattern);
    assertEquals(expectedShifts.size(), actualShifts.size());
    assertTrue(expectedShifts.containsAll(actualShifts));
  }

  @ParameterizedTest
  @CsvFileSource(resources = MULTI_PATTERN_MATCHING_DATA_SET, numLinesToSkip = 1)
  void testMultiPatternMatch(String text, @ConvertWith(StringToListOfStringConverter.class) List<String> patterns, @ConvertWith(StringToListOfIntegersConverter.class) List<Integer> expectedShifts) {
    Collection<Integer> actualShifts = patternMatcher.match(text, patterns);
    assertEquals(expectedShifts.size(), actualShifts.size());
    assertTrue(expectedShifts.containsAll(actualShifts));
  }

  @Test
  void patternMatchingStressTest() {
    LOGGER.info("Stress test duration: " + Defaults.STRESS_TEST_DURATION);
    if (patternMatcher instanceof NaivePatternMatcher) {
      LOGGER.info("Skipping pattern match stress test for NaivePatterMather to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      String pattern = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
      String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, pattern.length(), 10000);
      text = makeShifts(text, pattern);
      Collection<Integer> expectedShifts = naivePatternMatcher.match(text, pattern);
      Collection<Integer> actualShifts = patternMatcher.match(text, pattern);
      if (expectedShifts.size() != actualShifts.size() || !expectedShifts.containsAll(actualShifts)) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text: " + text);
        LOGGER.info("Stress test " + test + " pattern: " + pattern);
        LOGGER.info("Stress test " + test + " expected shifts: " + expectedShifts);
        LOGGER.info("Stress test " + test + " actual shifts: " + actualShifts);
        fail();
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > Defaults.STRESS_TEST_DURATION) {
        return;
      }
    }
  }

  private String makeShifts(String text, String pattern) {
    if (text.length() == 0 || pattern.length() == 0) {
      return "";
    }
    char[] textArray = text.toCharArray();
    char[] patternArray = pattern.toCharArray();
    int index = Utils.getRandomInteger(0, textArray.length);
    while (index + patternArray.length <= textArray.length) {
      System.arraycopy(patternArray, 0, textArray, index, patternArray.length);
      index = Utils.getRandomInteger(index + patternArray.length + 1, textArray.length + 1);
    }
    return String.valueOf(textArray);
  }
}
