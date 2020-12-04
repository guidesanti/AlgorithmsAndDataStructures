package edx.pa2;

import edx.common.FastScanner;

public class SmallFibonacciNumber {

  private static int fibonacci(int n) {
    if (n <= 1) {
      return n;
    }

    return fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static void trivialSolution() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();

    System.out.println(fibonacci(n));
  }

  public static void solution1() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    long[] fibonacci = new long[n + 1];
    fibonacci[0] = 0;

    if (n >= 1) {
      fibonacci[1] = 1;
      for (int i = 2; i <= n; i++) {
        fibonacci[i] = fibonacci[i-1] + fibonacci[i-2];
      }
    }

    System.out.println(fibonacci[n]);
  }

  public static void solution2() {
    FastScanner in = new FastScanner(System.in);
    long n = in.nextInt();
    long fibonacci = 0;

    if (n <= 1) {
      fibonacci = n;
    } else {
      long prev = 0;
      long curr = 1;
      for (long i = 2; i <= n; i++) {
        fibonacci = curr + prev;
        prev = curr;
        curr = fibonacci;
      }
    }

    System.out.println(fibonacci);
  }
}
