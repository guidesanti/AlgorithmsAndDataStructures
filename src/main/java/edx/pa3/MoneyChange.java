package edx.pa3;

import edx.common.FastScanner;

public class MoneyChange {

  public static void main(String[] args) {
    finalSolution();
  }

  public static void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(moneyChangeTrivial(m));
  }

  public static void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(moneyChangeGreedy(m, 10));
  }

  private static int moneyChangeTrivial(int m) {
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

  private static int moneyChangeGreedy(int m, int c) {
    int s = m / c;
    int r = m % c;
    if (r == 0) {
      return s;
    }
    if (c == 10) {
      c = 5;
    } else {
      c = 1;
    }
    s += moneyChangeGreedy(r, c);
    return s;
  }
}
