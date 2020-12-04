package edx.pa2;

import edx.common.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class GreatestCommonDivisorTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(SmallFibonacciNumberTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/greatest-common-divisor.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    GreatestCommonDivisor.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/greatest-common-divisor.csv", numLinesToSkip = 1)
  public void testTrivialSolution1(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    GreatestCommonDivisor.solution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    while (true) {
      // Generate input
      int a = getRandomNumber(1, Integer.MAX_VALUE);
      int b = getRandomNumber(1, Integer.MAX_VALUE);

      // Get input as string
      String input = a + " " + b;
      LOGGER.info("Input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeout(ofMillis(DEFAULT_TIME_LIMIT_IN_MS), GreatestCommonDivisor::solution1);
    }
  }

  @Test
  public void stressTest() {
    while (true) {
      // Generate input
      int a = getRandomNumber(1, Integer.MAX_VALUE);
      int b = getRandomNumber(1, Integer.MAX_VALUE);

      // Get input as string
      String input = a + " " + b;

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      GreatestCommonDivisor.trivialSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      GreatestCommonDivisor.solution1();
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
