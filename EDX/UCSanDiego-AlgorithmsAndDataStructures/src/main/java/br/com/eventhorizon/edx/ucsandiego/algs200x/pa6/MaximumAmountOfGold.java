package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class MaximumAmountOfGold implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int capacity = scanner.nextInt();
    int n = scanner.nextInt();
    List<Integer> weights = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      weights.add(scanner.nextInt());
    }
    System.out.println(naiveOptimalWeight(capacity, weights));
  }

  static int naiveOptimalWeight(int capacity, List<Integer> weights) {
    if (weights.size() == 1) {
      if (weights.get(0) <= capacity) {
        return weights.get(0);
      }
      return 0;
    }
    int maxWeight = 0;
    for (int i = 0; i < weights.size(); i++) {
      List<Integer> newWeights = new ArrayList<>(weights);
      int weight = newWeights.remove(i);
      int max = naiveOptimalWeight(capacity, newWeights);
      if (max + weight <= capacity) {
        max = max + weight;
      }
      if (max > maxWeight) {
        maxWeight = max;
      }
    }
    return maxWeight;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int capacity = scanner.nextInt();
    int n = scanner.nextInt();
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      weights[i] = scanner.nextInt();
    }
    System.out.println(finalOptimalWeight(capacity, weights));
  }

  static int finalOptimalWeight(int capacity, int[] weights) {
    int [][] table = new int[weights.length + 1][capacity + 1];
    for (int i = 0; i <= weights.length; i++) {
      for (int j = 0; j <= capacity; j++) {
        if (i == 0 || j == 0) {
          table[i][j] = 0;
        } else if (weights[i - 1] > j) {
          table[i][j] = table[i - 1][j];
        } else {
          table[i][j] = Math.max(table[i - 1][j], table[i - 1][j - weights[i - 1]] + weights[i - 1]);
        }
      }
    }
    return table[weights.length][capacity];
  }
}
