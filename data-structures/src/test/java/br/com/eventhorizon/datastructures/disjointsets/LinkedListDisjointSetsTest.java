package br.com.eventhorizon.datastructures.disjointsets;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListDisjointSetsTest {

  @Test
  public void testBuildAndFind() {
    assertThrows(IllegalArgumentException.class, () -> LinkedListDisjointSets.build(null));
    assertNull(LinkedListDisjointSets.find(null));
    List<LinkedListDisjointSets.Node<Object>> setRepresentatives = new ArrayList<>();
    Object[] objects = Utils.getRandomObjectArray(100, 1000);

    for (Object object : objects) {
      LinkedListDisjointSets.Node<Object> setRepresentative = LinkedListDisjointSets.build(object);
      assertNotNull(setRepresentative);
      LinkedListDisjointSets.Node<Object> setRepresentativeFound = LinkedListDisjointSets.find(setRepresentative);
      assertEquals(setRepresentative, setRepresentativeFound);
      assertFalse(setRepresentatives.contains(setRepresentative));
      setRepresentatives.add(setRepresentative);
    }
  }

  @Test
  public void testUnionAndFind() {
    LinkedListDisjointSets.Node<Integer> sr0 = LinkedListDisjointSets.build(0);
    LinkedListDisjointSets.Node<Integer> sr1 = LinkedListDisjointSets.build(1);
    LinkedListDisjointSets.Node<Integer> sr2 = LinkedListDisjointSets.build(2);
    LinkedListDisjointSets.Node<Integer> sr3 = LinkedListDisjointSets.build(3);
    LinkedListDisjointSets.Node<Integer> sr4 = LinkedListDisjointSets.build(4);
    LinkedListDisjointSets.Node<Integer> sr5 = LinkedListDisjointSets.build(5);
    LinkedListDisjointSets.Node<Integer> sr6 = LinkedListDisjointSets.build(6);
    LinkedListDisjointSets.Node<Integer> sr7 = LinkedListDisjointSets.build(7);
    LinkedListDisjointSets.Node<Integer> sr8 = LinkedListDisjointSets.build(8);
    LinkedListDisjointSets.Node<Integer> sr9 = LinkedListDisjointSets.build(9);

    // Union of 3 and 4
    LinkedListDisjointSets.Node<Integer> u1 = LinkedListDisjointSets.union(sr3, sr4);
    assertEquals(sr3, u1);
    assertEquals(3, u1.getObject());
    assertEquals(u1, LinkedListDisjointSets.find(sr3));
    assertEquals(u1, LinkedListDisjointSets.find(sr4));
    assertEquals(u1, LinkedListDisjointSets.union(sr3, sr4));
    assertEquals(sr3.set, sr4.set);

    // Union of 3, 4 and 8
    LinkedListDisjointSets.Node<Integer> u2 = LinkedListDisjointSets.union(sr3, sr8);
    assertEquals(sr3, u2);
    assertEquals(3, u2.getObject());
    assertEquals(u2, LinkedListDisjointSets.find(sr3));
    assertEquals(u2, LinkedListDisjointSets.find(sr4));
    assertEquals(u2, LinkedListDisjointSets.find(sr8));
    assertEquals(u2, LinkedListDisjointSets.union(sr3, sr4));
    assertEquals(u2, LinkedListDisjointSets.union(sr4, sr8));
    assertEquals(sr3.set, sr4.set);
    assertEquals(sr3.set, sr8.set);

    // Union of 5 and 9
    LinkedListDisjointSets.Node<Integer> u3 = LinkedListDisjointSets.union(sr9, sr5);
    assertEquals(sr9, u3);
    assertEquals(9, u3.getObject());
    assertEquals(u3, LinkedListDisjointSets.find(sr5));
    assertEquals(u3, LinkedListDisjointSets.find(sr9));
    assertEquals(u3, LinkedListDisjointSets.union(sr5, sr9));
    assertEquals(sr5.set, sr9.set);

    // Union of 5, 9 and 7
    LinkedListDisjointSets.Node<Integer> u4 = LinkedListDisjointSets.union(sr7, sr5);
    assertEquals(sr9, u4);
    assertEquals(9, u4.getObject());
    assertEquals(u4, LinkedListDisjointSets.find(sr5));
    assertEquals(u4, LinkedListDisjointSets.find(sr9));
    assertEquals(u4, LinkedListDisjointSets.find(sr7));
    assertEquals(u4, LinkedListDisjointSets.union(sr5, sr7));
    assertEquals(u4, LinkedListDisjointSets.union(sr7, sr9));
    assertEquals(sr5.set, sr7.set);
    assertEquals(sr5.set, sr9.set);

    // Union of 0, 1, 2 and 6
    LinkedListDisjointSets.union(sr0, sr1);
    LinkedListDisjointSets.union(sr2, sr1);
    LinkedListDisjointSets.Node<Integer> u5 = LinkedListDisjointSets.union(sr1, sr6);
    assertEquals(sr0, u5);
    assertEquals(0, u5.getObject());
    assertEquals(u5, LinkedListDisjointSets.find(sr0));
    assertEquals(u5, LinkedListDisjointSets.find(sr1));
    assertEquals(u5, LinkedListDisjointSets.find(sr2));
    assertEquals(u5, LinkedListDisjointSets.find(sr6));
    assertEquals(u5, LinkedListDisjointSets.union(sr0, sr1));
    assertEquals(u5, LinkedListDisjointSets.union(sr2, sr1));
    assertEquals(u5, LinkedListDisjointSets.union(sr2, sr6));
    assertEquals(sr0.set, sr1.set);
    assertEquals(sr0.set, sr2.set);
    assertEquals(sr0.set, sr6.set);

    // Union of all remaining
    LinkedListDisjointSets.Node<Integer> u6 = LinkedListDisjointSets.union(sr0, sr3);
    assertEquals(sr0, u6);
    assertEquals(0, u6.getObject());
    assertEquals(u6, LinkedListDisjointSets.find(sr0));
    assertEquals(u6, LinkedListDisjointSets.find(sr1));
    assertEquals(u6, LinkedListDisjointSets.find(sr2));
    assertEquals(u6, LinkedListDisjointSets.find(sr3));
    assertEquals(u6, LinkedListDisjointSets.find(sr4));
    assertEquals(u6, LinkedListDisjointSets.find(sr6));
    assertEquals(u6, LinkedListDisjointSets.find(sr8));
    LinkedListDisjointSets.Node<Integer> u7 = LinkedListDisjointSets.union(sr5, sr4);
    assertEquals(sr0, u7);
    assertEquals(0, u7.getObject());
    assertEquals(sr0.set, sr1.set);
    assertEquals(sr0.set, sr2.set);
    assertEquals(sr0.set, sr3.set);
    assertEquals(sr0.set, sr4.set);
    assertEquals(sr0.set, sr6.set);
    assertEquals(sr0.set, sr8.set);
    assertEquals(u7, LinkedListDisjointSets.find(sr0));
    assertEquals(u7, LinkedListDisjointSets.find(sr1));
    assertEquals(u7, LinkedListDisjointSets.find(sr2));
    assertEquals(u7, LinkedListDisjointSets.find(sr3));
    assertEquals(u7, LinkedListDisjointSets.find(sr4));
    assertEquals(u7, LinkedListDisjointSets.find(sr5));
    assertEquals(u7, LinkedListDisjointSets.find(sr6));
    assertEquals(u7, LinkedListDisjointSets.find(sr7));
    assertEquals(u7, LinkedListDisjointSets.find(sr8));
    assertEquals(u7, LinkedListDisjointSets.find(sr9));
    assertEquals(sr0.set, sr1.set);
    assertEquals(sr0.set, sr2.set);
    assertEquals(sr0.set, sr3.set);
    assertEquals(sr0.set, sr4.set);
    assertEquals(sr0.set, sr5.set);
    assertEquals(sr0.set, sr6.set);
    assertEquals(sr0.set, sr7.set);
    assertEquals(sr0.set, sr8.set);
    assertEquals(sr0.set, sr9.set);
  }
}
