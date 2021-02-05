package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Random;

public class ImprovingQuickSort implements PA {

  private static Random random = new Random();

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    randomizedQuickSort2(a, 0, n - 1);
    for (int i = 0; i < n; i++) {
      System.out.print(a[i] + " ");
    }
  }

  private static void randomizedQuickSort2(int[] a, int l, int r) {
    if (l >= r) {
      return;
    }
    int k = random.nextInt(r - l + 1) + l;
    int t = a[l];
    a[l] = a[k];
    a[k] = t;
    int m = partition2(a, l, r);
    randomizedQuickSort2(a, l, m - 1);
    randomizedQuickSort2(a, m + 1, r);
  }

  private static int partition2(int[] a, int l, int r) {
    int x = a[l];
    int j = l;
    for (int i = l + 1; i <= r; i++) {
      if (a[i] <= x) {
        j++;
        swap(a, i, j);
      }
    }
    swap(a, l, j);
    return j;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    randomizedQuickSort3(a, 0, n - 1);
    for (int i = 0; i < n; i++) {
      System.out.print(a[i] + " ");
    }
  }

  private static void randomizedQuickSort3(int[] a, int l, int r) {
    if (l >= r) {
      return;
    }
    int k = random.nextInt(r - l + 1) + l;
    int t = a[l];
    a[l] = a[k];
    a[k] = t;
    int[] m = partition3(a, l, r);
    randomizedQuickSort3(a, l, m[0] - 1);
    randomizedQuickSort3(a, m[1] + 1, r);
  }

  private static int[] partition3(int[] a, int l, int r) {
    long pivot = a[l];
    int pivotIndex = l;
    int i = pivotIndex + 1;
    int j = r;
    int count = 0;
    while (i <= j) {
      if (a[i] < pivot) {
        swap(a, i, pivotIndex);
        pivotIndex++;
        i++;
      } else if (a[i] == pivot) {
        i++;
        count++;
      } else {
        swap(a, i, j);
        j--;
      }
    }
    return new int[] {pivotIndex, pivotIndex + count};
  }

  private static void swap(int [] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}
