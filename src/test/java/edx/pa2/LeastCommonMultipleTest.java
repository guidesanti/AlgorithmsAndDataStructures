package edx.pa2;

import edx.common.BaseTest;
import edx.common.TestProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class LeastCommonMultipleTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(LeastCommonMultipleTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/least-common-multiple.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LeastCommonMultiple.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/least-common-multiple.csv", numLinesToSkip = 1)
  public void testSolution1(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LeastCommonMultiple.solution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    while (true) {
      // Generate input
      int a = getRandomInteger(1, 2000000000);
      int b = getRandomInteger(1, 2000000000);

      // Get input as string
      String input = a + " " + b;
      LOGGER.info("Input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeout(ofMillis(TestProperties.getTimeLimit()), LeastCommonMultiple::solution1);
    }
  }

  @Test
  public void stressTest() {
    while (true) {
      // Generate input
      int a = getRandomInteger(1, 10000);
      int b = getRandomInteger(1, 10000);

      // Get input as string
      String input = a + " " + b;

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      LeastCommonMultiple.trivialSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      LeastCommonMultiple.solution1();
      String result2 = getActualOutput();
      if (result1.equals(result2)) {
        LOGGER.info("OK");
      } else {
        LOGGER.info("FAILED");
        LOGGER.info("Input: " + input);
        LOGGER.info("Result 1:  " + result1);
        LOGGER.info("Result 2:  " + result2);
        throw new RuntimeException("Stress test failed");
      }
    }
  }
}
