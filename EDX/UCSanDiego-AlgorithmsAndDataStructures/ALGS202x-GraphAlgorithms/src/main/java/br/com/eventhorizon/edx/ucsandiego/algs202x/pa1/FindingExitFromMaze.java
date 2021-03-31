package br.com.eventhorizon.edx.ucsandiego.algs202x.pa1;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Stack;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.NoSuchElementException;

public class FindingExitFromMaze implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    Graph graph = new Graph(numberOfVertices);
    for (int i = 0; i < numberOfEdges; i++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1);
    }
    int sourceVertex = scanner.nextInt() - 1;
    int destinationVertex = scanner.nextInt() - 1;
    GraphTraverser traverser = new GraphTraverser(graph, sourceVertex);
    while (traverser.hasNext()) {
      if (traverser.next() == destinationVertex) {
        break;
      }
    }
    System.out.println(traverser.visited[destinationVertex] ? 1 : 0);
  }

  private static class Graph {

    private final int numberOfVertices;

    private final LinkedList<Integer>[] adjacencies;

    public Graph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
    }

    public void addEdge(int vertex1, int vertex2) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[vertex1].addLast(vertex2);
      adjacencies[vertex2].addLast(vertex1);
    }
  }

  private static class GraphTraverser {

    private final FindingExitFromMaze.Graph graph;

    private final Stack<Integer> stack;

    private final boolean[] visited;

    public GraphTraverser(FindingExitFromMaze.Graph graph, int vertex) {
      this.graph = graph;
      this.stack = new Stack<>();
      this.stack.push(vertex);
      visited = new boolean[graph.numberOfVertices];
    }

    private Integer depthFirst() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException();
      }
      Integer vertex = stack.pop();
      if (!visited[vertex]) {
        LinkedList<Integer> adjVertices = graph.adjacencies[vertex];
        for (int i = 0; i < adjVertices.size(); i++) {
          int adjVertex = adjVertices.get(i);
          if (!visited[adjVertex]) {
            stack.push(adjVertex);
          }
        }
        visited[vertex] = true;
      }
      return vertex;
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public Integer next() {
      return depthFirst();
    }
  }
}
