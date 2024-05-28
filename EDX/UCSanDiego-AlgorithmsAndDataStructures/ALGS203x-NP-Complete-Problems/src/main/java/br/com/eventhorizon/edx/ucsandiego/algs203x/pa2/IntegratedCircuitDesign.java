package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import br.com.eventhorizon.sat.Cnf;

import java.util.*;

public class IntegratedCircuitDesign implements PA {

  @Override
  public void trivialSolution() {
    Cnf cnf = readCnf(new FastScanner(System.in));
    List<Integer> solution = naiveSolve(cnf);
    output(solution);
  }

  private List<Integer> naiveSolve(Cnf cnf) {
    List<Integer> solution = new ArrayList<>();
    for (int i = 0; i <= cnf.numberOfVariables(); i++) {
      solution.add(-i);
    }
    do {
      if (verify(cnf, solution)) {
        return solution;
      }
    } while (nextSolutionCandidate(solution));
    return null;
  }

  private boolean nextSolutionCandidate(List<Integer> solution) {
    int index = solution.size() - 1;
    while (index > 0 && solution.get(index) > 0) {
      solution.set(index, -solution.get(index));
      index--;
    }
    solution.set(index, -solution.get(index));
    return index > 0;
  }

  private static boolean verify(Cnf cnf, List<Integer> solution) {
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

  @Override
  public void finalSolution() {
    Cnf cnf = readCnf(new FastScanner(System.in));
    DirectedGraph graph = buildImplicationGraph(cnf);
    DirectedGraphStronglyConnectedComponents scc = new DirectedGraphStronglyConnectedComponents(graph);
    for (int i = 1; i <= cnf.numberOfVariables(); i++) {
      int vertex1 = literalToVertexNumber(i, cnf.numberOfVariables());
      int vertex2 = literalToVertexNumber(-i, cnf.numberOfVariables());
      if (scc.areStronglyConnected(vertex1, vertex2)) {
        output(null);
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
    output(solution);
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

  private static Cnf readCnf(FastScanner scanner) {
    int numberOfVariables = scanner.nextInt();
    int numberOfClauses = scanner.nextInt();
    List<List<Integer>> clauses = new ArrayList<>();
    for (int i = 0; i < numberOfClauses; i++) {
      List<Integer> clause = new ArrayList<>();
      for (int j = 0; j < 2; j++) {
        clause.add(scanner.nextInt());
      }
      clauses.add(clause);
    }
    return new Cnf(numberOfVariables, clauses);
  }

  private static void output(List<Integer> solution) {
    if (solution == null || solution.isEmpty()) {
      System.out.println("UNSATISFIABLE");
    } else {
      System.out.println("SATISFIABLE");
      solution.forEach(literal -> System.out.print(literal + " "));
      System.out.println();
    }
  }

  private static class DirectedGraph {

    private final int numberOfVertices;

    private int numberOfEdges;

    private final LinkedList<Integer>[] adjacencies;

    public DirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
    }

    public void addEdge(int fromVertex, int toVertex) {
      if (fromVertex < 0 || fromVertex >= numberOfVertices ||
          toVertex < 0 || toVertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[fromVertex].addLast(toVertex);
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

    public LinkedList<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }

    public DirectedGraph reverse() {
      DirectedGraph graph = new DirectedGraph(numberOfVertices);
      for (int i = 0; i < numberOfVertices; i++) {
        LinkedList<Integer> adjacencies = this.adjacencies[i];
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
        LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
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
              LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
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
