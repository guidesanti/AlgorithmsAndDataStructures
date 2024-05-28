package br.com.eventhorizon.string.suffixarray;

import br.com.eventhorizon.common.pa.test.Defaults;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.utils.converters.StringToIntegerArrayConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public abstract class SuffixArrayBuilderTest {

  private static final Logger LOGGER = Logger.getLogger(SuffixArrayBuilderTest.class.getName());

  private static final String DATA_SET = "/string/suffixarray/suffix-array-builder.csv";

  private final SuffixArrayBuilder suffixArrayBuilder;

  public SuffixArrayBuilderTest(SuffixArrayBuilder suffixArrayBuilder) {
    this.suffixArrayBuilder = suffixArrayBuilder;
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  void test(String text, @ConvertWith(StringToIntegerArrayConverter.class) int[] expectedSuffixArray) {
    assertNotNull(text);
    assertNotNull(expectedSuffixArray);
    assertEquals(text.length(), expectedSuffixArray.length);
    int[] actualSuffixArray = suffixArrayBuilder.buildSuffixArray(text);
    assertArrayEquals(expectedSuffixArray, actualSuffixArray);
  }

  @Test
  void stressTest() {
    LOGGER.info("Stress test duration: " + Defaults.STRESS_TEST_DURATION);
    if (suffixArrayBuilder instanceof NaiveSuffixArrayBuilder) {
      LOGGER.info("Skipping stress test for NaiveSuffixArrayBuilder to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      SuffixArrayBuilder naiveSuffixArrayBuilder = new NaiveSuffixArrayBuilder();
      String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10000);
      int[] result1 = naiveSuffixArrayBuilder.buildSuffixArray(text);
      int[] result2 = suffixArrayBuilder.buildSuffixArray(text);
      if (!Arrays.equals(result1, result2)) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text: " + text);
        LOGGER.info("Stress test " + test + " result1: " + Arrays.toString(result1));
        LOGGER.info("Stress test " + test + " result2: " + Arrays.toString(result2));
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

  @Test
  void timeTestWithRandomText() {
    if (suffixArrayBuilder instanceof NaiveSuffixArrayBuilder) {
      LOGGER.info("Skipping time test for NaiveSuffixArrayBuilder to avoid compare it to itself");
      return;
    }
    double naiveTime = 0;
    double time = 0;
    for (int test = 0; test < 1000; test++) {
      SuffixArrayBuilder naiveSuffixArrayBuilder = new NaiveSuffixArrayBuilder();

      String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 10000);

      long startTime = System.currentTimeMillis();
      naiveSuffixArrayBuilder.buildSuffixArray(text);
      long elapsedTime = System.currentTimeMillis() - startTime;
      naiveTime += elapsedTime;

      startTime = System.currentTimeMillis();
      suffixArrayBuilder.buildSuffixArray(text);
      elapsedTime = System.currentTimeMillis() - startTime;
      time += elapsedTime;
    }

    LOGGER.info("Time test total time (Naive): " + naiveTime);
    LOGGER.info("Time test total time (" + suffixArrayBuilder.getClass().getName() + "): " + time);
    LOGGER.info("Time test relative time: " + naiveTime / time);
  }

  @Test
  void timeTestNonRandomText() {
    if (suffixArrayBuilder instanceof NaiveSuffixArrayBuilder) {
      LOGGER.info("Skipping time test for NaiveSuffixArrayBuilder to avoid compare it to itself");
      return;
    }
    double naiveTime = 0;
    double time = 0;
    for (int test = 0; test < 1000; test++) {
      SuffixArrayBuilder naiveSuffixArrayBuilder = new NaiveSuffixArrayBuilder();

      StringBuilder text = new StringBuilder();
      for (int i = 0; i < 10000; i++) {
        text.append('A');
      }

      long startTime = System.currentTimeMillis();
      naiveSuffixArrayBuilder.buildSuffixArray(text.toString());
      long elapsedTime = System.currentTimeMillis() - startTime;
      naiveTime += elapsedTime;

      startTime = System.currentTimeMillis();
      suffixArrayBuilder.buildSuffixArray(text.toString());
      elapsedTime = System.currentTimeMillis() - startTime;
      time += elapsedTime;
    }

    LOGGER.info("Time test total time (Naive): " + naiveTime);
    LOGGER.info("Time test total time (" + suffixArrayBuilder.getClass().getName() + "): " + time);
    LOGGER.info("Time test relative time: " + naiveTime / time);
  }
}
