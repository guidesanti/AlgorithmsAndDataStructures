package br.com.eventhorizon.array;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;
import lombok.extern.slf4j.Slf4j;

/**
 * Given an array arr[] of size N.
 * The task is to find the sum of the contiguous subarray within a arr[] with the largest sum.
 */
@Slf4j
public class MaxSubarraySum implements PA {

    private static int n;

    private static int[] array;

    private static long maxSubarraySum;

    private static void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read array length
        n = scanner.nextInt();
        array = new int[n];

        // Read array
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
    }

    private static void writeOutput() {
        System.out.println(maxSubarraySum);
    }

    @Override
    public void reset() {
        maxSubarraySum = Integer.MIN_VALUE;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        for (int i = 0; i < n; i++) {
            var sum = 0;
            for (int j = i; j < n; j++) {
                if (Thread.interrupted()) {
                    break;
                }
                sum += array[j];
                maxSubarraySum = Math.max(maxSubarraySum, sum);
            }
        }
    }

    private void finalSolutionImpl() {
        var sum = 0L;
        for (int i = 0; i < n; i++) {
            sum += array[i];
            maxSubarraySum = Math.max(maxSubarraySum, sum);
            sum = sum < 0 ? 0 : sum;
        }
    }
}
