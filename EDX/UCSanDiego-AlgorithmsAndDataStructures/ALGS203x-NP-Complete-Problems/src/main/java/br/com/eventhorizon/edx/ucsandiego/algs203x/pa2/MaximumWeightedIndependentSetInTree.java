package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MaximumWeightedIndependentSetInTree implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    Graph graph = readGraph();
    System.out.println(findMaximumWeightedIndependentSet(graph));
  }

  private static Graph readGraph() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int[] weights = new int[numberOfVertices];
    for (int vertex = 0; vertex < numberOfVertices; vertex++) {
      weights[vertex] = scanner.nextInt();
    }
    Graph graph = new Graph(weights);
    for (int edge = 0; edge < numberOfVertices - 1; edge++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1);
    }
    return graph;
  }

  private static int findMaximumWeightedIndependentSet(Graph graph) {
    int[] weights = new int[graph.numberOfVertices];
    int weight = Integer.MIN_VALUE;
    Arrays.fill(weights, Integer.MAX_VALUE);
    for (int vertex : depthFirstPostorder(graph)) {
      if (graph.degree(vertex) == 1 && vertex > 0) {
        // Leaves have only one adjacency, its parent
        // And vertex 0 is the root, so we avoid treating it as a leaf
        weights[vertex] = graph.weight(vertex);
      } else {
        // Non leaves, when processed all their children have already been processed and parent not yet
        int weight1 = 0;
        int weight2 = graph.weight(vertex);
        for (int child : graph.adjacencies(vertex)) {
          // If not processed yet, it is not actually a child and instead the parent
          // So we just ignore it and continue
          if (weights[child] == Integer.MAX_VALUE) {
            continue;
          }
          weight1 += weights[child];
          for (int grandChild : graph.adjacencies(child)) {
            if (grandChild == vertex) {
              continue;
            }
            weight2 += weights[grandChild];
          }
        }
        weights[vertex] = Math.max(weight1, weight2);
      }
      weight = Math.max(weight, weights[vertex]);
    }
    return weight;
  }

  private static Iterable<Integer> depthFirstPostorder(Graph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices];
    List<Integer> order = new ArrayList<>(graph.numberOfVertices);
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < graph.numberOfVertices; i++) {
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
          order.add(stack.pop());
        }
      }
    }
    return order;
  }

  private static class Graph {

    final int numberOfVertices;

    final List[] adjacencies;

    final int[] weights;

    public Graph(int[] weights) {
      numberOfVertices = weights.length;
      adjacencies = new ArrayList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        adjacencies[i] = new ArrayList<>();
      }
      this.weights = weights;
    }

    public void addEdge(int vertex1, int vertex2) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[vertex1].add(vertex2);
      adjacencies[vertex2].add(vertex1);
    }

    public int degree(int vertex) {
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

    public int weight(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return weights[vertex];
    }
  }
}
