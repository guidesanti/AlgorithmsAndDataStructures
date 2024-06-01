package br.com.eventhorizon.array;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given an array of N non-negative integers arr[] representing an elevation map where the width of each bar is 1,
 * compute how much water it is able to trap after raining.
 */
public class TrappingRainWater implements PA {

    private static int n;

    private static int[] array;

    private static long trappedWater;

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
        System.out.println(trappedWater);
    }

    @Override
    public void reset() {
        n = 0;
        array = null;
        trappedWater = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        if (n < 3) {
            return;
        }
        find(0, n -1);
    }

    private void find(int start, int end) {
        while (array[start] == 0) {
            start++;
            if (start >= end) {
                return;
            }
        }
        while (array[end] == 0) {
            end--;
            if (start >= end) {
                return;
            }
        }
        array[start]--;
        array[end]--;
        for (int i = start + 1; i <= end - 1; i++) {
            if (array[i] == 0) {
                trappedWater++;
            } else {
                array[i]--;
            }
        }
        find(start, end);
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var peaks = findPeaks();
        if (peaks.isEmpty()) {
            return;
        }
        var start = 0;
        while (peaks.size() > 1) {
            var peak = peaks.poll();
            calculateTrappedWaterLeftToRight(start, peak);
            start = peak;
        }
        var peak = peaks.poll();
        calculateTrappedWaterLeftToRight(start, peak);
        calculateTrappedWaterRightToLeft(n - 1, peak);
    }

    private Queue<Integer> findPeaks() {
        var peaks = new LinkedList<Integer>();
        for (int i = 0; i < n; i++) {
            if (array[i] == 0) {
                continue;
            }
            var peak = peaks.isEmpty() ? 0 : array[peaks.get(0)];
            if (array[i] > peak) {
                peaks.clear();
                peaks.add(i);
            } else if (array[i] == peak) {
                peaks.add(i);
            }
        }
        return peaks;
    }

    private void calculateTrappedWaterLeftToRight(int start, int end) {
        for (int i = start; i < end; i++) {
            if (array[i] >= array[start]) {
                start = i;
            } else {
                trappedWater += array[start] - array[i];
            }
        }
    }

    private void calculateTrappedWaterRightToLeft(int start, int end) {
        for (int i = start; i > end; i--) {
            if (array[i] >= array[start]) {
                start = i;
            } else {
                trappedWater += array[start] - array[i];
            }
        }
    }
}
