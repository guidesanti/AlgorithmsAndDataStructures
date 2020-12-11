package edx.pa3;

import edx.common.FastScanner;
import edx.common.PA;

public class MaximumValueOfLoot implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int capacity = scanner.nextInt();
    int[] values = new int[n];
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
      weights[i] = scanner.nextInt();
    }
    System.out.println(naiveMaximumValueOfLoot(capacity, values, weights));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int capacity = scanner.nextInt();
    int[] values = new int[n];
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
      weights[i] = scanner.nextInt();
    }
    System.out.println(naiveMaximumValueOfLoot(capacity, values, weights));
  }

  private double naiveMaximumValueOfLoot(int capacity, int[] values, int[] weights) {
    return 0;
  }

  private double greedyMaximumValueOfLoot(int capacity, int[] values, int[] weights) {
    return 0;
  }
}
