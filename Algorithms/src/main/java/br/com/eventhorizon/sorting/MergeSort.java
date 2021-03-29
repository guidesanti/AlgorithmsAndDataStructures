package br.com.eventhorizon.sorting;

public class MergeSort implements SortAlgorithm {

  @Override
  public void sort(long[] a) {
    mergeSort(a, 0, a.length - 1);
  }

  private void mergeSort(long[] a, int low, int high) {
    if (high == low) {
      return;
    }
    int middle = (low + high) / 2;
    mergeSort(a, low, middle);
    mergeSort(a, middle + 1, high);
    merge(a, low, middle, high);
  }

  private void merge(long[] a, int low, int middle, int high) {
    long[] lowArray = new long[middle - low + 2];
    for (int i = 0; i < lowArray.length - 1; i++) {
      lowArray[i] = a[low + i];
    }
    lowArray[lowArray.length - 1] = Long.MAX_VALUE;
    long[] highArray = new long[high - middle + 1];
    for (int i = 0; i < highArray.length - 1; i++) {
      highArray[i] = a[middle + i + 1];
    }
    highArray[highArray.length - 1] = Long.MAX_VALUE;
    int lowIndex = 0;
    int highIndex = 0;
    for (int i = low; i <= high; i++) {
      if (lowArray[lowIndex] <= highArray[highIndex]) {
        a[i] = lowArray[lowIndex++];
      } else {
        a[i] = highArray[highIndex++];
      }
    }
  }
}
