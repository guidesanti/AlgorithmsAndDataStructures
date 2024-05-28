package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.disjointsets.RootedTreeDisjointSets;
import br.com.eventhorizon.datastructures.queues.HeapPriorityQueue;

public class WeightedUndirectedGraphKruskalMinimumSpanningTree {

  private final LinkedList<WeightedUndirectedEdge> minimumSpanningTree;

  private double minimumSpanningTreeWeight;

  public WeightedUndirectedGraphKruskalMinimumSpanningTree(WeightedUndirectedGraph graph) {
    minimumSpanningTree = new LinkedList<>();
    RootedTreeDisjointSets.Node[] disjointSets = new RootedTreeDisjointSets.Node[graph.numberOfVertices()];
    HeapPriorityQueue<WeightedUndirectedEdge> queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MIN, graph.numberOfVertices());
    graph.edges().forEach(queue::add);
    for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
      disjointSets[vertex] = RootedTreeDisjointSets.build(vertex);
    }
    while (!queue.isEmpty() && minimumSpanningTree.size() < graph.numberOfVertices() - 1) {
      WeightedUndirectedEdge edge = queue.poll();
      RootedTreeDisjointSets.Node node1 = RootedTreeDisjointSets.find(disjointSets[edge.vertex1()]);
      RootedTreeDisjointSets.Node node2 = RootedTreeDisjointSets.find(disjointSets[edge.vertex2()]);
      if (node1.equals(node2)) {
        continue;
      }
      RootedTreeDisjointSets.union(node1, node2);
      minimumSpanningTree.addLast(edge);
      minimumSpanningTreeWeight += edge.weight;
    }
  }

  public double weight() {
    return minimumSpanningTreeWeight;
  }

  public Iterable<WeightedUndirectedEdge> edges() {
    return minimumSpanningTree;
  }
}
