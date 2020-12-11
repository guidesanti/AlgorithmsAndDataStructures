package edx.pa2;

import edx.common.FastScanner;
import edx.common.PA;

public class LeastCommonMultiple implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcmNaive(a, b));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(lcm1(a, b));
  }

  private static long lcmNaive(int a, int b) {
    for (long l = 1; l <= (long) a * b; ++l) {
      if (l % a == 0 && l % b == 0) {
        return l;
      }
    }

    return (long) a * b;
  }

  private static long lcm1(int a, int b) {
    return ((long) a * b) / gcd(a, b);
  }

  private static int gcd(int a, int b) {
    if (b == 0) {
      return a;
    }
    return gcd(b, a % b);
  }
}
