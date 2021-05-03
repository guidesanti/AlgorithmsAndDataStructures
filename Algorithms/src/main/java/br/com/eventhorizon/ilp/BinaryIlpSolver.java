package br.com.eventhorizon.ilp;

public interface BinaryIlpSolver {

  int[] solve(int[][] coefficients, int[]b);
}
