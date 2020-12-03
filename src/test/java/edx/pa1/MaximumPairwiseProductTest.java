package edx.pa1;

import edx.common.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class MaximumPairwiseProductTest extends BaseTest {

  private static final Logger LOGGER = Logger.getLogger(MaximumPairwiseProductTest.class.getName());

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/maximum-pairwise-product.csv", numLinesToSkip = 1)
  public void testMainTrivialWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    MaximumPairwiseProduct.mainTrivial(null);
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/maximum-pairwise-product.csv", numLinesToSkip = 1)
  public void testMainNonTrivialWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    MaximumPairwiseProduct.mainNonTrivial(null);
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void testMainNonTrivialWithBigDataSet() {
    StringBuilder input = new StringBuilder("200000 200000 200000 ");
    for (long i = 2; i < 200000; i++) {
      input.append("1 ");
    }
    System.setIn(new ByteArrayInputStream(input.toString().trim().getBytes()));
    assertTimeout(ofMillis(BaseTest.DEFAULT_TIME_LIMIT_IN_MS), () -> MaximumPairwiseProduct.mainNonTrivial(null));
    Assertions.assertEquals("40000000000", getActualOutput());
  }

  @Test
  public void stressTest() {
    for (int i = 0; i < 100; i++) {
      // Generate input
      int n = getRandomNumber(2, 10000);
      int[] numbers = new int[n];
      for (int j = 0; j < n; j++) {
        numbers[j] = getRandomNumber(0, 200000);
      }

      // Get input as string
      StringBuilder inputBuilder = new StringBuilder();
      inputBuilder.append(n);
      for (int j = 0; j < n; j++) {
        inputBuilder.append(" " + numbers[j]);
      }
      String input = inputBuilder.toString().trim();
      LOGGER.info("Input: " + input);

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      MaximumPairwiseProduct.mainTrivial(null);
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      MaximumPairwiseProduct.mainNonTrivial(null);
      String result2 = getActualOutput();
      assertEquals(result1, result2, "Wrong answer: " + result1 + ", " + result2);
    }
  }
}
