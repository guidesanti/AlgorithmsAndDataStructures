package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.Map;

public class PartitioningSouvenirs implements PA {

  private static Map<String, Boolean> cache;

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] values = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
    }
    System.out.println(naivePartition3(values) ? 1 : 0);
  }

  private static boolean naivePartition3(int[] values) {
    int capacity = 0;
    for (int i = 0; i < values.length; i++) {
      capacity += values[i];
    }
    if (capacity % 3 != 0) {
      return false;
    }

    return findSubSet(values, values.length - 1, 0, 0, 0);
  }

  private static boolean findSubSet(int[] values, int n, int sum1, int sum2, int sum3) {
    if (n < 0) {
      return sum1 == sum2 && sum1 == sum3;
    }
    return findSubSet(values, n - 1, sum1 + values[n], sum2, sum3) ||
        findSubSet(values, n - 1, sum1, sum2 + values[n], sum3) ||
        findSubSet(values, n - 1, sum1, sum2, sum3 + values[n]);
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] values = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
    }
    System.out.println(finalPartition3(values) ? 1 : 0);
  }

  private static boolean finalPartition3(int[] values) {
    int capacity = 0;
    for (int i = 0; i < values.length; i++) {
      capacity += values[i];
    }
    if (capacity % 3 != 0) {
      return false;
    }

    return memoizedFindSubSet(values, values.length - 1, 0, 0, 0, new HashMap<>());
  }

  private static boolean memoizedFindSubSet(int[] values, int n, int sum1, int sum2, int sum3, Map<String, Boolean> cache) {
    if (n < 0) {
      return sum1 == sum2 && sum1 == sum3;
    }

    String key = n + ":" + sum1 + ":" + sum2 + ":" + sum3;
    if (!cache.containsKey(key)) {
      cache.put(
          key,
          memoizedFindSubSet(values, n - 1, sum1 + values[n], sum2, sum3, cache) ||
          memoizedFindSubSet(values, n - 1, sum1, sum2 + values[n], sum3, cache) ||
          memoizedFindSubSet(values, n - 1, sum1, sum2, sum3 + values[n], cache));
    }

    return cache.get(key);
  }
}
