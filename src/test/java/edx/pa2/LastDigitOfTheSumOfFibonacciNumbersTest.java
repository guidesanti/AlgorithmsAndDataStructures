package edx.pa2;

import edx.common.BaseTest;
import edx.common.TestProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class LastDigitOfTheSumOfFibonacciNumbersTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(LastDigitOfTheSumOfFibonacciNumbersTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-the-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfTheSumOfFibonacciNumbers.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-the-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testSolution1WithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfTheSumOfFibonacciNumbers.solution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-the-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testSolution2WithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfTheSumOfFibonacciNumbers.solution2();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/last-digit-of-the-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfTheSumOfFibonacciNumbers.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvSource({
    "100,5"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfTheSumOfFibonacciNumbers.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    LOGGER.info("Time limit test duration: " + TestProperties.getTimeLimitTestDuration());
    long startTime = System.currentTimeMillis();
    while (true) {
      // Generate input
      long a = getRandomLong(0, 100000000000000L);

      // Get input as string
      String input = "" + a;
      LOGGER.info("Input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), LastDigitOfTheSumOfFibonacciNumbers::finalSolution);

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getTimeLimitTestDuration()) {
        return;
      }
    }
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    while (true) {
      // Generate input
      long a = getRandomInteger(1, 50);

      // Get input as string
      String input = "" + a;

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      LastDigitOfTheSumOfFibonacciNumbers.trivialSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      LastDigitOfTheSumOfFibonacciNumbers.finalSolution();
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

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }
}
