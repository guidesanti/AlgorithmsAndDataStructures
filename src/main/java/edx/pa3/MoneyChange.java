package edx.pa3;

import edx.common.FastScanner;
import edx.common.PA;

public class MoneyChange implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(naiveMoneyChange(m));
  }

  @Override
  public void intermediateSolution1() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(greedyMoneyChange1(m));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(greedyMoneyChange2(m));
  }

  private static int naiveMoneyChange(int m) {
    int min = Integer.MAX_VALUE;
    for (int a = 0; a <= 1000; a++) {
      for (int b = 0; b <= 1000; b++) {
        for (int c = 0; c <= 1000; c++) {
          if ((a + (5 * b) + (10 * c)) == m) {
            int s = a + b + c;
            if (s < min) {
              min = s;
            }
          }
        }
      }
    }
    return min;
  }

  /*
   * Greedy algorithm
   * Candidates: the set of coins available { 10, 5, 1} with infinity coins of each value
   * Selection: choose the highest-value coin remaining in the set of candidates
   * Feasible: checks whether the chosen coin does not exceed the amount to change (m)
   * Objective: counts the number of coins used in solution
   * Solution: checks whether the coins already taken makes a solution for the problem
   */

  /*
   * Greedy algorithm by using subtraction
   */
  private static int greedyMoneyChange1(int m) {
    int[] c = { 10, 5, 1 };
    int s = 0;
    int i = 0;
    while (m > 0) {
      if (m >= c[i]) {
        s++;
        m -= c[i];
      }  else {
        i++;
      }
    }

    return s;
  }

  /*
   * Greedy algorithm by using division
   */
  private static int greedyMoneyChange2(int m) {
    int[] c = { 10, 5, 1 };
    int s = 0;
    for (int i = 0; i < c.length && m > 0; i++) {
      s += m / c[i];
      m = m % c[i];
    }

    return s;
  }
}
