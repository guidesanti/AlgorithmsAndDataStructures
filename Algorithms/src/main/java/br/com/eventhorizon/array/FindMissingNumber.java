package br.com.eventhorizon.array;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

/**
 * Given an array arr[] of size N-1 with integers in the range of [1, N], the task is to find the missing number from the first N integers.
 * Note: There are no duplicates in the list.
 */
public class FindMissingNumber implements PA {

    private static int n;

    private static int[] array;

    private static int missingNumber;

    private static void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read array length
        n = scanner.nextInt();
        array = new int[n - 1];

        // Read array
        for (int i = 0; i < n - 1; i++) {
            array[i] = scanner.nextInt();
        }
    }

    private static void writeOutput() {
        System.out.println(missingNumber);
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var tempArray = new boolean[n];
        for (int i = 0; i < n - 1; i++) {
            tempArray[array[i] - 1] = true;
        }
        for (int i = 0; i < n; i++) {
            if (!tempArray[i]) {
                missingNumber = i + 1;
                break;
            }
        }
    }
}
