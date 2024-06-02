package br.com.eventhorizon.geeksforgeeks.array;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

/**
 * Given an array that contains both positive and negative integers, the task is to find the product of the maximum product subarray.
 */
public class MaxSubarrayProduct implements PA {

    private static int n;

    private static int[] array;

    private static long maxSubarrayProduct;

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
        System.out.println(maxSubarrayProduct);
    }

    @Override
    public void reset() {
        maxSubarrayProduct = Long.MIN_VALUE;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        for (int i = 0; i < n; i++) {
            var product = 1L;
            for (int j = i; j < n; j++) {
                if (Thread.interrupted()) {
                    break;
                }
                product *= array[j];
                maxSubarrayProduct = Math.max(maxSubarrayProduct, product);
            }
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        long productLeft = 1;
        long productRight = 1;
        for (int i = 0; i < n; i++) {
            productLeft *= array[i];
            maxSubarrayProduct = Math.max(maxSubarrayProduct, productLeft);
            if (productLeft == 0) {
                productLeft = 1;
            }
            productRight *= array[n - i - 1];
            maxSubarrayProduct = Math.max(maxSubarrayProduct, productRight);
            if (productRight == 0) {
                productRight = 1;
            }
        }
    }
}
