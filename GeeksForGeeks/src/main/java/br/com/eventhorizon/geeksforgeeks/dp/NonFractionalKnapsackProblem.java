package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

/**
 * Given N items where each item has some weight and profit associated with it and also given a bag with capacity W,
 * [i.e., the bag can hold at most W weight in it]. The task is to put the items into the bag such that the sum of
 * profits associated with them is the maximum possible.
 * <p>
 * Note: The constraint here is we can either put an item completely into the bag or cannot put it at all [It is not
 * possible to put a part of an item into the bag].
 */
public class NonFractionalKnapsackProblem implements PA {

    private int n;

    private int capacity;

    private List<Item> items;

    private long maxProfit;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n and capacity
        n = scanner.nextInt();
        capacity = scanner.nextInt();

        // Read items
        for (int i = 0; i < n; i++) {
            items.add(new Item(scanner.nextInt(), scanner.nextInt()));
        }
    }

    private void writeOutput() {
        System.out.printf("%d", maxProfit);
    }

    @Override
    public void reset() {
        n = 0;
        capacity = 0;
        items = new ArrayList<>();
        maxProfit = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        maxProfit = maxProfit(0, 0, 0);
    }

    private long maxProfit(int index, int currentProfit, int currentWeight) {
        if (currentWeight > capacity) {
            return 0;
        }
        if (index > items.size() - 1) {
            return currentProfit;
        }
        var item = items.get(index);
        return Math.max(
                maxProfit(index + 1, currentProfit, currentWeight),
                maxProfit(index + 1, currentProfit + item.profit, currentWeight + item.weight)
        );
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
//        maxProfitByMemoization();
        maxProfitByTabulation();
    }

    private long maxProfitMemoized(int index, long currentProfit, int currentWeight, Map<String, Long> memo) {
        if (currentWeight > capacity) {
            return 0L;
        }
        if (index > items.size() - 1) {
            return currentProfit;
        }

        var key = index + ":" + currentProfit + ":" + currentWeight;
        var profit = memo.getOrDefault(key, null);
        if (Objects.nonNull(profit)) {
            return profit;
        }

        var item = items.get(index);
        profit = Math.max(
                maxProfitMemoized(index + 1, currentProfit, currentWeight, memo),
                maxProfitMemoized(index + 1, currentProfit + item.profit, currentWeight + item.weight, memo)
        );
        memo.put(key, profit);

        return profit;
    }

    private void maxProfitByMemoization() {
        maxProfit = maxProfitMemoized(0, 0, 0, new HashMap<>());
    }

    private void maxProfitByTabulation() {
        var dp = new int[n + 1][capacity + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                    continue;
                }
                var item = items.get(i - 1);
                if (item.weight <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], item.profit + dp[i - 1][w - item.weight]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        maxProfit = dp[n][capacity];
    }

    private record Item(int profit, int weight) { }
}
