package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.datastructures.graphs.GraphUtils;
import br.com.eventhorizon.common.datastructures.graphs.UndirectedGraph;
import br.com.eventhorizon.common.datastructures.sets.ArraySet;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaxIndependentSetInTreeTest {

  @Test
  public void test() {
    UndirectedGraph graph = new UndirectedGraph(4);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(0, 3);
    ArraySet<Integer> set1 = new ArraySet<>();
    set1.add(1);
    set1.add(2);
    set1.add(3);
    MaxIndependentSetInTree maxIndependentSetInTree = new MaxIndependentSetInTree(graph);
    assertEquals(3, maxIndependentSetInTree.maxIndependentSetSize());
    maxIndependentSetInTree.maxIndependentSet().forEach(vertex -> assertTrue(set1.contains(vertex)));
    assertTrue(GraphUtils.isIndependentSet(graph, StreamSupport
        .stream(maxIndependentSetInTree.maxIndependentSet().spliterator(), false)
        .collect(Collectors.toList())));

    graph = new UndirectedGraph(4);
    graph.addEdge(1, 3);
    graph.addEdge(3, 2);
    graph.addEdge(0, 3);
    ArraySet<Integer> set2 = new ArraySet<>();
    set2.add(0);
    set2.add(1);
    set2.add(2);
    maxIndependentSetInTree = new MaxIndependentSetInTree(graph);
    assertEquals(3, maxIndependentSetInTree.maxIndependentSetSize());
    maxIndependentSetInTree.maxIndependentSet().forEach(vertex -> assertTrue(set2.contains(vertex)));
    assertTrue(GraphUtils.isIndependentSet(graph, StreamSupport
        .stream(maxIndependentSetInTree.maxIndependentSet().spliterator(), false)
        .collect(Collectors.toList())));
  }
}
