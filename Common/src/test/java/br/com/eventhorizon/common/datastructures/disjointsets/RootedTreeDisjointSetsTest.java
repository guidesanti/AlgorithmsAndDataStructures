package br.com.eventhorizon.common.datastructures.disjointsets;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RootedTreeDisjointSetsTest {

  @Test
  public void testBuildAndFind() {
    assertThrows(IllegalArgumentException.class, () -> RootedTreeDisjointSets.build(null));
    assertNull(RootedTreeDisjointSets.find(null));
    List<RootedTreeDisjointSets.Node<Object>> setRepresentatives = new ArrayList<>();
    Object[] objects = Utils.getRandomObjectArray(100, 1000);

    for (Object object : objects) {
      RootedTreeDisjointSets.Node<Object> setRepresentative = RootedTreeDisjointSets.build(object);
      assertNotNull(setRepresentative);
      RootedTreeDisjointSets.Node<Object> setRepresentativeFound = RootedTreeDisjointSets.find(setRepresentative);
      assertEquals(setRepresentative, setRepresentativeFound);
      assertFalse(setRepresentatives.contains(setRepresentative));
      setRepresentatives.add(setRepresentative);
    }
  }

  @Test
  public void testUnionAndFind() {
    RootedTreeDisjointSets.Node<Integer> sr0 = RootedTreeDisjointSets.build(0);
    RootedTreeDisjointSets.Node<Integer> sr1 = RootedTreeDisjointSets.build(1);
    RootedTreeDisjointSets.Node<Integer> sr2 = RootedTreeDisjointSets.build(2);
    RootedTreeDisjointSets.Node<Integer> sr3 = RootedTreeDisjointSets.build(3);
    RootedTreeDisjointSets.Node<Integer> sr4 = RootedTreeDisjointSets.build(4);
    RootedTreeDisjointSets.Node<Integer> sr5 = RootedTreeDisjointSets.build(5);
    RootedTreeDisjointSets.Node<Integer> sr6 = RootedTreeDisjointSets.build(6);
    RootedTreeDisjointSets.Node<Integer> sr7 = RootedTreeDisjointSets.build(7);
    RootedTreeDisjointSets.Node<Integer> sr8 = RootedTreeDisjointSets.build(8);
    RootedTreeDisjointSets.Node<Integer> sr9 = RootedTreeDisjointSets.build(9);

    // Union of 3 and 4
    RootedTreeDisjointSets.Node<Integer> u1 = RootedTreeDisjointSets.union(sr3, sr4);
    assertEquals(sr3, u1);
    assertEquals(3, u1.getObject());
    assertEquals(u1, RootedTreeDisjointSets.find(sr3));
    assertEquals(u1, RootedTreeDisjointSets.find(sr4));
    assertEquals(u1, RootedTreeDisjointSets.union(sr3, sr4));
    assertEquals(sr3, sr3.parent);
    assertEquals(sr3, sr4.parent);

    // Union of 3, 4 and 8
    RootedTreeDisjointSets.Node<Integer> u2 = RootedTreeDisjointSets.union(sr3, sr8);
    assertEquals(sr3, u2);
    assertEquals(3, u2.getObject());
    assertEquals(u2, RootedTreeDisjointSets.find(sr3));
    assertEquals(u2, RootedTreeDisjointSets.find(sr4));
    assertEquals(u2, RootedTreeDisjointSets.find(sr8));
    assertEquals(u2, RootedTreeDisjointSets.union(sr3, sr4));
    assertEquals(u2, RootedTreeDisjointSets.union(sr4, sr8));
    assertEquals(sr3, sr3.parent);
    assertEquals(sr3, sr4.parent);
    assertEquals(sr3, sr8.parent);

    // Union of 5 and 9
    RootedTreeDisjointSets.Node<Integer> u3 = RootedTreeDisjointSets.union(sr9, sr5);
    assertEquals(sr9, u3);
    assertEquals(9, u3.getObject());
    assertEquals(u3, RootedTreeDisjointSets.find(sr5));
    assertEquals(u3, RootedTreeDisjointSets.find(sr9));
    assertEquals(u3, RootedTreeDisjointSets.union(sr5, sr9));
    assertEquals(sr9, sr5.parent);
    assertEquals(sr9, sr9.parent);

    // Union of 5, 9 and 7
    RootedTreeDisjointSets.Node<Integer> u4 = RootedTreeDisjointSets.union(sr7, sr5);
    assertEquals(sr9, u4);
    assertEquals(9, u4.getObject());
    assertEquals(u4, RootedTreeDisjointSets.find(sr5));
    assertEquals(u4, RootedTreeDisjointSets.find(sr9));
    assertEquals(u4, RootedTreeDisjointSets.find(sr7));
    assertEquals(u4, RootedTreeDisjointSets.union(sr5, sr7));
    assertEquals(u4, RootedTreeDisjointSets.union(sr7, sr9));
    assertEquals(sr9, sr5.parent);
    assertEquals(sr9, sr7.parent);
    assertEquals(sr9, sr9.parent);

    // Union of 0, 1, 2 and 6
    RootedTreeDisjointSets.union(sr0, sr1);
    RootedTreeDisjointSets.union(sr2, sr1);
    RootedTreeDisjointSets.Node<Integer> u5 = RootedTreeDisjointSets.union(sr1, sr6);
    assertEquals(sr0, u5);
    assertEquals(0, u5.getObject());
    assertEquals(u5, RootedTreeDisjointSets.find(sr0));
    assertEquals(u5, RootedTreeDisjointSets.find(sr1));
    assertEquals(u5, RootedTreeDisjointSets.find(sr2));
    assertEquals(u5, RootedTreeDisjointSets.find(sr6));
    assertEquals(u5, RootedTreeDisjointSets.union(sr0, sr1));
    assertEquals(u5, RootedTreeDisjointSets.union(sr2, sr1));
    assertEquals(u5, RootedTreeDisjointSets.union(sr2, sr6));
    assertEquals(sr0, sr0.parent);
    assertEquals(sr0, sr1.parent);
    assertEquals(sr0, sr2.parent);
    assertEquals(sr0, sr6.parent);

    // Union of all remaining
    RootedTreeDisjointSets.Node<Integer> u6 = RootedTreeDisjointSets.union(sr0, sr3);
    assertEquals(sr0, u6);
    assertEquals(0, u6.getObject());
    assertEquals(u6, RootedTreeDisjointSets.find(sr0));
    assertEquals(u6, RootedTreeDisjointSets.find(sr1));
    assertEquals(u6, RootedTreeDisjointSets.find(sr2));
    assertEquals(u6, RootedTreeDisjointSets.find(sr3));
    assertEquals(u6, RootedTreeDisjointSets.find(sr4));
    assertEquals(u6, RootedTreeDisjointSets.find(sr6));
    assertEquals(u6, RootedTreeDisjointSets.find(sr8));
    RootedTreeDisjointSets.Node<Integer> u7 = RootedTreeDisjointSets.union(sr5, sr4);
    assertEquals(sr0, u7);
    assertEquals(0, u7.getObject());
    assertEquals(sr0, sr0.parent);
    assertEquals(sr0, sr1.parent);
    assertEquals(sr0, sr2.parent);
    assertEquals(sr0, sr3.parent);
    assertEquals(sr0, sr4.parent);
    assertEquals(sr0, sr6.parent);
    assertEquals(sr0, sr8.parent);
    assertEquals(u7, RootedTreeDisjointSets.find(sr0));
    assertEquals(u7, RootedTreeDisjointSets.find(sr1));
    assertEquals(u7, RootedTreeDisjointSets.find(sr2));
    assertEquals(u7, RootedTreeDisjointSets.find(sr3));
    assertEquals(u7, RootedTreeDisjointSets.find(sr4));
    assertEquals(u7, RootedTreeDisjointSets.find(sr5));
    assertEquals(u7, RootedTreeDisjointSets.find(sr6));
    assertEquals(u7, RootedTreeDisjointSets.find(sr7));
    assertEquals(u7, RootedTreeDisjointSets.find(sr8));
    assertEquals(u7, RootedTreeDisjointSets.find(sr9));
    assertEquals(sr0, sr0.parent);
    assertEquals(sr0, sr1.parent);
    assertEquals(sr0, sr2.parent);
    assertEquals(sr0, sr3.parent);
    assertEquals(sr0, sr4.parent);
    assertEquals(sr0, sr5.parent);
    assertEquals(sr0, sr6.parent);
    assertEquals(sr0, sr7.parent);
    assertEquals(sr0, sr8.parent);
    assertEquals(sr0, sr9.parent);
  }
}
