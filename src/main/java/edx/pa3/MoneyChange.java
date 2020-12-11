package edx.pa3;

import edx.common.FastScanner;

public class MoneyChange {

  public static void main(String[] args) {
    finalSolution();
  }

  public static void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(naiveMoneyChange(m));
  }

  public static void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(greedyMoneyChange(m));
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

  private static int greedyMoneyChange(int m) {
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
}
