package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class NumberOfInversions implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    int[] b = new int[n];
    System.out.println(naiveNumberOfInversions(a));
  }

  private static long naiveNumberOfInversions(int[] a) {
    long count = 0;
    for (int i = 0; i < a.length; i++) {
      for (int j = i + 1; j < a.length; j++) {
        if (a[i] > a[j]) {
          count++;
        }
      }
    }
    return count;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
      a[i] = scanner.nextInt();
    }
    int[] b = new int[n];
    System.out.println(finalNumberOfInversions(a, 0, a.length - 1));
  }

  private static long finalNumberOfInversions(int[] a, int low, int high) {
    long numberOfInvertions = 0;
    if (high == low) {
      return 0;
    }
    int middle = (low + high) / 2;
    numberOfInvertions += finalNumberOfInversions(a, low, middle);
    numberOfInvertions += finalNumberOfInversions(a, middle + 1, high);
    numberOfInvertions += merge(a, low, middle, high);
    return numberOfInvertions;
  }

  private static long merge(int[] a, int low, int middle, int high) {
    int[] lowArray = new int[middle - low + 2];
    for (int i = 0; i < lowArray.length - 1; i++) {
      lowArray[i] = a[low + i];
    }
    lowArray[lowArray.length - 1] = Integer.MAX_VALUE;
    int[] highArray = new int[high - middle + 1];
    for (int i = 0; i < highArray.length - 1; i++) {
      highArray[i] = a[middle + i + 1];
    }
    highArray[highArray.length - 1] = Integer.MAX_VALUE;
    int lowIndex = 0;
    int highIndex = 0;
    long count = 0;
    for (int i = low; i <= high; i++) {
      if (lowArray[lowIndex] > highArray[highIndex]) {
        a[i] = highArray[highIndex++];
        count += lowArray.length - lowIndex - 1;
      } else {
        a[i] = lowArray[lowIndex++];
      }
    }
    return count;
  }
}
