package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayMaxHeapTest {

  @Test
  public void testAdd() {
    for (int i = 0; i < 10; i++) {
      ArrayMaxHeap heap = new ArrayMaxHeap();
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
      assertArrayEquals(new long[0], heap.toArray());

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
  public void testAddWithInitialCapacity() {
    for (int i = 0; i < 10; i++) {
      ArrayMaxHeap heap = new ArrayMaxHeap(1024);
      assertTrue(heap.isEmpty());
      assertEquals(0, heap.size());
      assertArrayEquals(new long[0], heap.toArray());

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
      long[] data = Utils.getRandomLongArray(100, 10000, 0, Long.MAX_VALUE);
      int initialSize = data.length;
      ArrayMaxHeap heap = new ArrayMaxHeap(data);
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
  public void testGetMax() {
    ArrayMaxHeap heap = new ArrayMaxHeap();
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new long[0], heap.toArray());
    assertThrows(NoSuchElementException.class, heap::getMax);

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertEquals(1, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertEquals(2, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(2, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(3);
    assertFalse(heap.isEmpty());
    assertEquals(4, heap.size());
    assertEquals(3, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(5, heap.size());
    assertEquals(3, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(10);
    assertFalse(heap.isEmpty());
    assertEquals(6, heap.size());
    assertEquals(10, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));
  }

  @Test
  public void testRemoveMax() {
    ArrayMaxHeap heap = new ArrayMaxHeap();
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new long[0], heap.toArray());
    assertThrows(NoSuchElementException.class, heap::getMax);

    heap.add(3);
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertEquals(3, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(1);
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertEquals(3, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(2);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(3, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(3, heap.removeMax());
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    heap.add(5);
    assertFalse(heap.isEmpty());
    assertEquals(3, heap.size());
    assertEquals(5, heap.getMax());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(5, heap.removeMax());
    assertFalse(heap.isEmpty());
    assertEquals(2, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(2, heap.removeMax());
    assertFalse(heap.isEmpty());
    assertEquals(1, heap.size());
    assertTrue(Utils.isMaxHeap(heap.toArray()));

    assertEquals(1, heap.removeMax());
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertThrows(NoSuchElementException.class, heap::removeMax);
  }

  @Test
  public void testClear() {
    ArrayMaxHeap heap = new ArrayMaxHeap();
    assertTrue(heap.isEmpty());
    assertEquals(0, heap.size());
    assertArrayEquals(new long[0], heap.toArray());

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
    assertArrayEquals(new long[0], heap.toArray());
  }
}
