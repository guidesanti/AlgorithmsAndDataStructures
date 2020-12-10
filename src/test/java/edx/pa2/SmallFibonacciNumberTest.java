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

public class SmallFibonacciNumberTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(SmallFibonacciNumberTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/small-fibonacci-number.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    SmallFibonacciNumber.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/small-fibonacci-number.csv", numLinesToSkip = 1)
  public void testSolution1WithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    SmallFibonacciNumber.solution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/small-fibonacci-number.csv", numLinesToSkip = 1)
  public void testSolution2WithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    SmallFibonacciNumber.solution2();
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
      assertTimeout(ofMillis(TestProperties.getTimeLimit()), SmallFibonacciNumber::solution1);
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeout(ofMillis(TestProperties.getTimeLimit()), SmallFibonacciNumber::solution2);
    }
  }

  @Test
  public void stressTest() {
    while (true) {
      // Generate input
      int n = getRandomInteger(0, 50);

      // Get input as string
      String input = "" + n;
      LOGGER.info("Input: " + input);

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      SmallFibonacciNumber.solution1();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      SmallFibonacciNumber.solution2();
      String result2 = getActualOutput();
      if (!result1.equals(result2)) {
        LOGGER.info("FAILED: Result 1 = " + result1 + ", result 2 = " + result2);
        LOGGER.info("Input: ");
        return;
      }
    }
  }
}
