package br.com.eventhorizon.geeksforgeeks.greedy;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Given the weights and profits of N items, in the form of {profit, weight} put these items in a knapsack of capacity W
 * to get the maximum total profit in the knapsack.
 * In Fractional Knapsack, we can break items for maximizing the total value of the knapsack.
 */
public class FractionalKnapsackProblem implements PA {

    private static int n;

    private static int capacity;

    private static List<ProfitWeight> profitWeights;

    private static double maxProfit;

    private static void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n and capacity
        n = scanner.nextInt();
        capacity = scanner.nextInt();
        profitWeights = new ArrayList<>(n);

        // Read profits and weights
        for (int i = 0; i < n; i++) {
            profitWeights.add(new ProfitWeight(scanner.nextInt(), scanner.nextInt()));
        }
    }

    private static void writeOutput() {
        System.out.printf("%.2f", maxProfit);
    }

    @Override
    public void reset() {
        n = 0;
        capacity = 0;
        profitWeights = null;
        maxProfit = 0;
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        // Sort the profit/weight array by fraction in reverse order
        profitWeights.sort(Comparator.comparingDouble(ProfitWeight::getFraction));
        Collections.reverse(profitWeights);

        // Fill the knapsack
        int remainingCapacity = capacity;
        for (var profitWeight : profitWeights) {
            int count = Math.min(remainingCapacity, profitWeight.weight);
            maxProfit += count * profitWeight.getFraction();
            remainingCapacity -= count;
            if (remainingCapacity == 0) {
                break;
            }
        }
    }

    @Data
    private static class ProfitWeight {

        private final int profit;

        private final int weight;

        private final double fraction;

        public ProfitWeight(int profit, int weight) {
            this.profit = profit;
            this.weight = weight;
            this.fraction = (double) profit / weight;
        }
    }
}
