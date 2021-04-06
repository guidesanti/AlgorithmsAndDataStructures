package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Stack;

/**
 * This graph processing class finds a connected component from a specified source vertex.
 * The processing algorithm uses the depth first search to traverse the graph starting from
 * the source vertex until it visits all the vertices that are connected to the source vertex.
 */
public class UndirectedGraphConnectedComponent {

  private final boolean[] marked;

  private int size;

  /**
   * Creates a connected component with all vertices in the connected component of the source vertex.
   *
   * @param graph The graph to be processed and to find the respective connected component
   * @param sourceVertex The source vertex
   */
  public UndirectedGraphConnectedComponent(UndirectedGraph graph, int sourceVertex) {
    if (graph == null) {
      throw new IllegalArgumentException("graph cannot be null");
    }
    if (sourceVertex < 0 || sourceVertex >= graph.numberOfVertices()) {
      throw new IllegalArgumentException("Invalid sourceVertex");
    }
    this.marked = new boolean[graph.numberOfVertices()];
    depthFirstSearch(graph, sourceVertex);
  }

  /**
   * Verifies if this connected component contains a specific vertex.
   *
   * @param vertex The vertex to be verified
   * @return True if the vertex belongs to this connected component, otherwise returns false.
   */
  public boolean contains(int vertex) {
    if (vertex < 0 || vertex >= marked.length) {
      throw new IllegalArgumentException("Invalid vertex");
    }
    return marked[vertex];
  }

  /**
   * Gets the number of vertices within this connected component.
   *
   * @return The number of vertices within this connected component
   */
  public int size() {
    return size;
  }

  private void depthFirstSearch(UndirectedGraph graph, int sourceVertex) {
    Stack<Integer> stack = new Stack<>();
    stack.push(sourceVertex);
    marked[sourceVertex] = true;
    size++;
    while (!stack.isEmpty()) {
      Integer vertex = stack.pop();
      LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
      for (int i = 0; i < adjVertices.size(); i++) {
        int adjVertex = adjVertices.get(i);
        if (!marked[adjVertex]) {
          stack.push(adjVertex);
          marked[adjVertex] = true;
          size++;
        }
      }
    }
  }
}
