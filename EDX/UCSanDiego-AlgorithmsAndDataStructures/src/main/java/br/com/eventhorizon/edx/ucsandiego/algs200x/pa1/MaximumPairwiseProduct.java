package br.com.eventhorizon.edx.ucsandiego.algs200x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MaximumPairwiseProduct implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    long[] numbers = new long[n];
    for (int i = 0; i < n; i++) {
      numbers[i] = scanner.nextInt();
    }
    System.out.println(maximumPairwiseProductTrivial(numbers));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    long first = scanner.nextInt();
    long second = scanner.nextInt();
    long max1;
    long max2;
    if (first > second) {
      max1 = first;
      max2 = second;
    } else {
      max1 = second;
      max2 = first;
    }
    for (int i = 2; i < n; i++) {
      long value = scanner.nextInt();
      if (value > max1) {
        max2 = max1;
        max1 = value;
      } else if (value > max2) {
        max2 = value;
      }
    }
    System.out.println(max1 * max2);
  }

  private static long maximumPairwiseProductTrivial(long[] numbers) {
    long product = 0;
    int n = numbers.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        product = Math.max(product, numbers[i]*numbers[j]);
      }
    }
    return product;
  }

  private static long maximumPairwiseProductFaster1(long[] numbers) {
    int n = numbers.length;
    long max1 = 0;
    long max2 = 0;
    int max1Index = 0;
    for (int i = 0; i < n; i++) {
      if (numbers[i] > max1) {
        max1 = numbers[i];
        max1Index = i;
      }
    }
    for (int i = 0; i < n; i++) {
      if (max1Index != i && numbers[i] > max2) {
        max2 = numbers[i];
      }
    }
    return max1 * max2;
  }

  private static long maximumPairwiseProductFaster2(long[] numbers) {
    int n = numbers.length;
    long max1;
    long max2;
    if (numbers[0] > numbers[1]) {
      max1 = numbers[0];
      max2 = numbers[1];
    } else {
      max1 = numbers[1];
      max2 = numbers[0];
    }
    for (int i = 2; i < n; i++) {
      if (numbers[i] > max1) {
        max2 = max1;
        max1 = numbers[i];
      } else if (numbers[i] > max2) {
        max2 = numbers[i];
      }
    }
    return max1 * max2;
  }
}
