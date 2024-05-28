package br.com.eventhorizon.datastructures.queues;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class HeapPriorityQueueTest {

  @Test
  public void testMinHeapPriorityQueue() {
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MIN);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Add and peek
    int count = 0;
    int minValue = Integer.MAX_VALUE;
    for (int value : values) {
      count++;
      if (value < minValue) {
        minValue = value;
      }
      queue.add(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(minValue, queue.peek());
      assertTrue(queue.contains(value));
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      Integer oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Peek and poll
    int lastValue = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue >= lastValue);
      assertEquals(findAndRemoveMin(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testMaxHeapPriorityQueue() {
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MAX);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Add and peek
    int count = 0;
    int maxValue = Integer.MIN_VALUE;
    for (int value : values) {
      count++;
      if (value > maxValue) {
        maxValue = value;
      }
      queue.add(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(maxValue, queue.peek());
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      Integer oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Peek and poll
    int lastValue = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue <= lastValue);
      assertEquals(findAndRemoveMax(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testMinHeapPriorityQueueWitInitialCapacity() {
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MIN, 100);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Add and peek
    int count = 0;
    int minValue = Integer.MAX_VALUE;
    for (int value : values) {
      count++;
      if (value < minValue) {
        minValue = value;
      }
      queue.add(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(minValue, queue.peek());
      assertTrue(queue.contains(value));
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      Integer oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Peek and poll
    int lastValue = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue >= lastValue);
      assertEquals(findAndRemoveMin(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testMaxHeapPriorityQueueWithInitialCapacity() {
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MAX, 100);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Add and peek
    int count = 0;
    int maxValue = Integer.MIN_VALUE;
    for (int value : values) {
      count++;
      if (value > maxValue) {
        maxValue = value;
      }
      queue.add(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(maxValue, queue.peek());
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      Integer oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Peek and poll
    int lastValue = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue <= lastValue);
      assertEquals(findAndRemoveMax(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testMinHeapPriorityQueueFromArray() {
    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MIN, values);
    assertEquals(values.length, queue.size());
    assertFalse(queue.isEmpty());

    int count = values.length;
    int lastValue = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue >= lastValue);
      assertEquals(findAndRemoveMin(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testMaxHeapPriorityQueueFromArray() {
    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MAX, values);
    assertEquals(values.length, queue.size());
    assertFalse(queue.isEmpty());

    int count = values.length;
    int lastValue = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue <= lastValue);
      assertEquals(findAndRemoveMax(values), currValue);
      assertEquals(currValue, queue.poll());
      lastValue = currValue;
      count--;
      assertEquals(count, queue.size());
      if (count == 0) {
        assertTrue(queue.isEmpty());
      } else {
        assertFalse(queue.isEmpty());
      }
    }
  }

  @Test
  public void testClear() {
    HeapPriorityQueue<Integer>
        queue = new HeapPriorityQueue<>(HeapPriorityQueue.Type.MIN);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    for (int value : values) {
      queue.add(value);
    }
    assertEquals(values.length, queue.size());
    assertFalse(queue.isEmpty());
    queue.clear();
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::poll);
  }

  private int findAndRemoveMin(Integer[] array) {
    int min = Integer.MAX_VALUE;
    int minIndex = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] <= min) {
        min = array[i];
        minIndex = i;
      }
    }
    array[minIndex] = Integer.MAX_VALUE;
    return min;
  }

  private int findAndRemoveMax(Integer[] array) {
    int max = Integer.MIN_VALUE;
    int maxIndex = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] >= max) {
        max = array[i];
        maxIndex = i;
      }
    }
    array[maxIndex] = Integer.MIN_VALUE;
    return max;
  }
}
