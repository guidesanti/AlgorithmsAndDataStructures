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

public class LastDigitOfLargeFibonacciNumberTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(LastDigitOfLargeFibonacciNumberTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-large-fibonacci-number.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfLargeFibonacciNumber.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-large-fibonacci-number.csv", numLinesToSkip = 1)
  public void testTrivialSolution1(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfLargeFibonacciNumber.solution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-large-fibonacci-number.csv", numLinesToSkip = 1)
  public void testTrivialSolution2(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfLargeFibonacciNumber.solution2();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    for (int n = 0; n < 50; n++) {
      // Get input as string
      String input = "" + n;
      LOGGER.info("Input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeout(ofMillis(DEFAULT_TIME_LIMIT_IN_MS), SmallFibonacciNumber::solution2);
    }
  }

  @Test
  public void stressTest() {
    while (true) {
      // Generate input
      int n = getRandomNumber(0, 90);

      // Get input as string
      String input = "" + n;

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      LastDigitOfLargeFibonacciNumber.solution1();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      LastDigitOfLargeFibonacciNumber.solution2();
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
