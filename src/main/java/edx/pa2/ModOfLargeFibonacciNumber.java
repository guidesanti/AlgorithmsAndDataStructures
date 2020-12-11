package edx.pa2;

import edx.common.FastScanner;
import edx.common.PA;

import java.util.ArrayList;
import java.util.List;

public class ModOfLargeFibonacciNumber implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long n = scanner.nextLong();
    long m = scanner.nextLong();
    System.out.println(getFibonacciModNaive(n, m));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    long n = scanner.nextLong();
    long m = scanner.nextLong();
    System.out.println(getFibonacciModClever(n, m));
  }

  private static long getFibonacciModNaive(long n, long m) {
    if (n <= 1) {
      return n;
    }

    long previous = 0;
    long current  = 1;

    for (long i = 0; i < n - 1; ++i) {
      long temp = previous;
      previous = current;
      current = temp + current;
    }

    return current % m;
  }

  private static long getFibonacciModClever(long n, long m) {
    if (n <= 1) {
      return n;
    }

    long period = 0;
    long previous = 0;
    long current = 1;
    List<Long> mod = new ArrayList<>();
    mod.add(0L);
    mod.add(1L);
    for (int i = 2; i <= n; i++) {
      long temp = previous;
      previous = current;
      current = (temp + current) % m;
      mod.add(current);
      if (previous == 0 && current == 1) {
        period = i - 1;
        break;
      }
    }

    return mod.get((int)(period > 0 ? n % period : n));
  }
}
