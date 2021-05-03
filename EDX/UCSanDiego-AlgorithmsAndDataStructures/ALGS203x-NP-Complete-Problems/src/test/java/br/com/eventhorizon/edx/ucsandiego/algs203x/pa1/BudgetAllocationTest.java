package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.sat.Cnf;
import br.com.eventhorizon.sat.NaiveSatSolver;
import br.com.eventhorizon.sat.SatSolverAlgorithm;
import br.com.eventhorizon.sat.SatVerifier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetAllocationTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/budget-allocation.csv";

  public BudgetAllocationTest() {
    super(new BudgetAllocation(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    boolean expectedResult = Boolean.parseBoolean(expectedOutput);
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();
    String actualOutput = getActualOutput();
    String[] lines = actualOutput.split("\n");
    String[] line = lines[0].split(" ");
    int numberOfClauses = Integer.parseInt(line[0]);
    assertEquals(numberOfClauses, lines.length - 1);
    int numberOfVariables = Integer.parseInt(line[1]);
    List<List<Integer>> clauses = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      line = lines[i].split(" ");
      List<Integer> clause = new ArrayList<>();
      for (int j = 0; j < line.length; j++) {
        clause.add(Integer.parseInt(line[j]));
      }
      clauses.add(clause);
    }
    Cnf cnf = new Cnf(numberOfVariables, clauses);
    SatSolverAlgorithm satSolver = new NaiveSatSolver();
    List<Integer> solution = satSolver.solve(cnf);
    if (solution != null) {
      assertTrue(expectedResult);
      assertTrue(SatVerifier.verify(cnf, solution));
    } else {
      assertFalse(expectedResult);
    }
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    boolean expectedResult = Boolean.parseBoolean(expectedOutput);
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();
    String actualOutput = getActualOutput();
    String[] lines = actualOutput.split("\n");
    String[] line = lines[0].split(" ");
    int numberOfClauses = Integer.parseInt(line[0]);
    assertEquals(numberOfClauses, lines.length - 1);
    int numberOfVariables = Integer.parseInt(line[1]);
    List<List<Integer>> clauses = new ArrayList<>();
    for (int i = 1; i < lines.length; i++) {
      line = lines[i].split(" ");
      List<Integer> clause = new ArrayList<>();
      for (int j = 0; j < line.length; j++) {
        clause.add(Integer.parseInt(line[j]));
      }
      clauses.add(clause);
    }
    Cnf cnf = new Cnf(numberOfVariables, clauses);
    SatSolverAlgorithm satSolver = new NaiveSatSolver();
    List<Integer> solution = satSolver.solve(cnf);
    if (solution != null) {
      assertTrue(expectedResult);
      assertTrue(SatVerifier.verify(cnf, solution));
    } else {
      assertFalse(expectedResult);
    }
  }

  @Override
  protected String generateInput(PATestType type) {
    return null;
  }
}
