package br.com.eventhorizon.sat;

import java.util.ArrayList;
import java.util.List;

public class NaiveSatSolver implements SatSolverAlgorithm {

  @Override
  public List<Integer> solve(Cnf cnf) {
    List<Integer> solution = new ArrayList<>();
    for (int i = 0; i <= cnf.numberOfVariables(); i++) {
      solution.add(-i);
    }
    do {
      if (SatVerifier.verify(cnf, solution)) {
        return solution;
      }
    } while (next(solution));
    return null;
  }

  private boolean next(List<Integer> solution) {
    int index = solution.size() - 1;
    while (index > 0 && solution.get(index) > 0) {
      solution.set(index, -solution.get(index));
      index--;
    }
    solution.set(index, -solution.get(index));
    return index > 0;
  }
}
