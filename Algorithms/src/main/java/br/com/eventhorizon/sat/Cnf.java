package br.com.eventhorizon.sat;

import java.util.List;

public class Cnf {

  private final int numberOfVariables;

  private final List<List<Integer>> clauses;

  public Cnf(int numberOfVariables, List<List<Integer>> clauses) {
    this.numberOfVariables = numberOfVariables;
    this.clauses = clauses;
  }

  public int numberOfVariables() {
    return numberOfVariables;
  }

  public int numberOfClauses() {
    return clauses.size();
  }

  public List<List<Integer>> clauses() {
    return clauses;
  }

  @Override
  public String toString() {
    return "Cnf{" +
        "numberOfVariables=" + numberOfVariables +
        "numberOfClauses=" + clauses.size() +
        ", clauses=" + clauses +
        '}';
  }
}
