package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayPriorityQueueTest {

  @Test
  public void testMinArrayPriorityQueue() {
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MIN);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Enqueue and peek
    int count = 0;
    int minValue = Integer.MAX_VALUE;
    for (int value : values) {
      count++;
      if (value < minValue) {
        minValue = value;
      }
      queue.enqueue(value);
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
      int oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Dequeue and peek
    int lastValue = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue >= lastValue);
      assertEquals(findAndRemoveMin(values), currValue);
      assertEquals(currValue, queue.dequeue());
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
  public void testMaxArrayPriorityQueue() {
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MAX);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Enqueue and peek
    int count = 0;
    int maxValue = Integer.MIN_VALUE;
    for (int value : values) {
      count++;
      if (value > maxValue) {
        maxValue = value;
      }
      queue.enqueue(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(maxValue, queue.peek());
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      int oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Dequeue and peek
    int lastValue = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue <= lastValue);
      assertEquals(findAndRemoveMax(values), currValue);
      assertEquals(currValue, queue.dequeue());
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
  public void testMinArrayPriorityQueueWitInitialCapacity() {
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MIN, 100);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Enqueue and peek
    int count = 0;
    int minValue = Integer.MAX_VALUE;
    for (int value : values) {
      count++;
      if (value < minValue) {
        minValue = value;
      }
      queue.enqueue(value);
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
      int oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Dequeue and peek
    int lastValue = Integer.MIN_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue >= lastValue);
      assertEquals(findAndRemoveMin(values), currValue);
      assertEquals(currValue, queue.dequeue());
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
  public void testMaxArrayPriorityQueueWithInitialCapacity() {
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MAX, 100);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    // Enqueue and peek
    int count = 0;
    int maxValue = Integer.MIN_VALUE;
    for (int value : values) {
      count++;
      if (value > maxValue) {
        maxValue = value;
      }
      queue.enqueue(value);
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      assertEquals(maxValue, queue.peek());
    }

    // Replace
    int n = Utils.getRandomInteger(100, 500);
    for (int i = 0; i < n; i++) {
      int currentSize = queue.size();
      int index = Utils.getRandomInteger(0, values.length - 1);
      int oldValue = values[index];
      values[index] = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      queue.replace(oldValue, values[index]);
      assertEquals(currentSize, queue.size());
      assertTrue(queue.contains(values[index]));
    }

    // Dequeue and peek
    int lastValue = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      assertEquals(count, queue.size());
      assertFalse(queue.isEmpty());
      int currValue = queue.peek();
      assertTrue(currValue <= lastValue);
      assertEquals(findAndRemoveMax(values), currValue);
      assertEquals(currValue, queue.dequeue());
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
  public void testMinArrayPriorityQueueFromArray() {
    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MIN, values);
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
      assertEquals(currValue, queue.dequeue());
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
  public void testMaxArrayPriorityQueueFromArray() {
    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MAX, values);
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
      assertEquals(currValue, queue.dequeue());
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
    ArrayPriorityQueue<Integer> queue = new ArrayPriorityQueue<>(ArrayPriorityQueue.Type.MIN);
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);

    Integer[] values = Utils.getRandomIntegerArray(100, 10000, Integer.MIN_VALUE, Integer.MAX_VALUE);

    for (int value : values) {
      queue.enqueue(value);
    }
    assertEquals(values.length, queue.size());
    assertFalse(queue.isEmpty());
    queue.clear();
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);
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
