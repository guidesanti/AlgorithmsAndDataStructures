package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.datastructures.sets.ArraySet;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class IndexedHeapPriorityQueueTest {

  @Test
  public void testMinIndexedHeapPriorityQueue() {
    Integer[] keys = generateData();
    Integer[] sortedKeys = Arrays.copyOf(keys, keys.length);
    Arrays.sort(sortedKeys);
    IndexedHeapPriorityQueue<Integer> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, keys.length);
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    for (int index = 0; index < keys.length; index++) {
      queue.add(index, keys[index]);
      assertFalse(queue.isEmpty());
      assertEquals(index + 1, queue.size());
      assertTrue(queue.contains(index));
      assertTrue(queue.contains(keys[index]));
    }
  }

  private Integer[] generateData() {
    Integer[] keys = Utils.getRandomIntegerArray(1000, 10000, -1000000, 1000000);
    ArraySet<Integer> set = new ArraySet<>(keys);
    Integer[] result = new Integer[set.size()];
    return set.toArray(result);
  }
}
