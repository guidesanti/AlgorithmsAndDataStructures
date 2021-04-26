package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.datastructures.sets.ArraySet;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IndexedHeapPriorityQueueTest {

  @Test
  public void testMinIndexedHeapPriorityQueue() {
    Integer[] keys = generateData();
    ArraySet<Integer> keySet = new ArraySet<>(keys);
    assertEquals(keys.length, keySet.size());

    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, keys.length);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    assertThrows(IndexOutOfBoundsException.class, () -> queue.add(-1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> queue.add(keys.length, 1));
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    // Add, contains and peek
    int min = Integer.MAX_VALUE;
    int minIndex = 0;
    for (int index = 0; index < keys.length; index++) {
      if (keys[index] < min) {
        min = keys[index];
        minIndex = index;
      }
      queue.add(index, keys[index]);
      int finalIndex = index;
      assertThrows(IllegalArgumentException.class, () -> queue.add(finalIndex, 1));
      assertFalse(queue.isEmpty());
      assertEquals(index + 1, queue.size());
      assertTrue(queue.contains(index));
      assertTrue(queue.contains(keys[index]));
      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertNotNull(node);
      assertEquals(minIndex, node.index());
      assertEquals(keys[minIndex], node.key());
    }

    // Replace, contains and peek
    assertThrows(IndexOutOfBoundsException.class, () -> queue.replace(-1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> queue.replace(keys.length, 1));
    int numberOfReplaces = Utils.getRandomInteger(1, keys.length / 2);
    while (numberOfReplaces > 0) {
      numberOfReplaces--;
      int index = Utils.getRandomInteger(0, keys.length - 1);
      Integer key = Utils.getRandomInteger(-1000000, 1000000);
      if (keySet.contains(key)) {
        continue;
      }
      keySet.remove(keys[index]);
      keySet.add(key);
      keys[index] = key;
      queue.replace(index, key);
      assertFalse(queue.isEmpty());
      assertEquals(keys.length, queue.size());
      assertTrue(queue.contains(index));
      assertTrue(queue.contains(key));
      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertNotNull(node);
      minIndex = findMin(keys);
      assertEquals(minIndex, node.index());
      assertEquals(keys[minIndex], node.key());
    }
    assertEquals(keys.length, keySet.size());

    // Poll, contains and peek
    int expectedQueueSize = keys.length;
    while (expectedQueueSize > 0) {
      minIndex = findMin(keys);
      Integer minKey = keys[minIndex];

      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertEquals(minIndex, node.index());
      assertEquals(minKey, node.key());
      assertEquals(expectedQueueSize, queue.size());

      node = queue.poll();
      assertEquals(minIndex, node.index());
      assertEquals(minKey, node.key());
      expectedQueueSize--;
      assertEquals(expectedQueueSize, queue.size());

      keys[minIndex] = Integer.MAX_VALUE;
    }
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void testMinIndexedHeapPriorityQueueDuplicateKeys() {
    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, 10);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    queue.add(0, 10);
    assertEquals(0, queue.peek().index());
    assertEquals(10, queue.peek().key());
    assertTrue(queue.contains(0));
    assertTrue(queue.contains((Integer)10));

    queue.add(1, 9);
    assertEquals(1, queue.peek().index());
    assertEquals(9, queue.peek().key());
    assertTrue(queue.contains(1));
    assertTrue(queue.contains((Integer)9));

    queue.add(2, 8);
    assertEquals(2, queue.peek().index());
    assertEquals(8, queue.peek().key());
    assertTrue(queue.contains(2));
    assertTrue(queue.contains((Integer)8));

    queue.add(3, 7);
    assertEquals(3, queue.peek().index());
    assertEquals(7, queue.peek().key());
    assertTrue(queue.contains(3));
    assertTrue(queue.contains((Integer)7));

    queue.add(4, 6);
    assertEquals(4, queue.peek().index());
    assertEquals(6, queue.peek().key());
    assertTrue(queue.contains(4));
    assertTrue(queue.contains((Integer)6));

    queue.add(5, 5);
    assertEquals(5, queue.peek().index());
    assertEquals(5, queue.peek().key());
    assertTrue(queue.contains(5));
    assertTrue(queue.contains((Integer)5));

    queue.add(6, 9);
    assertEquals(5, queue.peek().index());
    assertEquals(5, queue.peek().key());
    assertTrue(queue.contains(6));
    assertTrue(queue.contains((Integer)9));

    queue.add(7, 9);
    assertEquals(5, queue.peek().index());
    assertEquals(5, queue.peek().key());
    assertTrue(queue.contains(7));
    assertTrue(queue.contains((Integer)9));

    queue.add(8, 4);
    assertEquals(8, queue.peek().index());
    assertEquals(4, queue.peek().key());
    assertTrue(queue.contains(8));
    assertTrue(queue.contains((Integer)4));

    queue.add(9, 3);
    assertEquals(9, queue.peek().index());
    assertEquals(3, queue.peek().key());
    assertTrue(queue.contains(9));
    assertTrue(queue.contains((Integer)3));

    queue.replace(1, 1);
    assertEquals(1, queue.peek().index());
    assertEquals(1, queue.peek().key());

    queue.replace(6, 0);
    assertEquals(6, queue.peek().index());
    assertEquals(0, queue.peek().key());

    queue.replace(7, -1);
    assertEquals(7, queue.peek().index());
    assertEquals(-1, queue.peek().key());
  }

  @Test
  public void testMaxIndexedHeapPriorityQueue() {
    Integer[] keys = generateData();
    ArraySet<Integer> keySet = new ArraySet<>(keys);
    assertEquals(keys.length, keySet.size());

    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MAX, keys.length);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    assertThrows(IndexOutOfBoundsException.class, () -> queue.add(-1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> queue.add(keys.length, 1));
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    // Add, contains and peek
    int max = Integer.MIN_VALUE;
    int maxIndex = 0;
    for (int index = 0; index < keys.length; index++) {
      if (keys[index] > max) {
        max = keys[index];
        maxIndex = index;
      }
      queue.add(index, keys[index]);
      int finalIndex = index;
      assertThrows(IllegalArgumentException.class, () -> queue.add(finalIndex, 1));
      assertFalse(queue.isEmpty());
      assertEquals(index + 1, queue.size());
      assertTrue(queue.contains(index));
      assertTrue(queue.contains(keys[index]));
      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertNotNull(node);
      assertEquals(maxIndex, node.index());
      assertEquals(keys[maxIndex], node.key());
    }

    // Replace, contains and peek
    assertThrows(IndexOutOfBoundsException.class, () -> queue.replace(-1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> queue.replace(keys.length, 1));
    int numberOfReplaces = Utils.getRandomInteger(1, keys.length / 2);
    while (numberOfReplaces > 0) {
      numberOfReplaces--;
      int index = Utils.getRandomInteger(0, keys.length - 1);
      Integer key = Utils.getRandomInteger(-1000000, 1000000);
      if (keySet.contains(key)) {
        continue;
      }
      keySet.remove(keys[index]);
      keySet.add(key);
      keys[index] = key;
      queue.replace(index, key);
      assertFalse(queue.isEmpty());
      assertEquals(keys.length, queue.size());
      assertTrue(queue.contains(index));
      assertTrue(queue.contains(key));
      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertNotNull(node);
      maxIndex = findMax(keys);
      assertEquals(maxIndex, node.index());
      assertEquals(keys[maxIndex], node.key());
    }
    assertEquals(keys.length, keySet.size());

    // Poll, contains and peek
    int expectedQueueSize = keys.length;
    while (expectedQueueSize > 0) {
      maxIndex = findMax(keys);
      Integer maxKey = keys[maxIndex];

      IndexedHeapPriorityQueue<Integer>.Node node = queue.peek();
      assertEquals(maxIndex, node.index());
      assertEquals(maxKey, node.key());
      assertEquals(expectedQueueSize, queue.size());

      node = queue.poll();
      assertEquals(maxIndex, node.index());
      assertEquals(maxKey, node.key());
      expectedQueueSize--;
      assertEquals(expectedQueueSize, queue.size());

      keys[maxIndex] = Integer.MIN_VALUE;
    }
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void testReplace() {
    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, 10);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    queue.add(0, -10);
    assertEquals(0, queue.peek().index());
    assertEquals(-10, queue.peek().key());
    assertTrue(queue.contains(0));
    assertTrue(queue.contains((Integer)(-10)));

    queue.add(1, 10);
    assertEquals(0, queue.peek().index());
    assertEquals(-10, queue.peek().key());
    assertTrue(queue.contains(1));
    assertTrue(queue.contains((Integer)10));

    queue.replace(1, -20);
    assertEquals(1, queue.peek().index());
    assertEquals(-20, queue.peek().key());
    assertTrue(queue.contains(1));
    assertFalse(queue.contains((Integer)10));
    assertTrue(queue.contains((Integer)(-20)));

    assertThrows(NoSuchElementException.class, () -> queue.replace(2, 20));

    queue.replace(1, 20);
    assertEquals(0, queue.peek().index());
    assertEquals(-10, queue.peek().key());
    assertTrue(queue.contains(1));
    assertFalse(queue.contains((Integer)(-20)));
    assertTrue(queue.contains((Integer)20));
  }

  @Test
  public void testEmptyQueue() {
    IndexedHeapPriorityQueue<Integer> queue1 = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, 10);
    assertTrue(queue1.isEmpty());
    assertEquals(0, queue1.size());
    assertThrows(NoSuchElementException.class, queue1::peek);
    assertThrows(NoSuchElementException.class, queue1::poll);
    for (int i = 0; i < 10; i++) {
      assertFalse(queue1.contains(i));
      assertFalse(queue1.contains((Integer)i));
    }

    IndexedHeapPriorityQueue<Integer> queue2 = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MAX, 10);
    assertTrue(queue2.isEmpty());
    assertEquals(0, queue2.size());
    assertThrows(NoSuchElementException.class, queue2::peek);
    assertThrows(NoSuchElementException.class, queue2::poll);
    for (int i = 0; i < 10; i++) {
      assertFalse(queue2.contains(i));
      assertFalse(queue2.contains((Integer)i));
    }

    IndexedHeapPriorityQueue<Integer> queue3 = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, new Integer[0]);
    assertTrue(queue3.isEmpty());
    assertEquals(0, queue3.size());
    assertThrows(NoSuchElementException.class, queue3::peek);
    assertThrows(NoSuchElementException.class, queue3::poll);
    for (int i = 0; i < 10; i++) {
      assertFalse(queue3.contains(i));
      assertFalse(queue3.contains((Integer)i));
    }

    IndexedHeapPriorityQueue<Integer> queue4 = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MAX, new Integer[0]);
    assertTrue(queue4.isEmpty());
    assertEquals(0, queue4.size());
    assertThrows(NoSuchElementException.class, queue4::peek);
    assertThrows(NoSuchElementException.class, queue4::poll);
    for (int i = 0; i < 10; i++) {
      assertFalse(queue4.contains(i));
      assertFalse(queue4.contains((Integer)i));
    }
  }

  @Test
  public void testClear() {
    Integer[] keys = generateData();

    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, keys.length);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    for (int index = 0; index < keys.length; index++) {
      queue.add(index, keys[index]);
    }
    assertFalse(queue.isEmpty());
    assertEquals(keys.length, queue.size());
    queue.clear();
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());

    queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, keys);
    assertFalse(queue.isEmpty());
    assertEquals(keys.length, queue.size());
    queue.clear();
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  public void testToString() {
    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, 5);
    queue.add(0, 10);
    queue.add(1, -10);
    queue.add(4, 0);
    assertEquals("IndexedHeapPriorityQueue{size:3,keys:[Node{index:1,key:-10},Node{index:0,key:10},Node{index:4,key:0}]}", queue.toString());
  }

  private int findMin(Integer[] array) {
    int minIndex = 0;
    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[minIndex]) {
        minIndex = i;
      }
    }
    return minIndex;
  }

  private int findMax(Integer[] array) {
    int minIndex = 0;
    for (int i = 1; i < array.length; i++) {
      if (array[i] > array[minIndex]) {
        minIndex = i;
      }
    }
    return minIndex;
  }

  private Integer[] generateData() {
    Integer[] keys = Utils.getRandomIntegerArray(1000, 10000, -1000000, 1000000);
    ArraySet<Integer> set = new ArraySet<>(keys);
    Integer[] result = new Integer[set.size()];
    return set.toArray(result);
  }
}
