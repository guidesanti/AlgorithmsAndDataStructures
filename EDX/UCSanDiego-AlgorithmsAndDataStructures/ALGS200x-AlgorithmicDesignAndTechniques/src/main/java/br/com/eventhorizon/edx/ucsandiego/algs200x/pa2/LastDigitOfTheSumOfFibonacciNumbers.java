package br.com.eventhorizon.edx.ucsandiego.algs200x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class LastDigitOfTheSumOfFibonacciNumbers implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long n = scanner.nextLong();
    System.out.println(fibonacciSumNaive(n));
  }

  /**
   * Solution based on matrix multiplication to calculate the nth Fibonacci number, but storing
   * only the last digit of the multiplication to avoid overflow of long
   */
  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long n = scanner.nextLong();
    System.out.println(fibonacciSum3(n));
  }

  private static long fibonacciSumNaive(long n) {
    if (n <= 1) {
      return n;
    }

    long previous = 0;
    long current = 1;
    long sum = 1;

    for (long i = 0; i < n - 1; ++i) {
      long temp = previous;
      previous = current;
      current = temp + current;
      sum += current;
    }

    return sum % 10;
  }

  private static long fibonacciSum3(long n) {
    if (n <= 1) {
      return n;
    }
    long sumMod = fibonacciLastDigit(n + 2);
    sumMod = sumMod == 0 ? 9 : sumMod - 1;
    return sumMod;
  }

  private static long fibonacci(long n) {
    long fibonacci = 0;
    if (n <= 1) {
      fibonacci = n;
    } else {
      int[][] m = new int[][] {{1, 1}, {1, 0}};
      int[][] r = power(m, n - 1);
      fibonacci = r[0][0];
    }
    return fibonacci;
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

  private static int[][] power(int m[][], long n) {
    int[][] r = new int[m.length][m[0].length];
    r[0][0] = m[0][0];
    r[0][1] = m[0][1];
    r[1][0] = m[1][0];
    r[1][1] = m[1][1];

    if (n == 1) {
      return r;
    }
    r = power(m, n / 2);
    r = multiply(r, r);
    if (n % 2 != 0) {
      r = multiply(r, m);
    }

    return r;
  }

  private static int[][] multiply(int a[][], int b[][]) {
    int r[][] = new int[a.length][a[0].length];
    r[0][0] = a[0][0]*b[0][0] + a[0][1]*b[1][0];
    r[0][1] = a[0][0]*b[0][1] + a[0][1]*b[1][1];
    r[1][0] = a[1][0]*b[0][0] + a[1][1]*b[1][0];
    r[1][1] = a[1][0]*b[0][1] + a[1][1]*b[1][1];
    return r;
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
