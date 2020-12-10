package edx.pa3;

import edx.common.BaseTest;
import edx.common.TestProperties;
import edx.pa2.LastDigitOfThePartialSumOfFibonacciNumbersTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class MoneyChangeTest extends BaseTest {

  private static final Logger
    LOGGER = Logger.getLogger(LastDigitOfThePartialSumOfFibonacciNumbersTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa3/money-change.csv", numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    MoneyChange.naiveSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa3/money-change.csv", numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    MoneyChange.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvSource({
    "28,6"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    MoneyChange.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    LOGGER.info("Time limit test duration: " + TestProperties.getTimeLimitTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput();
      LOGGER.info("Time limit test " + i + " input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), MoneyChange::finalSolution);
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
      String input = generateInput();
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      MoneyChange.naiveSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      MoneyChange.finalSolution();
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

  private String generateInput() {
    int m = getRandomInteger(1, 1000);
    return "" + m;
  }
}
