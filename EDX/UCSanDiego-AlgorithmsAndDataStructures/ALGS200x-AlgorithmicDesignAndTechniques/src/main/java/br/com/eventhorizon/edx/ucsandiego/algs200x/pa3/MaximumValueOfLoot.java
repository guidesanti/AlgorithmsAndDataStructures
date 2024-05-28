package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MaximumValueOfLoot implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int capacity = scanner.nextInt();
    int[] values = new int[n];
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
      weights[i] = scanner.nextInt();
    }
    System.out.format("%.4f", greedyMaximumValueOfLoot(capacity, values, weights));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int capacity = scanner.nextInt();
    int[] values = new int[n];
    int[] weights = new int[n];
    for (int i = 0; i < n; i++) {
      values[i] = scanner.nextInt();
      weights[i] = scanner.nextInt();
    }
    System.out.format("%.4f", greedyMaximumValueOfLoot(capacity, values, weights));
  }

  /*
   * Greedy algorithm
   * Candidates: the set of items: value of ith item / weight of ith item
   * Selection: choose the highest-value item remaining in the set of candidates
   * Feasible: checks whether the chosen item does not exceed the capacity of the knapsack
   * Objective: counts the total value in solution
   * Solution: checks whether the items already taken makes a solution for the problem
   */
  private double greedyMaximumValueOfLoot(int capacity, int[] values, int[] weights) {
    double result = 0;
    while (capacity > 0) {
      int selectedItem = findMaxValuePerWeight(values, weights);
      if (capacity >= weights[selectedItem]) {
        result += values[selectedItem];
        capacity -= weights[selectedItem];
        values[selectedItem] = 0;
      } else {
        result += capacity * ((double) values[selectedItem] / weights[selectedItem]);
        break;
      }
    }
    return result;
  }

  private int findMaxValuePerWeight(int[] values, int[] weights) {
    double max = 0;
    int maxIndex = 0;
    for (int i = 0; i < values.length; i++) {
      double current = (double) values[i] / weights[i];
      if (current >= max) {
        max = current;
        maxIndex = i;
      }
    }
    return maxIndex;
  }
}
