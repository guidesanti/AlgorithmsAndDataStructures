package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class AssignFrequenciesToGsmNetwork implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    List<List<Integer>> clauses = new ArrayList<>();

    for (int edge = 0; edge < numberOfEdges; edge++) {
      int vertex1 = scanner.nextInt();
      int vertex2 = scanner.nextInt();

      List<Integer> clause = new ArrayList<>();
      clause.add(-vertex1);
      clause.add(-vertex2);
      clauses.add(clause);

      clause = new ArrayList<>();
      clause.add(-(vertex1 + numberOfVertices));
      clause.add(-(vertex2 + numberOfVertices));
      clauses.add(clause);

      clause = new ArrayList<>();
      clause.add(-(vertex1 + (2 * numberOfVertices)));
      clause.add(-(vertex2 + (2 * numberOfVertices)));
      clauses.add(clause);
    }

    for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
      List<Integer> clause = new ArrayList<>();
      clause.add(vertex);
      clause.add(vertex + numberOfVertices);
      clause.add(vertex + (2 * numberOfVertices));
      clauses.add(clause);

      clause = new ArrayList<>();
      clause.add(-vertex);
      clause.add(-(vertex + numberOfVertices));
      clauses.add(clause);

      clause = new ArrayList<>();
      clause.add(-vertex);
      clause.add(-(vertex + (2 * numberOfVertices)));
      clauses.add(clause);

      clause = new ArrayList<>();
      clause.add(-(vertex + numberOfVertices));
      clause.add(-(vertex + (2 * numberOfVertices)));
      clauses.add(clause);
    }

    System.out.println(clauses.size() + " " + (3 * numberOfVertices));
    clauses.forEach(clause -> {
      clause.forEach(literal -> System.out.print(literal + " "));
      System.out.println(0);
    });
  }
}
