package br.com.eventhorizon.sat;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.FastScanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SatUtils {

  public static Cnf readCnfFromFile(String file) {
    try {
      FastScanner scanner = new FastScanner(new FileInputStream(file));
      String p = scanner.next();
      String cnf = scanner.next();
      if (!p.equals("p") || !cnf.equals("cnf")) {
        throw new RuntimeException("Invalid CNF file, first line does not start with \"p cnf\"");
      }
      int numberOfVariables = scanner.nextInt();
      int numberOfClauses = scanner.nextInt();
      List<List<Integer>> clauses = new ArrayList<>();
      for (int i = 0; i < numberOfClauses; i++) {
        List<Integer> clause = new ArrayList<>();
        int next = scanner.nextInt();
        while (next != 0) {
          clause.add(next);
          next = scanner.nextInt();
        }
        clauses.add(clause);
      }
      return new Cnf(numberOfVariables, clauses);
    } catch (IOException exception) {
      throw new RuntimeException("Failed to read CNF file", exception);
    }
  }

  public static Cnf getRandomCnf(int minNumberOfVariables, int maxNumberOfVariables,
                                 int minNumberOfClauses, int maxNumberOfClauses) {
    int numberOfVariables = Utils.getRandomInteger(minNumberOfVariables, maxNumberOfVariables);
    int numberOfClauses = Utils.getRandomInteger(minNumberOfClauses, maxNumberOfClauses);
    Set<Integer> unassignedVariables = new HashSet<>();
    for (int i = 1; i <= numberOfVariables; i++) {
      unassignedVariables.add(i);
    }
    List<List<Integer>> clauses = new ArrayList<>();
    for (int i = 0; i < numberOfClauses; i++) {
      List<Integer> clause = new ArrayList<>();
      for (int j = 1; j <= numberOfVariables; j++) {
        if (Utils.getRandomBoolean()) {
          unassignedVariables.remove(j);
          clause.add(Utils.getRandomBoolean() ? -j : j);
        }
      }
      if (clause.isEmpty()) {
        clause.add(Utils.getRandomInteger(1, numberOfVariables));
      }
      clauses.add(clause);
    }
    if (!unassignedVariables.isEmpty()) {
      clauses.get(0).addAll(unassignedVariables);
    }
    return new Cnf(numberOfVariables, clauses);
  }
}
