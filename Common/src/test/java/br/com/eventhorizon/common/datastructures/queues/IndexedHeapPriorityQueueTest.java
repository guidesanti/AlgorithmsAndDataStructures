package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.datastructures.sets.ArraySet;
import org.junit.jupiter.api.Test;

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
