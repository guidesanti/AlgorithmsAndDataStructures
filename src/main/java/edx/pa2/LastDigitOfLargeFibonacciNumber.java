package edx.pa2;

import edx.common.FastScanner;

public class LastDigitOfLargeFibonacciNumber {

  public static void trivialSolution() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    int fibonacci = 0;

    if (n <= 1) {
      fibonacci = n;
    } else {
      int previous = 0;
      int current = 1;
      for (int i = 2; i <= n; i++) {
        fibonacci = current + previous;
        previous = current;
        current = fibonacci;
      }
    }
    int lastFibonacciDigit = fibonacci % 10;

    System.out.println(lastFibonacciDigit);
  }

  public static void solution1() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    long fibonacci = 0;

    if (n <= 1) {
      fibonacci = n;
    } else {
      long previous = 0;
      long current = 1;
      for (int i = 2; i <= n; i++) {
        fibonacci = current + previous;
        previous = current;
        current = fibonacci;
      }
    }
    long lastFibonacciDigit = fibonacci % 10;

    System.out.println(lastFibonacciDigit);
  }

  public static void solution2() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    int lastFibonacciDigit = 0;

    if (n <= 1) {
      lastFibonacciDigit = n;
    } else {
      int previousLastFibonacciDigit = 0;
      int currentLastFibonacciDigit = 1;
      for (int i = 2; i <= n; i++) {
        lastFibonacciDigit = (previousLastFibonacciDigit + currentLastFibonacciDigit) % 10;
        previousLastFibonacciDigit = currentLastFibonacciDigit;
        currentLastFibonacciDigit = lastFibonacciDigit;
      }
    }

    System.out.println(lastFibonacciDigit);
  }
}
