package edx.pa2;

import edx.common.FastScanner;
import edx.common.PA;

public class LastDigitOfThePartialSumOfFibonacciNumbers implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long from = scanner.nextLong();
    long to = scanner.nextLong();
    System.out.println(fibonacciSumLastDigitNaive(from, to));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long from = scanner.nextLong();
    long to = scanner.nextLong();
    System.out.println(fibonacciSumLastDigit(from, to));
  }

  private static long fibonacciSumLastDigitNaive(long from, long to) {
    long sum = 0;

    long current = 0;
    long next  = 1;

    for (long i = 0; i <= to; ++i) {
      if (i >= from) {
        sum += current;
      }

      long new_current = next;
      next = next + current;
      current = new_current;
    }

    return sum % 10;
  }

  private static long fibonacciSumLastDigit(long from, long to) {
    long sumMod1 = fibonacciLastDigit(from + 1);
    sumMod1 = sumMod1 == 0 ? 9 : sumMod1 - 1;
    long sumMod2 = fibonacciLastDigit(to + 2);
    sumMod2 = sumMod2 == 0 ? 9 : sumMod2 - 1;
    long result = sumMod2 >= sumMod1 ? sumMod2 - sumMod1 : (sumMod2 + 10) - sumMod1;
    return result;
  }

  private static long fibonacciLastDigit(long n) {
    long fibonacciLastDigit = 0;
    if (n <= 1) {
      fibonacciLastDigit = n;
    } else {
      int[][] m = new int[][] {{1, 1}, {1, 0}};
      int[][] r = powerLastDigit(m, n - 1);
      fibonacciLastDigit = r[0][0];
    }
    return fibonacciLastDigit;
  }

  private static int[][] powerLastDigit(int m[][], long n) {
    int[][] r = new int[m.length][m[0].length];
    r[0][0] = m[0][0];
    r[0][1] = m[0][1];
    r[1][0] = m[1][0];
    r[1][1] = m[1][1];

    if (n == 1) {
      return r;
    }
    r = powerLastDigit(m, n / 2);
    r = multiplyLastDigit(r, r);
    if (n % 2 != 0) {
      r = multiplyLastDigit(r, m);
    }

    return r;
  }

  private static int[][] multiplyLastDigit(int a[][], int b[][]) {
    int r[][] = new int[a.length][a[0].length];
    r[0][0] = (a[0][0]*b[0][0] + a[0][1]*b[1][0]) % 10;
    r[0][1] = (a[0][0]*b[0][1] + a[0][1]*b[1][1]) % 10;
    r[1][0] = (a[1][0]*b[0][0] + a[1][1]*b[1][0]) % 10;
    r[1][1] = (a[1][0]*b[0][1] + a[1][1]*b[1][1]) % 10;
    return r;
  }
}
