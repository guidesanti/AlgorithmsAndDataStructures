package edx.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MaximumAdvertisementRevenue implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    long[] a = new long[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextLong();
    }
    long[] b = new long[n];
    for (int i = 0; i < n; i++) {
      b[i] = scanner.nextLong();
    }
    System.out.println(naiveMaximumAdvertisementRevenue(a, b));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    long[] a = new long[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextLong();
    }
    long[] b = new long[n];
    for (int i = 0; i < n; i++) {
      b[i] = scanner.nextLong();
    }
    System.out.println(greedyMaximumAdvertisementRevenue(a, b));
  }

  private long naiveMaximumAdvertisementRevenue(long[] a, long[] b) {
    long maximumAdvertisementRevenue = 0;
    for (int i = 0; i < a.length; i++) {
      maximumAdvertisementRevenue += findAndTakeMaxProfit(a, b);
    }
    return maximumAdvertisementRevenue;
  }

  /*
   * Greedy algorithm
   * Candidates: the set of items: profit per click * average number of clicks
   * Selection: choose the highest-value item remaining in the set of candidates
   * Feasible: checks whether the chosen item matches the solution (it will always in this case)
   * Objective: counts the total value in solution
   * Solution: checks whether the items already taken makes a solution for the problem
   */
  private long greedyMaximumAdvertisementRevenue(long[] a, long[] b) {
    long maximumAdvertisementRevenue = 0;
    for (int i = 0; i < a.length; i++) {
      maximumAdvertisementRevenue += findAndTakeMaxProfit(a, b);
    }
    return maximumAdvertisementRevenue;
  }

  private long findAndTakeMaxProfit(long[] a, long[] b) {
    long maxProfit = Long.MIN_VALUE;
    int maxProfitIndexA = 0;
    int maxProfitIndexB = 0;
    for (int i = 0; i < a.length; i++) {
      for (int j = 0; j < a.length; j++) {
        if (a[i] == Integer.MIN_VALUE || b[j] == Integer.MIN_VALUE) {
          continue;
        }
        long currentProfit = a[i] * b[j];
        if (currentProfit >= maxProfit) {
          maxProfit = currentProfit;
          maxProfitIndexA = i;
          maxProfitIndexB = j;
        }
      }
    }
    a[maxProfitIndexA] = Integer.MIN_VALUE;
    b[maxProfitIndexB] = Integer.MIN_VALUE;
    return maxProfit;
  }
}
