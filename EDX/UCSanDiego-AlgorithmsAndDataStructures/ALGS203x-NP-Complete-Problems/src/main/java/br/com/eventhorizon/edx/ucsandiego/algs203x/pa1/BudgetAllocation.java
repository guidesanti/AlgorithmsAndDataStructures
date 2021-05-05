package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import br.com.eventhorizon.ilp.BinaryIlpSolver;
import br.com.eventhorizon.ilp.NaiveBinaryIlpSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BudgetAllocation implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfInequalities = scanner.nextInt();
    int numberOfVariables = scanner.nextInt();
    int[][] coefficients = new int[numberOfInequalities][numberOfVariables];
    for (int i = 0; i < numberOfInequalities; i++) {
      for (int j = 0; j < numberOfVariables; j++) {
        coefficients[i][j] = scanner.nextInt();
      }
    }
    int[] b = new int[numberOfInequalities];
    for (int i = 0; i < numberOfInequalities; i++) {
      b[i] = scanner.nextInt();
    }
    BinaryIlpSolver solver = new NaiveBinaryIlpSolver();
    int[] solution = solver.solve(coefficients, b);
    if (solution == null) {
      System.out.println("2 1");
      System.out.println("1 0");
      System.out.println("-1 0");
    } else {
      System.out.println("1 1");
      System.out.println("1 0");
    }
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfInequalities = scanner.nextInt();
    int numberOfVariables = scanner.nextInt();
    List<Pair>[] coefficients = new List[numberOfInequalities];
    for (int i = 0; i < numberOfInequalities; i++) {
      coefficients[i] = new ArrayList<>();
      for (int j = 0; j < numberOfVariables; j++) {
        int coefficient = scanner.nextInt();
        if (coefficient != 0) {
          coefficients[i].add(new Pair(coefficient, j + 1));
        }
      }
    }
    int[] b = new int[numberOfInequalities];
    for (int i = 0; i < numberOfInequalities; i++) {
      b[i] = scanner.nextInt();
    }

    List<List<Integer>> clauses = new ArrayList<>();

    for (int i = 0; i < numberOfInequalities; i++) {
      List<Pair> pairs = coefficients[i];

      if (pairs.isEmpty()) {
        if (b[i] < 0) {
          clauses.clear();
          List<Integer> clause = new ArrayList<>();
          clause.add(1);
          clauses.add(clause);
          clause = new ArrayList<>();
          clause.add(-1);
          clauses.add(clause);
          numberOfVariables = 1;
          break;
        }
        continue;
      }

      if (0 > b[i]) {
        clauses.add(pairs.stream().map(pair -> pair.variable).collect(Collectors.toList()));
      }

      for (int j = 0; j < pairs.size(); j++) {
        Pair p1 = pairs.get(j);
        if (p1.coefficient > b[i]) {
          clauses.add(pairs.stream()
              .map(pair -> pair != p1 ? pair.variable : -p1.variable)
              .collect(Collectors.toList()));
        }
      }
      if (pairs.size() == 1) {
        continue;
      }

      for (int j = 0; j < pairs.size(); j++) {
        for (int k = j + 1; k < pairs.size(); k++) {
          Pair p1 = pairs.get(j);
          Pair p2 = pairs.get(k);
          if (p1.coefficient + p2.coefficient > b[i]) {
            clauses.add(pairs.stream()
                .map(pair -> pair == p1 ? -pair.variable : pair == p2 ? -pair.variable : pair.variable)
                .collect(Collectors.toList()));
          }
        }
      }
      if (pairs.size() == 2) {
        continue;
      }

      if (pairs.stream().map(pair -> pair.coefficient).reduce(Integer::sum).get() > b[i]) {
        clauses.add(pairs.stream().map(pair -> -pair.variable).collect(Collectors.toList()));
      }
    }

    output(numberOfVariables, clauses);
  }

  private static void output(int numberOfVariables, List<List<Integer>> clauses) {
    if (clauses.isEmpty()) {
      System.out.println("1 1");
      System.out.println("1 0");
    } else {
      System.out.println(clauses.size() + " " + numberOfVariables);
      clauses.forEach(clause -> {
        clause.forEach(literal -> System.out.print(literal + " "));
        System.out.println(0);
      });
    }
  }

  private static class Pair {

    int coefficient;

    int variable;

    public Pair(int coefficient, int variable) {
      this.coefficient = coefficient;
      this.variable = variable;
    }
  }
}
