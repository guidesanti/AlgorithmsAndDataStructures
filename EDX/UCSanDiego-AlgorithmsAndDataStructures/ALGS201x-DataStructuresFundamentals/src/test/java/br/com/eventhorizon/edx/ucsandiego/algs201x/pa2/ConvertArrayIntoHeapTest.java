package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.FastScanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConvertArrayIntoHeapTest extends PATestBase {

  private static final Logger LOGGER = Logger.getLogger(ConvertArrayIntoHeapTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/convert-array-into-heap.csv";

  public ConvertArrayIntoHeapTest() {
    super(new ConvertArrayIntoHeap(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .build());
  }

  // This test is commented because the naive solution always creates a lot of swaps
  // and will always fail
//  @ParameterizedTest
//  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
//  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
//    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
//  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Test
  @Override
  public void stressTest() {
    LOGGER.info("Stress test duration: " + getSettings().getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.STRESS_TEST, null);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Naive solution
      reset(input);
      pa.trivialSolution();
      String result1 = getActualOutput();

      FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));
      int n = scanner.nextInt();
      int[] values1 = new int[n];
      for (int k = 0; k < values1.length; k++) {
        values1[k] = scanner.nextInt();
      }

      scanner = new FastScanner(new ByteArrayInputStream(result1.getBytes()));
      int numberOfSwaps1 = scanner.nextInt();
      int[][] swaps1 = new int[numberOfSwaps1][2];
      for (int k = 0; k < swaps1.length; k++) {
        swaps1[k][0] = scanner.nextInt();
        swaps1[k][1] = scanner.nextInt();
      }
      for (int k = 0; k < swaps1.length; k++) {
        int temp = values1[swaps1[k][0]];
        values1[swaps1[k][0]] = values1[swaps1[k][1]];
        values1[swaps1[k][1]] = temp;
      }
      assertTrue(isMinHeap(values1), "Solution 1 output is not a heap");

      // Final solution
      reset(input);
      pa.finalSolution();
      String result2 = getActualOutput();

      scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));
      n = scanner.nextInt();
      int[] values2 = new int[n];
      for (int k = 0; k < values2.length; k++) {
        values2[k] = scanner.nextInt();
      }

      scanner = new FastScanner(new ByteArrayInputStream(result2.getBytes()));
      int numberOfSwaps2 = scanner.nextInt();
      int[][] swaps2 = new int[numberOfSwaps2][2];
      for (int k = 0; k < swaps2.length; k++) {
        swaps2[k][0] = scanner.nextInt();
        swaps2[k][1] = scanner.nextInt();
      }
      for (int k = 0; k < swaps2.length; k++) {
        int temp = values2[swaps2[k][0]];
        values2[swaps2[k][0]] = values2[swaps2[k][1]];
        values2[swaps2[k][1]] = temp;
      }
      assertTrue(isMinHeap(values2), "Solution 2 output is not a min heap");
      assertTrue(swaps2.length <= 4 * n, "Solution 2 number of swaps exceeded the limit of " + 4 * n);

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > getSettings().getStressTestDuration()) {
        return;
      }
    }
  }

  private boolean isMinHeap(int[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      int parent = (i - 1) / 2;
      if (a[i] < a[parent]) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    int n;
    if (type == PATestType.TIME_LIMIT_TEST) {
      n = getRandomInteger(1, 100000);
    } else {
      n = getRandomInteger(1, 100);
    }
    input.append(n);
    for (int i = 0; i < n; i++) {
      input.append(" ").append(getRandomInteger(0, 1000000000));
    }
    return input.toString();
  }
}
