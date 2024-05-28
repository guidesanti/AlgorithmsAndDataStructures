package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class ConvertArrayIntoHeap implements PA {

  private static int[] data;

  private static List<Swap> swaps;

  private static int heapSize;

  @Override
  public void trivialSolution() {
    readInput();
    generateSwaps();
    writeOutput();
  }

  private static void generateSwaps() {
    swaps = new ArrayList<>();
    for (int i = 0; i < data.length; ++i) {
      for (int j = i + 1; j < data.length; ++j) {
        if (data[i] > data[j]) {
          swaps.add(new Swap(i, j));
          int tmp = data[i];
          data[i] = data[j];
          data[j] = tmp;
        }
      }
    }
  }

  @Override
  public void finalSolution() {
    swaps = new ArrayList<>();
    readInput();
    buildMinHeap();
    writeOutput();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    data = new int[n];
    for (int i = 0; i < n; ++i) {
      data[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.println(swaps.size());
    for (Swap swap : swaps) {
      System.out.println(swap.index1 + " " + swap.index2);
    }
  }

  private static void buildMinHeap() {
    heapSize = data.length;
    for (int i = heapSize - 1; i >= 0; i--) {
      siftDown(i);
    }
  }

  private static void siftDown(int i) {
    int leftChild = leftChild(i);
    int rightChild = rightChild(i);
    if (leftChild >= heapSize) {
      return;
    }
    int minChild = leftChild;
    if (rightChild < heapSize) {
      if (data[leftChild] > data[rightChild]) {
        minChild = rightChild;
      }
    }
    if (data[i] > data[minChild]) {
      swap(i, minChild);
      siftDown(minChild);
    }
  }

  private static int leftChild(int i) {
    return (i << 1) + 1;
  }

  private static int rightChild(int i) {
    return (i << 1) + 2;
  }

  private static void swap(int i, int j) {
    int temp = data[i];
    data[i] = data[j];
    data[j] = temp;
    swaps.add(new Swap(i, j));
  }

  private static class Swap {

    int index1;

    int index2;

    public Swap(int index1, int index2) {
      this.index1 = index1;
      this.index2 = index2;
    }
  }
}
