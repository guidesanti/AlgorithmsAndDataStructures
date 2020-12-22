package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MajorityElement implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    System.out.println(naiveMajorityElement(a));
  }

  private static int naiveMajorityElement(int[] a) {
    for (int i = 0; i < a.length; i++) {
      int currentElement = a[i];
      int count = 0;
      for (int j = 0; j < a.length; j++) {
        if (a[j] == currentElement) {
          count++;
        }
      }
      if (count > a.length / 2) {
        return 1;
      }
    }
    return 0;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    if (finalMajorityElement(a, 0, a.length - 1) != -1) {
      System.out.println(1);
    } else {
      System.out.println(0);
    }
  }

  private static int finalMajorityElement(int[] a, int left, int right) {
    if (left == right) {
      return a[left];
    }
    if (left + 1 == right) {
      if (a[left] == a[right]) {
        return a[left];
      } else {
        return -1;
      }
    }

    int middle = (left + right) / 2;
    int majorityLeft = finalMajorityElement(a, left, middle);
    int majorityRight = finalMajorityElement(a, middle + 1, right);
    return combine(a, left, right, majorityLeft, majorityRight);
  }

  private static int combine(int[] a, int left, int right, int majorityLeft, int majorityRight) {
    int majorityLeftCount = 0;
    int majorityRightCount = 0;
    for (int i = left; i <= right; i++) {
      if (a[i] == majorityLeft) {
        majorityLeftCount++;
      }
      if (a[i] == majorityRight) {
        majorityRightCount++;
      }
    }
    if (majorityLeftCount >= majorityRightCount) {
      return majorityLeftCount > ((right - left + 1) / 2) ? majorityLeft : -1;
    } else {
      return majorityRightCount > ((right - left + 1) / 2) ? majorityRight : -1;
    }
  }
}
