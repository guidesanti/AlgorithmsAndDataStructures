package br.com.eventhorizon.geeksforgeeks.array;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

/**
 * Given a sequence arr[] of size n, Write a function int equilibrium(int[] arr, int n) that returns an
 * equilibrium index (if any) or -1 if no equilibrium index exists.
 * The equilibrium index of an array is an index such that the sum of elements at lower indexes is
 * equal to the sum of elements at higher indexes.
 */
public class EquilibriumIndexOfAnArray implements PA {

    private static int n;

    private static int[] array;

    private static int equilibriumIndex;

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
        System.out.println(equilibriumIndex);
    }

    @Override
    public void reset() {
        n = 0;
        array = null;
        equilibriumIndex = -1;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        if (n == 0 || n == 2) {
            equilibriumIndex = -1;
            return;
        }
        if (n == 1) {
            equilibriumIndex = 0;
            return;
        }
        for (int i = 1; i < n - 1; i++) {
            var sumLeft = 0;
            var sumRight = 0;
            for (int j = 0; j < i; j++) {
                sumLeft += array[j];
            }
            for (int j = i + 1; j < n; j++) {
                sumRight += array[j];
            }
            if (sumLeft == sumRight) {
                equilibriumIndex = i;
                return;
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
        if (n == 0 || n == 2) {
            equilibriumIndex = -1;
            return;
        }
        if (n == 1) {
            equilibriumIndex = 0;
            return;
        }
        long sumLeft = array[0];
        long sumRight = 0;
        for (int i = 2; i < n; i++) {
            sumRight += array[i];
        }
        if (sumLeft == sumRight) {
            equilibriumIndex = 1;
            return;
        }
        for (int i = 2; i < n - 1; i++) {
            sumLeft += array[i - 1];
            sumRight -= array[i];
            if (sumLeft == sumRight) {
                equilibriumIndex = i;
                return;
            }
        }
    }
}
