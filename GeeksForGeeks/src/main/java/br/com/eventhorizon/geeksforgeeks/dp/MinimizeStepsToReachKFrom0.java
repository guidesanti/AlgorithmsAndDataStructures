package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Given a positive integer K, the task is to find the minimum number of operations of the following two types,
 * required to change 0 to K:
 * - Add one to the operand
 * - Multiply the operand by 2
 */
public class MinimizeStepsToReachKFrom0 implements PA {

    private int k;

    private int numberOfOperations;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read k
        k = scanner.nextInt();
    }

    private void writeOutput() {
        System.out.printf("%d", numberOfOperations);
    }

    @Override
    public void reset() {
        k = 0;
        numberOfOperations = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        numberOfOperations = numberOfOperationsByRecursion(1, 1);
    }

    private int numberOfOperationsByRecursion(int numberOfOperations, int currentValue) {
        if (currentValue > k) {
            return 0;
        }
        if (currentValue == k) {
            return numberOfOperations;
        }

        var n1 = numberOfOperationsByRecursion(numberOfOperations + 1, currentValue + 1);
        var n2 = numberOfOperationsByRecursion(numberOfOperations + 1, currentValue * 2);

        if (n1 == 0) {
            return n2;
        }
        if (n2 == 0) {
            return n1;
        }
        return Math.min(n1, n2);
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var dp = new int[k + 1];

        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= k; i++) {
            if (i % 2 == 0) {
                dp[i] = Math.min(dp[i - 1], dp[i / 2]) + 1;
            } else {
                dp[i] = dp[i - 1] + 1;
            }
        }

        numberOfOperations = dp[k];
    }

    private record Item(int profit, int weight) { }
}
