package br.com.eventhorizon.sorting;

import br.com.eventhorizon.common.pa.FastScanner;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public abstract class SortTest {

  private static final Logger LOGGER = Logger.getLogger(SortTest.class.getName());

  protected final SortAlgorithm sortAlgorithm;

  public SortTest(SortAlgorithm sortAlgorithm) {
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
}
