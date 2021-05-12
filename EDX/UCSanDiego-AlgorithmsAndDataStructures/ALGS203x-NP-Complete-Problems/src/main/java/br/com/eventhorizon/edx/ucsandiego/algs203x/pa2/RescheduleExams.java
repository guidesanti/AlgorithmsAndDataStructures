package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class RescheduleExams implements PA {

  private static UndirectedGraph graph;

  private static String initialColors;

  @Override
  public void finalSolution() {
    readInput();
    Cnf cnf = buildCnf(graph, initialColors);
    DirectedGraph implicationGgraph = buildImplicationGraph(cnf);
    DirectedGraphStronglyConnectedComponents scc = new DirectedGraphStronglyConnectedComponents(implicationGgraph);
    for (int i = 1; i <= cnf.numberOfVariables(); i++) {
      int vertex1 = literalToVertexNumber(i, cnf.numberOfVariables());
      int vertex2 = literalToVertexNumber(-i, cnf.numberOfVariables());
      if (scc.areStronglyConnected(vertex1, vertex2)) {
        System.out.println("Impossible");
        return;
      }
    }
    boolean[] assigned = new boolean[2 * cnf.numberOfVariables()];
    List<Integer> solution = new ArrayList<>();
    scc.components().forEach(component -> component.forEach(vertex -> {
      int literal = vertexNumberToLiteral(vertex, cnf.numberOfVariables());
      if (!assigned[Math.abs(literal) - 1]) {
        solution.add(literal);
        assigned[Math.abs(literal) - 1] = true;
      }
    }));
    solution.sort(Comparator.comparingInt(Math::abs));
    System.out.println(solutionToRgb(solution, graph.numberOfVertices()));
  }

  private static Cnf buildCnf(UndirectedGraph graph, String initialColors) {
    List<List<Integer>> clauses = new ArrayList<>();
    for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
      // Each vertex should not have the initial color
      int initialColor = colorToIndex(initialColors.charAt(vertex));
      List<Integer> clause = new ArrayList<>();
      clause.add(-literal(graph.numberOfVertices(), vertex, initialColor));
      clause.add(-literal(graph.numberOfVertices(), vertex, initialColor));
      clauses.add(clause);

      // Find remaining colors for the current vertex
      int[] remainingColors = new int[2];
      int index = 0;
      for (int color = 0; color < 3; color++) {
        if (color != initialColor) {
          remainingColors[index++] = color;
        }
      }

      // Each vertex should have at least one of the remaining colors
      clause = new ArrayList<>();
      clause.add(literal(graph.numberOfVertices(), vertex, remainingColors[0]));
      clause.add(literal(graph.numberOfVertices(), vertex, remainingColors[1]));
      clauses.add(clause);

      // Each vertex should have at most one of the remaining colors
      clause = new ArrayList<>();
      clause.add(-literal(graph.numberOfVertices(), vertex, remainingColors[0]));
      clause.add(-literal(graph.numberOfVertices(), vertex, remainingColors[1]));
      clauses.add(clause);

      // Each vertex should not have the same color as its respective adjacent vertices
      for (int adjVertex : graph.adjacencies(vertex)) {
        clause = new ArrayList<>();
        clause.add(-literal(graph.numberOfVertices(), vertex, remainingColors[0]));
        clause.add(-literal(graph.numberOfVertices(), adjVertex, remainingColors[0]));
        clauses.add(clause);

        clause = new ArrayList<>();
        clause.add(-literal(graph.numberOfVertices(), vertex, remainingColors[1]));
        clause.add(-literal(graph.numberOfVertices(), adjVertex, remainingColors[1]));
        clauses.add(clause);
      }
    }
    return new Cnf(3 * graph.numberOfVertices(), clauses);
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    initialColors = scanner.next();
    graph = new UndirectedGraph(numberOfVertices);
    for (int edge = 0; edge < numberOfEdges; edge++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1);
    }
  }

  private static String solutionToRgb(List<Integer> solution, int numberOfVertices) {
    char[] colors = new char[numberOfVertices];
    for (int literal : solution) {
      if (literal < 1) {
        continue;
      }
      int vertex;
      char color;
      literal--;
      if (literal < numberOfVertices) {
        vertex = literal;
        color = 'R';
      } else if (literal < 2 * numberOfVertices) {
        vertex = literal - numberOfVertices;
        color = 'G';
      } else {
        vertex = literal - (2 * numberOfVertices);
        color = 'B';
      }
      colors[vertex] = color;
    }
    return new String(colors);
  }

  private static int literal(int numberOfVertices, int vertex, int color) {
    return (numberOfVertices * color) + vertex + 1;
  }

  private static int colorToIndex(char color) {
    switch (color) {
      case 'R':
        return 0;
      case 'G':
        return 1;
      case 'B':
        return 2;
      default:
        throw new RuntimeException("Invalid color " + color);
    }
  }

  private static DirectedGraph buildImplicationGraph(Cnf cnf) {
    final int numberOfVariables = cnf.numberOfVariables();
    DirectedGraph graph = new DirectedGraph(2 * numberOfVariables);
    cnf.clauses().forEach(clause -> {
      int literal1 = clause.get(0);
      int literal2 = clause.get(1);
      graph.addEdge(literalToVertexNumber(-literal1, numberOfVariables), literalToVertexNumber(literal2, numberOfVariables));
      graph.addEdge(literalToVertexNumber(-literal2, numberOfVariables), literalToVertexNumber(literal1, numberOfVariables));
    });
    return graph;
  }

  private static int literalToVertexNumber(int literal, int numberOfVariables) {
    return literal > 0 ? literal - 1 : numberOfVariables - literal - 1;
  }

  private static int vertexNumberToLiteral(int vertex, int numberOfVariables) {
    return vertex < numberOfVariables ? vertex + 1 : numberOfVariables - vertex - 1;
  }

  private static class Cnf {

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

    public int maxClauseSize() {
      return clauses.isEmpty() ? 0 : clauses.stream().map(List::size).reduce(Math::max).get();
    }

    @Override
    public String toString() {
      return "Cnf{" +
          "numberOfVariables=" + numberOfVariables +
          ", numberOfClauses=" + clauses.size() +
          ", clauses=" + clauses +
          '}';
    }
  }

  private static class UndirectedGraph {

    protected final int numberOfVertices;

    protected int numberOfEdges;

    protected final List<Integer>[] adjacencies;

    protected int maxDegree;

    public UndirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new ArrayList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new ArrayList<>();
      }
    }

    public void addEdge(int vertex1, int vertex2) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[vertex1].add(vertex2);
      adjacencies[vertex2].add(vertex1);
      maxDegree = Math.max(maxDegree, Math.max(adjacencies[vertex1].size(), adjacencies[vertex2].size()));
      numberOfEdges++;
    }

    public int numberOfVertices() {
      return numberOfVertices;
    }

    public int numberOfEdges() {
      return numberOfEdges;
    }

    public int degree(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex].size();
    }

    public int maxDegree() {
      return maxDegree;
    }

    public List<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }
  }

  private static class DirectedGraph {

    private final int numberOfVertices;

    private int numberOfEdges;

    private final List<Integer>[] adjacencies;

    public DirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new ArrayList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new ArrayList<>();
      }
    }

    public void addEdge(int fromVertex, int toVertex) {
      if (fromVertex < 0 || fromVertex >= numberOfVertices ||
          toVertex < 0 || toVertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[fromVertex].add(toVertex);
      numberOfEdges++;
    }

    public int numberOfVertices() {
      return numberOfVertices;
    }

    public int numberOfEdges() {
      return numberOfEdges;
    }

    public int outDegree(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex].size();
    }

    public List<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }

    public DirectedGraph reverse() {
      DirectedGraph graph = new DirectedGraph(numberOfVertices);
      for (int i = 0; i < numberOfVertices; i++) {
        List<Integer> adjacencies = this.adjacencies[i];
        for (int j = 0; j < adjacencies.size(); j++) {
          graph.addEdge(adjacencies.get(j), i);
        }
      }
      return graph;
    }
  }

  public static class DirectedGraphStronglyConnectedComponents {

    private final List<List<Integer>> components;

    private final boolean[] marked;

    private final int[] id;

    private int count;

    public DirectedGraphStronglyConnectedComponents(DirectedGraph graph) {
      components = new ArrayList<>();
      marked = new boolean[graph.numberOfVertices()];
      id = new int[graph.numberOfVertices()];
      processGraph(graph);
    }

    public boolean areStronglyConnected(int vertex1, int vertex2) {
      return id[vertex1] == id[vertex2];
    }

    public List<List<Integer>> components() {
      return components;
    }

    private void processGraph(DirectedGraph graph) {
      Iterable<Integer> order = depthFirstReversePostorder(graph.reverse());
      for (int vertex : order) {
        if (!marked[vertex]) {
          depthFirstPreorder(graph, vertex);
          count++;
        }
      }
    }

    private void depthFirstPreorder(DirectedGraph graph, int sourceVertex) {
      List<Integer> component = new ArrayList<>();
      Stack<Integer> stack = new Stack<>();
      stack.push(sourceVertex);
      marked[sourceVertex] = true;
      while (!stack.isEmpty()) {
        Integer vertex = stack.pop();
        id[vertex] = count;
        component.add(vertex);
        List<Integer> adjVertices = graph.adjacencies(vertex);
        adjVertices.forEach(adjVertex -> {
          if (!marked[adjVertex]) {
            stack.push(adjVertex);
            marked[adjVertex] = true;
          }
        });
      }
      components.add(component);
    }

    private Iterable<Integer> depthFirstReversePostorder(DirectedGraph graph) {
      boolean[] marked = new boolean[graph.numberOfVertices()];
      LinkedList<Integer> order = new LinkedList<>();
      Stack<Integer> stack = new Stack<>();
      for (int i = 0; i < graph.numberOfVertices(); i++) {
        if (!marked[i]) {
          stack.push(i);
          marked[i] = true;
          while (!stack.isEmpty()) {
            while (true) {
              Integer vertex = stack.peek();
              List<Integer> adjVertices = graph.adjacencies(vertex);
              boolean stop = true;
              for (int adjVertex : adjVertices) {
                if (!marked[adjVertex]) {
                  stack.push(adjVertex);
                  marked[adjVertex] = true;
                  stop = false;
                  break;
                }
              }
              if (stop) {
                break;
              }
            }
            order.addFirst(stack.pop());
          }
        }
      }
      return order;
    }
  }
}
