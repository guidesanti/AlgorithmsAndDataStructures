package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class BinarySearch implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    int m = scanner.nextInt();
    int[] b = new int[m];
    for (int i = 0; i < m; i++) {
      b[i] = scanner.nextInt();
    }
    for (int i = 0; i < m; i++) {
      System.out.print(linearSearch(a, b[i]) + " ");
    }
  }

  private static int linearSearch(int[] a, int x) {
    for (int i = 0; i < a.length; i++) {
      if (a[i] == x) return i;
    }
    return -1;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    int m = scanner.nextInt();
    int[] b = new int[m];
    for (int i = 0; i < m; i++) {
      b[i] = scanner.nextInt();
    }
    for (int i = 0; i < m; i++) {
      System.out.print(binarySearch(a, 0, a.length - 1, b[i]) + " ");
    }
  }

  private static int binarySearch(int[] a, int left, int right, int x) {
    int middle = (left + right) / 2;
    if (a[middle] == x) {
      return middle;
    }
    if (right - left == 0) {
      return -1;
    }
    if (right - left == 1) {
      if (a[middle + 1] == x) {
        return middle + 1;
      }
      return -1;
    }
    if (a[middle] > x) {
      right = middle;
    } else {
      left = middle;
    }
    return binarySearch(a, left, right, x);
  }
}
