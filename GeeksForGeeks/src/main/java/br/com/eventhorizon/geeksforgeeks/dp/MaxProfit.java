package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Arrays;

/**
 * In the stock market, a person buys a stock and sells it on some future date. You are given an array prices[]
 * representing stock prices on different days and a positive integer k, find out the maximum profit a person
 * can make in at-most k transactions.
 * <p>
 * A transaction consists of buying and subsequently selling a stock and new transaction can start only when the
 * previous transaction has been completed.
 */
public class MaxProfit implements PA {

    private int[] prices;

    private int k;

    private int maxProfit;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read k
        k = scanner.nextInt();

        // Read prices
        var n = scanner.nextInt();
        prices = new int[n];
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }
    }

    private void writeOutput() {
        System.out.printf("%d", maxProfit);
    }

    @Override
    public void reset() {
        prices = null;
        k = 0;
        maxProfit = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        maxProfit = findMaxProfitByRecursion(0, k, 0, 0, prices);
    }

    private int findMaxProfitByRecursion(int index, int k, int buyPrice, int profit, int[] prices) {
        // Base case
        if (index == prices.length || k < 0) {
            return profit;
        }

        if (buyPrice == 0) {
            // Do nothing or buy
            return Math.max(
                    findMaxProfitByRecursion(index + 1, k, buyPrice, profit, prices),
                    findMaxProfitByRecursion(index + 1, k - 1, prices[index], profit, prices));
        } else {
            // Do nothing or sell
            return Math.max(
                    findMaxProfitByRecursion(index + 1, k, buyPrice, profit, prices),
                    findMaxProfitByRecursion(index + 1, k, 0, profit + prices[index] - buyPrice, prices));
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var memo = new int[prices.length][k + 1][2];
        for (int i = 0; i < prices.length; i++) {
            for (int j = 0; j <= k; j++) {
                Arrays.fill(memo[i][j], Integer.MIN_VALUE);
            }
        }
        maxProfit = findMaxProfitByMemoization(0, k, 1, prices, memo);
    }

    private int findMaxProfitByMemoization(int index, int k, int buy, int[] prices, int[][][] memo) {
        // Base case
        if (index == prices.length || k < 0) {
            return 0;
        }

        // Look on cache
        if (memo[index][k][buy] != Integer.MIN_VALUE) {
            return memo[index][k][buy];
        }

        var result = 0;
        var profit = 0;
        if (buy == 1) {
            // Buy
            profit = findMaxProfitByMemoization(index + 1, k - 1, 0, prices, memo) - prices[index];
        } else {
            // Sell
            profit = prices[index] + findMaxProfitByMemoization(index + 1, k, 1, prices, memo);
        }
        result = Math.max(result, profit);

        // Skip
        profit = findMaxProfitByMemoization(index + 1, k, buy, prices, memo);
        result = Math.max(result, profit);

        // Store on cache
        memo[index][k][buy] = result;
        return result;
    }
}
