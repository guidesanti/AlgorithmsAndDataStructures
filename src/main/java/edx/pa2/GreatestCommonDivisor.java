package edx.pa2;

import edx.common.FastScanner;

public class GreatestCommonDivisor {

  public static void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    int gcd = 1;
    for(int d = 2; d <= a && d <= b; ++d) {
      if (a % d == 0 && b % d == 0) {
        if (d > gcd) {
          gcd = d;
        }
      }
    }

    System.out.println(gcd);
  }

  public static void solution1() {
    FastScanner scanner = new FastScanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();

    System.out.println(gcd(a, b));
  }

  private static int gcd(int a, int b) {
    if (b == 0) {
      return a;
    }
    return gcd(b, a % b);
  }
}
