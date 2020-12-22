package br.com.eventhorizon.sorting;

public class SelectionSort implements SortAlgorithm {

  @Override
  public void sort(long[] a) {
    for (int i = 0; i < a.length; i++) {
      int minIndex = findMin(a, i);
      long temp = a[i];
      a[i] = a[minIndex];
      a[minIndex] = temp;
    }
  }

  private int findMin(long[] a, int startIndex) {
    long min = a[startIndex];
    int minIndex = startIndex;
    for (int i = startIndex + 1; i < a.length; i++) {
      if (a[i] < min) {
        min = a[i];
        minIndex = i;
      }
    }
    return minIndex;
  }
}
