package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class CleaningTheApartment implements PA {

  private static int numberOfVertices;

  private static boolean[][] adjacencyMatrix;

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    adjacencyMatrix = new boolean[numberOfVertices + 1][numberOfVertices + 1];
    for (int i = 0; i < numberOfEdges; i++) {
      int vertex1 = scanner.nextInt();
      int vertex2 = scanner.nextInt();
      adjacencyMatrix[vertex1][vertex2] = true;
      adjacencyMatrix[vertex2][vertex1] = true;
    }

    List<List<Integer>> clauses = new ArrayList<>();

    for (int i = 1; i <= numberOfVertices; i++) {
      // Ensure the vertex is in at least in one path position
      // i = vertex
      // j = position
      List<Integer> clause1 = new ArrayList<>();
      for (int j = 1; j <= numberOfVertices; j++) {
        clause1.add(literal(i, j));
      }
      clauses.add(clause1);

      // Ensure the vertex is in at most one path position
      // i = vertex
      // j = position j
      // k = position k
      for (int j = 1; j <= numberOfVertices; j++) {
        for (int k = j + 1; k <= numberOfVertices; k++) {
          List<Integer> clause2 = new ArrayList<>();
          clause2.add(-(literal(i, j)));
          clause2.add(-literal(i, k));
          clauses.add(clause2);
        }
      }

      // Ensure each path position is occupied by at least one vertex
      // i = position
      // j = vertex
      List<Integer> clause3 = new ArrayList<>();
      for (int j = 1; j <= numberOfVertices; j++) {
        clause3.add(literal(j, i));
      }
      clauses.add(clause3);

      // Ensure each path position is occupied by at most one vertex
      // i = position
      // j = vertex j
      // k = vertex k
      for (int j = 1; j <= numberOfVertices; j++) {
        for (int k = j + 1; k <= numberOfVertices; k++) {
          List<Integer> clause4 = new ArrayList<>();
          clause4.add(-(literal(j, i)));
          clause4.add(-literal(k, i));
          clauses.add(clause4);
        }
      }

      // Ensure two successive vertices on a path are connected by an edge
      // i = vertex 1
      // j = vertex 2
      // k = position
      for (int j = i + 1; j <= numberOfVertices; j++) {
        if (!connected(i, j)) {
          for (int k = 1; k < numberOfVertices; k++) {
            List<Integer> clause5 = new ArrayList<>();
            clause5.add(-literal(i, k));
            clause5.add(-literal(j, k + 1));
            clauses.add(clause5);

            clause5 = new ArrayList<>();
            clause5.add(-literal(j, k));
            clause5.add(-literal(i, k + 1));
            clauses.add(clause5);
          }
        }
      }
    }

    output(numberOfVertices * numberOfVertices, clauses);
  }

  private static boolean connected(int vertex1, int vertex2) {
    return adjacencyMatrix[vertex1][vertex2];
  }

  // vertex = vertex number (1 to number of vertices)
  // pathPosition = path position (1 to number of path positions, that is equals to the number of vertices)
  private static int literal(int vertex, int pathPosition) {
    return vertex + ((pathPosition - 1) * numberOfVertices);
  }

  private static void output(int numberOfVariables, List<List<Integer>> clauses) {
    System.out.println(clauses.size() + " " + numberOfVariables);
    clauses.forEach(clause -> {
      clause.forEach(literal -> System.out.print(literal + " "));
      System.out.println(0);
    });
  }
}
