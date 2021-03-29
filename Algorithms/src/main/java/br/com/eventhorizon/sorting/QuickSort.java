package br.com.eventhorizon.sorting;

public class QuickSort implements SortAlgorithm {

  @Override
  public void sort(long[] a) {
    quickSort(a, 0, a.length - 1);
  }

  private void quickSort(long[] a, int low, int high) {
    if (low >= high) {
      return;
    }
    int p = partition(a, low, high);
    quickSort(a, low, p - 1);
    quickSort(a, p + 1, high);
  }

  private int partition(long[] a, int low, int high) {
    long pivot = a[low];
    int pivotIndex = low;
    for (int i = pivotIndex + 1, j = high; i <= j;) {
      if (a[i] < pivot) {
        swap(a, i, pivotIndex);
        pivotIndex++;
        i++;
      } else {
        swap(a, i, j);
        j--;
      }
    }
    return pivotIndex;
  }

  private void swap(long [] a, int i, int j) {
    long temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}
