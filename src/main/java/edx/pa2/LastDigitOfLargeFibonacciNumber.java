package edx.pa2;

import edx.common.FastScanner;
import edx.common.PA;

public class LastDigitOfLargeFibonacciNumber implements PA {

  @Override
  public void naiveSolution() {
    FastScanner in = new FastScanner(System.in);
    long n = in.nextInt();
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

  @Override
  public void intermediateSolution1() {
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

  @Override
  public void finalSolution() {
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
