package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Given an integer array of coins[] of size n representing different types of denominations and an integer sum,
 * the task is to count all combinations of coins to make a given value sum.
 * <p>
 * Note: Assume that you have an infinite supply of each type of coin.
 */
public class CoinChange implements PA {

    private int sum;

    private int[] coins;

    private int solutions;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read sum
        sum = scanner.nextInt();

        // Read coins
        coins = new int[scanner.nextInt()];
        for (int i = 0; i < coins.length; i++) {
            coins[i] = scanner.nextInt();
        }
    }

    private void writeOutput() {
        System.out.printf("%d", solutions);
    }

    @Override
    public void reset() {
        sum = 0;
        coins = null;
        solutions = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        solutions = solutionsByRecursion(coins, 0, 0);
    }

    private int solutionsByRecursion(int[] coins, int index, int currSum) {
        if (index >= coins.length) {
            return 0;
        }

        if (currSum == sum) {
            return 1;
        }
        if (currSum > sum) {
            return 0;
        }

        return solutionsByRecursion(coins, index, currSum + coins[index]) + solutionsByRecursion(coins, index + 1, currSum);
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        solutionsByMemoization();
//        solutionsByTabulation();
    }

    private void solutionsByMemoization() {
        for (int i = 0; i < coins.length; i++) {
            solutions += solutionsByMemoization(coins, i, 0, 0, new HashMap<>());
        }
    }

    private int solutionsByMemoization(int[] coins, int index, int currSum, int solutions, Map<String, Integer> memo) {
        if (index >= coins.length) {
            return solutions;
        }

        var key = index + ":" + currSum + ":" + solutions;
        var memoSolutions = memo.getOrDefault(key, null);
        if (Objects.nonNull(memoSolutions)) {
            return memoSolutions;
        }

        currSum += coins[index];
        if (currSum == sum) {
            memoSolutions = solutions + 1;
            memo.put(key, memoSolutions);
            return memoSolutions;
        }
        if (currSum > sum) {
            memoSolutions = solutions;
            memo.put(key, memoSolutions);
            return memoSolutions;
        }

        for (int i = index; i < coins.length; i++) {
            solutions = Math.max(solutions, solutionsByMemoization(coins, i, currSum, solutions, memo));
        }

        return solutions;
    }

    private void solutionsByTabulation() {
    }

    private record Item(int profit, int weight) { }
}
