package br.com.eventhorizon.edx.ucsandiego.algs200x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class GreatestCommonDivisor implements PA {

  @Override
  public void naiveSolution() {
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

  @Override
  public void finalSolution() {
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
