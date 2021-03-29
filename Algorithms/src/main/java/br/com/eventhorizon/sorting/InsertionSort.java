package br.com.eventhorizon.sorting;

public class InsertionSort implements SortAlgorithm {

  @Override
  public void sort(long[] a) {
    for (int i = 1; i < a.length; i++) {
      long value = a[i];
      int j;
      for (j = i - 1; j >= 0 && value < a[j]; j--) {
        a[j + 1] = a[j];
      }
      a[j + 1] = value;
    }
  }
}
