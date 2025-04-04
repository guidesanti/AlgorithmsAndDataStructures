package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Arrays;

/**
 * Given an array arr[]  containing non-negative integers, the task is to divide it into two sets set1 and set2 such
 * that the absolute difference between their sums is minimum and find the minimum difference.
 */
public class MinimumSumPartition implements PA {

    private int[] array;

    private int diff;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read array size
        var n = scanner.nextInt();

        // Read array
        array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
    }

    private void writeOutput() {
        System.out.printf("%d", diff);
    }

    @Override
    public void reset() {
        array = null;
        diff = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        diff = findMinimumDiffByRecursion(0, 0, Arrays.stream(array).sum(), array);
    }

    private int findMinimumDiffByRecursion(int index, int subsetSum, int totalSum, int[] array) {
        // Base case
        if (index == array.length) {
            return Math.abs(subsetSum - (totalSum - subsetSum));
        }

        // Include current element on subset
        var include = findMinimumDiffByRecursion(index + 1, subsetSum + array[index], totalSum, array);

        // Exclude current element from subset
        var exclude = findMinimumDiffByRecursion(index + 1, subsetSum, totalSum, array);

        return Math.min(include, exclude);
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var totalSum = Arrays.stream(array).sum();
        var memo = new int[array.length + 1][totalSum + 1];
        for (int i = 0; i < memo.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        diff = findMinimumDiffByMemoization(0, 0, totalSum, array, memo);
    }

    private int findMinimumDiffByMemoization(int index, int subsetSum, int totalSum, int[] array, int[][] memo) {
        // Base case
        if (index == array.length) {
            return Math.abs(subsetSum - (totalSum - subsetSum));
        }

        // Check memo
        if (memo[index][subsetSum] != -1) {
            return memo[index][subsetSum];
        }

        // Include current element on subset
        var include = findMinimumDiffByMemoization(index + 1, subsetSum + array[index], totalSum, array, memo);

        // Exclude current element from subset
        var exclude = findMinimumDiffByMemoization(index + 1, subsetSum, totalSum, array, memo);

        var diff = Math.min(include, exclude);
        memo[index][subsetSum] = diff;
        return diff;
    }
}
