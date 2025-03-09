package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

/**
 * Given an array arr[] of size n, the task is to find the length of the Longest Increasing Subsequence (LIS) i.e.,
 * the longest possible subsequence in which the elements of the subsequence are sorted in increasing order.
 */
public class LongestIncreasingSubsequence implements PA {

    private int n;

    private int[] values;

    private long lisLength;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n
        n = scanner.nextInt();

        // Read values
        values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
        }
    }

    private void writeOutput() {
        System.out.printf("%s", lisLength);
    }

    @Override
    public void reset() {
        n = 0;
        values = null;
        lisLength = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        lisLength = lisLengthRecursive(0, Integer.MIN_VALUE, 0);
    }

    private long lisLengthRecursive(int index, int last, int length) {
        if (index >= n) {
            return length;
        }

        if (values[index] <= last) {
            return lisLengthRecursive(index + 1, last, length);
        } else {
            return Math.max(
                    lisLengthRecursive(index + 1, last, length),
                    lisLengthRecursive(index + 1, values[index], length + 1)
            );
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
//        lisLengthByMemoization();
        lisLengthByTabulation();
    }

    private void lisLengthByMemoization() {
        lisLength = lisLengthMemoized(0, Integer.MIN_VALUE, 0, new HashMap<>());
    }

    private long lisLengthMemoized(int index, int last, int length, Map<String, Long> memo) {
        if (index >= n) {
            return length;
        }

        var key = index + ":" + last + ":" + length;
        var memoLength = memo.getOrDefault(key, null);
        if (Objects.nonNull(memoLength)) {
            return memoLength;
        }

        if (values[index] <= last) {
            memoLength = lisLengthMemoized(index + 1, last, length, memo);
        } else {
            memoLength = Math.max(
                    lisLengthMemoized(index + 1, last, length, memo),
                    lisLengthMemoized(index + 1, values[index], length + 1, memo)
            );
        }
        memo.put(key, memoLength);

        return memoLength;
    }

    private void lisLengthByTabulation() {
        var lis = new int[n];

        for (int i = 0; i < n; i++) {
            lis[i] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (values[i] > values[prev] && lis[i] < lis[prev] + 1) {
                    lis[i] = lis[prev] + 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            lisLength = Math.max(lisLength, lis[i]);
        }
    }
}
