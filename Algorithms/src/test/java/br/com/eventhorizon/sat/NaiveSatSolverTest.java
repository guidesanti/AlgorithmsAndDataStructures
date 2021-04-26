package br.com.eventhorizon.sat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NaiveSatSolverTest {

  private static final String DATA_SET = "/sat/sat-solver.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void tesNaiveSatSolver(
      String satCnfFileName,
      boolean satisfiable) {
    Cnf cnf = SatUtils.readCnfFromFile("src/test/resources/sat/" + satCnfFileName);
    NaiveSatSolver satSolver = new NaiveSatSolver();
    List<Integer> solution = satSolver.solve(cnf);
    if (satisfiable) {
      assertNotNull(solution);
      assertEquals(cnf.numberOfVariables(), solution.size() - 1);
      assertTrue(SatVerifier.verify(cnf, solution));
    } else {
      assertNull(solution);
    }
  }
}
