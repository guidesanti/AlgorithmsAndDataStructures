package br.com.eventhorizon.sat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DpllSatSolver implements SatSolverAlgorithm {

  @Override
  public List<Integer> solve(Cnf cnf) {
    List<Clause> clauses = new ArrayList<>();
    cnf.clauses().forEach(clause -> clauses.add(new Clause(clause)));
    return solve1(clauses, new HashSet<>());
  }

  private List<Integer> solve1(List<Clause> clauses, Set<Integer> candidateSolution) {
    if (clauses.stream().allMatch(clause -> clause.satisfied)) {
      return new ArrayList<>(candidateSolution);
    }

    if (clauses.stream().anyMatch(clause -> !clause.satisfied && clause.unassignedLiterals.isEmpty())) {
      return null;
    }

    Set<Integer> unassignedLiterals = findUnassignedLiterals(clauses);
    for(Integer literal : unassignedLiterals) {
      forward(clauses, literal);
      if (candidateSolution.contains(literal)) {
        continue;
      }
      Set<Integer> newCandidateSolution = new HashSet<>(candidateSolution);
      newCandidateSolution.add(literal);
      List<Integer> solution = solve1(clauses, newCandidateSolution);
      if (solution != null) {
        return solution;
      }

      backward(clauses, literal);
    }

    return null;
  }

  private List<Integer> solve2(List<Clause> clauses, List<Integer> candidateSolution) {
    if (clauses.stream().allMatch(clause -> clause.satisfied)) {
      return candidateSolution;
    }

    if (clauses.stream().anyMatch(clause -> !clause.satisfied && clause.unassignedLiterals.isEmpty())) {
      return null;
    }

    if (clauses.stream().noneMatch(clause -> !clause.satisfied && !clause.unassignedLiterals.isEmpty())) {
      return null;
    }

    List<Integer> newCandidateSolution = new ArrayList<>();

    // Process unit clauses
    List<Integer> unitClauses = findUnitClauses(clauses);
    while (!unitClauses.isEmpty()) {
      for (Integer unit : unitClauses) {
        if (unitClauses.contains(-unit)) {
          for(Integer literal : newCandidateSolution) {
            backward(clauses, literal);
          }
          return null;
        }
        newCandidateSolution.add(unit);
        forward(clauses, unit);
      }
      unitClauses.removeAll(newCandidateSolution);
      unitClauses.addAll(findUnitClauses(clauses));
    }

    // Process all remaining clauses
    Set<Integer> unassignedLiterals = findUnassignedLiterals(clauses);
    for(Integer literal : unassignedLiterals) {
      forward(clauses, literal);

      List<Integer> deeperAssignedLiterals = new ArrayList<>();
      deeperAssignedLiterals.addAll(candidateSolution);
      deeperAssignedLiterals.addAll(newCandidateSolution);
      deeperAssignedLiterals.add(literal);

      List<Integer> solution = solve2(clauses, deeperAssignedLiterals);
      if (solution != null) {
        return solution;
      }

      backward(clauses, literal);
    }

    if (clauses.stream().allMatch(clause -> clause.satisfied)) {
      List<Integer> deeperAssignedLiterals = new ArrayList<>();
      deeperAssignedLiterals.addAll(candidateSolution);
      deeperAssignedLiterals.addAll(newCandidateSolution);
      return deeperAssignedLiterals;
    }

    for(Integer literal : newCandidateSolution) {
      backward(clauses, literal);
    }

    return null;
  }

  private List<Integer> findUnitClauses(List<Clause> clauses) {
    return clauses.stream()
        .filter(Clause::isUnitClause)
        .map(clause -> clause.unassignedLiterals.get(0))
        .collect(Collectors.toList());
  }

  private Set<Integer> findUnassignedLiterals(List<Clause> clauses) {
    return clauses.stream()
        .filter(clause -> !clause.satisfied)
        .flatMap(clause -> clause.unassignedLiterals.stream())
        .collect(Collectors.toSet());
  }

  private void forward(List<Clause> clauses, Integer literal) {
    clauses.stream().filter(clause -> !clause.satisfied).forEach(clause -> {
      if (clause.unassignedLiterals.contains(literal)) {
        clause.satisfied = true;
      } else if (clause.unassignedLiterals.contains(-literal)) {
        clause.unassignedLiterals.remove((Integer) (-literal));
        clause.deadLiterals.add(-literal);
      }
    });
  }

  private void backward(List<Clause> clauses, Integer literal) {
    for(Clause clause : clauses) {
      if(clause.satisfied && clause.unassignedLiterals.contains(literal)) {
        clause.satisfied = false;
      }
      if(clause.deadLiterals.contains(-literal)) {
        clause.deadLiterals.remove((Integer) (-literal));
        clause.unassignedLiterals.add(-literal);
      }
    }
  }

  private static class Clause {

    private final List<Integer> unassignedLiterals = new ArrayList<>();

    private final List<Integer> deadLiterals = new ArrayList<>();

    private boolean satisfied = false;

    private Clause(List<Integer> clause) {
      unassignedLiterals.addAll(clause);
    }

    boolean isUnitClause() {
      return !satisfied && unassignedLiterals.size() == 1;
    }

    @Override
    public String toString() {
      return "Clause{"
          + "satisfied=" + satisfied +
          ",unassignedLiterals=" + unassignedLiterals +
          ", deadLiterals=" + deadLiterals + '}';
    }
  }
}
