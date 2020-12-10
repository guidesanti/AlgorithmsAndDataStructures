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

public class LastDigitOfThePartialSumOfFibonacciNumbersTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(LastDigitOfThePartialSumOfFibonacciNumbersTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/last-digit-of-the-partial-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfThePartialSumOfFibonacciNumbers.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa2/last-digit-of-the-partial-sum-of-fibonacci-numbers.csv", numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfThePartialSumOfFibonacciNumbers.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvSource({
    "10 200,2"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    LastDigitOfThePartialSumOfFibonacciNumbers.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    LOGGER.info("Time limit test duration: " + TestProperties.getTimeLimitTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      // Generate input
      long a = getRandomLong(0, 1000000000000000000L - 1);
      long b = getRandomLong(a + 1, 1000000000000000000L);

      // Get input as string
      String input = a + " " + b;
      LOGGER.info("Time limit test " + i + " input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), LastDigitOfThePartialSumOfFibonacciNumbers::finalSolution);
      LOGGER.info("Time limit test " + i + " status: PASSED");

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
    for (long i = 0; true; i++) {
      // Generate input
      long a = getRandomLong(0, 49L);
      long b = getRandomLong(a + 1, 50L);

      // Get input as string
      String input = a + " " + b;
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      LastDigitOfThePartialSumOfFibonacciNumbers.trivialSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      LastDigitOfThePartialSumOfFibonacciNumbers.finalSolution();
      String result2 = getActualOutput();
      if (result1.equals(result2)) {
        LOGGER.info("Stress test " + i + " status: PASSED");
      } else {
        LOGGER.info("Stress test " + i + " status: FAILED");
        LOGGER.info("Stress test " + i + " result 1:  " + result1);
        LOGGER.info("Stress test " + i + " result 2:  " + result2);
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
