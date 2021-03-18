package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayHeapTest {

  // ---------------------------------------
  // Min heap tests
  // ---------------------------------------

  // TODO

  // ---------------------------------------
  // Max heap tests
  // ---------------------------------------

  @Test
  public void testAdd() {
    for (int i = 0; i < 10; i++) {
      ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX);
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
      assertArrayEquals(new Object[0], heap.toArray());

      int n = Utils.getRandomInteger(100, 10000);
      for (int j = 0; j < n; j++) {
        assertEquals(j, heap.add(Utils.getRandomLong(0, Long.MAX_VALUE)));
        assertFalse(heap.isEmpty());
        assertEquals(j + 1, heap.size());
        assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
      }
      assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
    }
  }

  @Test
  public void testAddWithInitialCapacity() {
    for (int i = 0; i < 10; i++) {
      ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX,1024);
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
      assertArrayEquals(new Object[0], heap.toArray());

      int n = Utils.getRandomInteger(100, 10000);
      for (int j = 0; j < n; j++) {
        heap.add(Utils.getRandomLong(0, Long.MAX_VALUE));
        assertFalse(heap.isEmpty());
        assertEquals(j + 1, heap.size());
        assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
      }
      assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
    }
  }

  @Test
  public void testAddWithInitialArray() {
    for (int i = 0; i < 10; i++) {
      Long[] data = Utils.getRandomLongArray(100, 10000, 0, Long.MAX_VALUE);
      int initialSize = data.length;
      ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX, data);
      assertFalse(heap.isEmpty());
      assertEquals(data.length, heap.size());
      assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");

      int n = Utils.getRandomInteger(10, 100);
      for (int j = 0; j < n; j++) {
        heap.add(Utils.getRandomLong(0, Long.MAX_VALUE));
        assertFalse(heap.isEmpty());
        assertEquals(initialSize + j + 1, heap.size());
        assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
      }
      assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
    }
  }

  @Test
  public void testPeek() {
    ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX);
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new Object[0], heap.toArray());
    assertThrows(NoSuchElementException.class, heap::peek);

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertEquals(1, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertEquals(2, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(2, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(3);
    assertFalse(heap.isEmpty());
    assertEquals(4, heap.size());
    assertEquals(3, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(5, heap.size());
    assertEquals(3, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(10);
    assertFalse(heap.isEmpty());
    assertEquals(6, heap.size());
    assertEquals(10, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));
  }

  @Test
  public void testPoll() {
    ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX);
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new Object[0], heap.toArray());
    assertThrows(NoSuchElementException.class, heap::peek);

    heap.add(3);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertEquals(3, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertEquals(3, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(3, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(3, heap.poll());
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(5);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(5, heap.peek());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(5, heap.poll());
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(2, heap.poll());
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(1, heap.poll());
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertThrows(NoSuchElementException.class, heap::poll);
  }

  @Test
  public void testRemove() {
    for (int i = 0; i < 100; i++) {
      Long[] values = Utils.getSortedRandomLongArray(100, 1000, 0, Long.MAX_VALUE);
      ArrayHeap<Long> heap = new ArrayHeap<>(ArrayHeap.Type.MAX);
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
      assertArrayEquals(new Object[0], heap.toArray());
      for (int j = 0; j < values.length; j++) {
        assertEquals(j, heap.add(values[j]));
        assertEquals(values[j], heap.peek());
        assertTrue(Utils.isMaxHeap(heap.toArray()));
      }
      for (Long value : values) {
        assertTrue(heap.contains(value));
      }
      for (Long value : values) {
        assertEquals(value, heap.remove(value));
        assertTrue(Utils.isMaxHeap(heap.toArray()));
      }
      for (Long value : values) {
        assertFalse(heap.contains(value));
      }
    }
  }

  @Test
  public void testClear() {
    ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX);
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new Object[0], heap.toArray());

    int n = Utils.getRandomInteger(100, 10000);
    for (int j = 0; j < n; j++) {
      heap.add(Utils.getRandomLong(0, Long.MAX_VALUE));
      assertFalse(heap.isEmpty());
      assertEquals(j + 1, heap.size());
      assertTrue(Utils.isMaxHeap(heap.toArray()), "Not a max heap");
    }
    heap.clear();
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new Object[0], heap.toArray());
  }

  @Test
  public void testContains() {
    ArrayHeap heap = new ArrayHeap(ArrayHeap.Type.MAX);
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new Object[0], heap.toArray());
    assertThrows(NoSuchElementException.class, heap::peek);

    heap.add(10);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertFalse(heap.contains(9));
    assertTrue(heap.contains(10));
    assertFalse(heap.contains(11));
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(100);
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertFalse(heap.contains(9));
    assertTrue(heap.contains(10));
    assertFalse(heap.contains(11));
    assertTrue(heap.contains(100));
    assertFalse(heap.contains(200));
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(200);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertFalse(heap.contains(9));
    assertTrue(heap.contains(10));
    assertFalse(heap.contains(11));
    assertTrue(heap.contains(100));
    assertTrue(heap.contains(200));
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.clear();
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertFalse(heap.contains(9));
    assertFalse(heap.contains(10));
    assertFalse(heap.contains(11));
    assertFalse(heap.contains(100));
    assertFalse(heap.contains(200));
  }
}
