package br.com.eventhorizon.sorting;

public class HeapSort implements SortAlgorithm {

  int heapSize = 0;

  @Override
  public void sort(long[] a) {
    if (a.length <= 1) {
      return;
    }
    heapSize = a.length;
    buildHeap(a);
    while (heapSize > 0) {
      heapSize--;
      swap(a, 0, heapSize);
      siftDown(a, 0);
    }
  }

  private void buildHeap(long[] a) {
    for (int i = a.length - 1; i >= 0; i--) {
      siftDown(a, i);
    }
  }

  private void siftDown(long[] a, int i) {
    int leftChild = leftChild(i);
    int rightChild = rightChild(i);
    if (leftChild >= heapSize) {
      return;
    }
    int maxChild = leftChild;
    if (rightChild < heapSize) {
      if (a[leftChild] < a[rightChild]) {
        maxChild = rightChild;
      }
    }
    if (a[i] < a[maxChild]) {
      swap(a, i, maxChild);
      siftDown(a, maxChild);
    }
  }

  private int leftChild(int i) {
    return (2 * i) + 1;
  }

  private int rightChild(int i) {
    return (2 * i) + 2;
  }

  private void swap(long[] a, int i, int j) {
    long temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
}
