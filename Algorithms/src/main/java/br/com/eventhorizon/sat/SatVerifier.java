package br.com.eventhorizon.sat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SatVerifier {

  public static boolean verify(Cnf cnf, List<Integer> solution) {
    if (cnf == null) {
      throw new IllegalArgumentException("cnf cannot be null");
    }
    if (solution == null) {
      throw new IllegalArgumentException("solution cannot be null pr its size should be equals to number of variables in cnf");
    }
    Set<Integer> solutionSet = new HashSet<>(solution);
    for (List<Integer> clause : cnf.clauses()) {
      boolean satisfied = false;
      for (Integer literal : clause) {
        if (literal == 0 || !solutionSet.contains(literal)) {
          continue;
        }
        if (solutionSet.contains(literal)) {
          satisfied = true;
          break;
        }
      }
      if (!satisfied) {
        return false;
      }
    }
    return true;
  }
}
