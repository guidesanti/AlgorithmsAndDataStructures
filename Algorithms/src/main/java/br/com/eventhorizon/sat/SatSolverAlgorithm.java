package br.com.eventhorizon.sat;

import java.util.List;

public interface SatSolverAlgorithm {

  List<Integer> solve(Cnf cnf);
}
