package br.com.eventhorizon.common.datastructures.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedUndirectedEdgeTest {

  @Test
  public void testWeightedUndirectedEdge() {
    WeightedUndirectedEdge edge1 = new WeightedUndirectedEdge(1, 2, 10.0);
    assertEquals(1, edge1.vertex1());
    assertEquals(2, edge1.vertex2());
    assertEquals(1, edge1.either());
    assertEquals(2, edge1.other(1));
    assertEquals(1, edge1.other(2));
    assertEquals(10.0, edge1.weight());
    assertThrows(IllegalArgumentException.class, () -> edge1.other(3));

    WeightedUndirectedEdge edge2 = new WeightedUndirectedEdge(2, 1, 10.0);
    assertEquals(2, edge2.vertex1());
    assertEquals(1, edge2.vertex2());
    assertEquals(2, edge2.either());
    assertEquals(2, edge2.other(1));
    assertEquals(1, edge2.other(2));
    assertEquals(10.0, edge2.weight());
    assertThrows(IllegalArgumentException.class, () -> edge2.other(3));

    WeightedUndirectedEdge edge3 = new WeightedUndirectedEdge(1, 2, 11.0);
    assertEquals(1, edge3.vertex1());
    assertEquals(2, edge3.vertex2());
    assertEquals(1, edge3.either());
    assertEquals(2, edge3.other(1));
    assertEquals(1, edge3.other(2));
    assertEquals(11.0, edge3.weight());
    assertThrows(IllegalArgumentException.class, () -> edge3.other(3));

    assertEquals(edge1.hashCode(), edge2.hashCode());
    assertNotEquals(edge1.hashCode(), edge3.hashCode());
    assertNotEquals(edge2.hashCode(), edge3.hashCode());

    assertNotEquals(edge1, null);
    assertNotEquals(edge1, new Object());
    assertEquals(edge2, edge1);
    assertEquals(edge1, edge2);
    assertNotEquals(edge1, edge3);
    assertNotEquals(edge3, edge1);
    assertNotEquals(edge2, edge3);
    assertNotEquals(edge3, edge2);

    assertEquals(0, edge1.compareTo(edge2));
    assertEquals(-1, edge1.compareTo(edge3));
    assertEquals(-1, edge2.compareTo(edge3));
    assertEquals(1, edge3.compareTo(edge1));
    assertEquals(1, edge3.compareTo(edge2));

    assertEquals("WeightedDirectEdge {(10.0) 1 - 2}", edge1.toString());
    assertEquals("WeightedDirectEdge {(10.0) 2 - 1}", edge2.toString());
    assertEquals("WeightedDirectEdge {(11.0) 1 - 2}", edge3.toString());
  }
}
