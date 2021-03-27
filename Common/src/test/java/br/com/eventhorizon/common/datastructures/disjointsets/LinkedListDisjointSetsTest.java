package br.com.eventhorizon.common.datastructures.disjointsets;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedListDisjointSetsTest {

  @Test
  public void testBuildAndFind() {
    LinkedListDisjointSets<Object> disjointSets = new LinkedListDisjointSets<>();
    assertThrows(IllegalArgumentException.class, () -> disjointSets.build(null));
    assertNull(disjointSets.find(null));
    List<LinkedListDisjointSets<Object>.Node> setRepresentatives = new ArrayList<>();
    Object[] objects = Utils.getRandomObjectArray(100, 1000);

    // Creating sets
    for (int i = 0; i < objects.length; i++) {
      LinkedListDisjointSets<Object>.Node setRepresentative = disjointSets.build(objects[i]);
      assertNotNull(setRepresentative);
      assertEquals(i + 1, disjointSets.count());
      LinkedListDisjointSets<Object>.Node setRepresentativeFound = disjointSets.find(objects[i]);
      assertEquals(setRepresentative, setRepresentativeFound);
      assertFalse(setRepresentatives.contains(setRepresentative));
      setRepresentatives.add(setRepresentative);
    }
  }

  @Test
  public void testUnionAndFind() {
    LinkedListDisjointSets<Integer> disjointSets = new LinkedListDisjointSets<>();
    LinkedListDisjointSets<Integer>.Node sr0 = disjointSets.build(0);
    LinkedListDisjointSets<Integer>.Node sr1 = disjointSets.build(1);
    LinkedListDisjointSets<Integer>.Node sr2 = disjointSets.build(2);
    LinkedListDisjointSets<Integer>.Node sr3 = disjointSets.build(3);
    LinkedListDisjointSets<Integer>.Node sr4 = disjointSets.build(4);
    LinkedListDisjointSets<Integer>.Node sr5 = disjointSets.build(5);
    LinkedListDisjointSets<Integer>.Node sr6 = disjointSets.build(6);
    LinkedListDisjointSets<Integer>.Node sr7 = disjointSets.build(7);
    LinkedListDisjointSets<Integer>.Node sr8 = disjointSets.build(8);
    LinkedListDisjointSets<Integer>.Node sr9 = disjointSets.build(9);

    assertEquals(10, disjointSets.count());
    for (int i = -100; i < 0; i++) {
      assertNull(disjointSets.find(i));
    }
    for (int i = 10; i < 100; i++) {
      assertNull(disjointSets.find(i));
    }

    // Union of 3 and 4
    sr3 = disjointSets.union(sr3, sr4);
    assertEquals(9, disjointSets.count());
    assertEquals(sr3, disjointSets.find(3));
    assertEquals(sr3, disjointSets.find(4));
    assertEquals(3, sr3.getObject());
    LinkedListDisjointSets<Integer>.Node finalSr = sr3;
    assertThrows(IllegalStateException.class, () -> disjointSets.union(finalSr, sr4));

    // Union of 3, 4 and 8
    sr3 = disjointSets.union(sr3, sr8);
    assertEquals(8, disjointSets.count());
    assertEquals(sr3, disjointSets.find(3));
    assertEquals(sr3, disjointSets.find(4));
    assertEquals(sr3, disjointSets.find(8));
    assertEquals(3, sr3.getObject());
    assertThrows(IllegalStateException.class, () -> disjointSets.union(finalSr, sr8));

    // Union of 5 and 9
    sr5 = disjointSets.union(sr5, sr9);
    assertEquals(7, disjointSets.count());
    assertEquals(sr5, disjointSets.find(5));
    assertEquals(sr5, disjointSets.find(9));
    assertEquals(5, sr5.getObject());
    LinkedListDisjointSets<Integer>.Node finalSr1 = sr5;
    assertThrows(IllegalStateException.class, () -> disjointSets.union(finalSr1, sr9));

    // Union of 5, 9 and 7
    sr5 = disjointSets.union(sr7, sr5);
    assertEquals(6, disjointSets.count());
    assertEquals(sr5, disjointSets.find(5));
    assertEquals(sr5, disjointSets.find(9));
    assertEquals(sr5, disjointSets.find(7));
    assertEquals(5, sr5.getObject());
    LinkedListDisjointSets<Integer>.Node finalSr2 = sr5;
    assertThrows(IllegalStateException.class, () -> disjointSets.union(finalSr2, sr7));

    // Union of 0, 1, 2 and 6
    sr0 = disjointSets.union(sr0, sr1);
    sr0 = disjointSets.union(sr0, sr2);
    sr0 = disjointSets.union(sr0, sr6);
    assertEquals(3, disjointSets.count());
    assertEquals(sr0, disjointSets.find(0));
    assertEquals(sr0, disjointSets.find(1));
    assertEquals(sr0, disjointSets.find(2));
    assertEquals(sr0, disjointSets.find(6));
    assertEquals(0, sr0.getObject());

    // Union of all remaining
    sr0 = disjointSets.union(sr0, sr3);
    assertEquals(2, disjointSets.count());
    assertEquals(sr0, disjointSets.find(0));
    assertEquals(sr0, disjointSets.find(1));
    assertEquals(sr0, disjointSets.find(2));
    assertEquals(sr0, disjointSets.find(3));
    assertEquals(sr0, disjointSets.find(4));
    assertEquals(sr0, disjointSets.find(6));
    assertEquals(sr0, disjointSets.find(8));
    assertEquals(0, sr0.getObject());
    sr0 = disjointSets.union(sr5, sr0);
    assertEquals(sr0, disjointSets.find(0));
    assertEquals(sr0, disjointSets.find(1));
    assertEquals(sr0, disjointSets.find(2));
    assertEquals(sr0, disjointSets.find(3));
    assertEquals(sr0, disjointSets.find(4));
    assertEquals(sr0, disjointSets.find(5));
    assertEquals(sr0, disjointSets.find(6));
    assertEquals(sr0, disjointSets.find(7));
    assertEquals(sr0, disjointSets.find(8));
    assertEquals(sr0, disjointSets.find(9));
    assertEquals(0, sr0.getObject());
  }
}
