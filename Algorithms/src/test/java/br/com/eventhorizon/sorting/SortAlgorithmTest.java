package br.com.eventhorizon.sorting;

import br.com.eventhorizon.common.pa.test.Defaults;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.FastScanner;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SortAlgorithmTest {

  private static final Logger LOGGER = Logger.getLogger(SortAlgorithmTest.class.getName());

  protected final SortAlgorithm sortAlgorithm;

  public SortAlgorithmTest(SortAlgorithm sortAlgorithm) {
    this.sortAlgorithm = sortAlgorithm;
  }

  protected void testSort(String input, String expectedOutput) {
    FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));
    int n = scanner.nextInt();
    long[] inputArray = new long[n];
    for (int i = 0; i < n; i++) {
      inputArray[i] = scanner.nextLong();
    }
    scanner = new FastScanner(new ByteArrayInputStream(expectedOutput.getBytes()));
    long[] expectedOutputArray = new long[n];
    for (int i = 0; i < n; i++) {
      expectedOutputArray[i] = scanner.nextLong();
    }
    LOGGER.info("Testing algorithm: " + sortAlgorithm.getClass());
    sortAlgorithm.sort(inputArray);
    assertArrayEquals(expectedOutputArray, inputArray);
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + Defaults.STRESS_TEST_DURATION);
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      long[] a = generateInput();
      sortAlgorithm.sort(a);
      for (int i = 1; i < a.length; i++) {
        assertTrue(a[i] >= a[i - 1], "Stress test " + i + " failed, a[" + i + "] is not greater than a[" + (i - 1) + "]");
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > Defaults.STRESS_TEST_DURATION) {
        return;
      }
    }
  }

  private long[] generateInput() {
    int n = Utils.getRandomInteger(1, 10000);
    long[] a = new long[n];
    for (int i = 0; i < n; i++) {
      a[i] = Utils.getRandomInteger(1, Integer.MAX_VALUE) - (Integer.MAX_VALUE / 2);
    }
    return a;
  }
}
