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
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class ModOfLargeFibonacciNumberTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(ModOfLargeFibonacciNumberTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/mod-of-large-fibonacci-number.csv", numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ModOfLargeFibonacciNumber.trivialSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/mod-of-large-fibonacci-number.csv", numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ModOfLargeFibonacciNumber.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvSource({
    "239 1000,161",
    "2816213588 239,151"
  })
  public void testFinalSolutionWithBigDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    ModOfLargeFibonacciNumber.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    while (true) {
      // Generate input
      int a = getRandomInteger(1, 1000000000);
      int b = getRandomInteger(2, 100000);

      // Get input as string
      String input = a + " " + b;
      LOGGER.info("Input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      assertTimeout(ofMillis(TestProperties.getTimeLimit()), ModOfLargeFibonacciNumber::finalSolution);
    }
  }

  @Test
  public void stressTest() {
    while (true) {
      // Generate input
      int a = getRandomInteger(1, 60);
      int b = getRandomInteger(2, 100000);

      // Get input as string
      String input = a + " " + b;

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      ModOfLargeFibonacciNumber.trivialSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      ModOfLargeFibonacciNumber.finalSolution();
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
