package br.com.eventhorizon.datastructures.queues;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

  private static final int NUMBER_OF_TESTS = 100;

  @Test
  public void testQueue() {
    Queue<Object> queue = new Queue<>();
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
    assertThrows(NoSuchElementException.class, queue::peek);
    assertThrows(NoSuchElementException.class, queue::dequeue);
  }

  @Test
  public void testEnqueuePeekAndDequeue() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Queue<Object> queue = new Queue<>();
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      int count = 0;
      for (int j = 0; j < objects.length; j++) {
        queue.enqueue(objects[j]);
        count++;
        assertFalse(queue.isEmpty());
        assertEquals(count, queue.size());
        assertEquals(objects[0], queue.peek());
        assertEquals(count, queue.size());
      }
      for (int j = 0; j < objects.length; j++) {
        Object peekedObject = queue.peek();
        assertEquals(objects[j], peekedObject);
        assertEquals(count, queue.size());
        Object dequeuedObject = queue.dequeue();
        assertEquals(objects[j], dequeuedObject);
        count--;
        assertEquals(count, queue.size());
      }
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
    }
  }

  @Test
  public void testClear() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Queue<Object> queue = new Queue<>();
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      for (Object object : objects) {
        queue.enqueue(object);
      }
      assertFalse(queue.isEmpty());
      assertEquals(objects.length, queue.size());
      queue.clear();
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
    }
  }

  @Test
  public void testIterator() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Queue<Object> queue = new Queue<>();
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      int count = 0;
      for (Object object : objects) {
        queue.enqueue(object);
        assertFalse(queue.isEmpty());
        assertEquals(objects[0], queue.peek());
        count++;
        assertEquals(count, queue.size());
      }
      count = 0;
      for (Object object : queue) {
        assertEquals(objects[count++], object);
      }
    }
  }

  @Test
  public void testToString() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Queue<Object> queue = new Queue<>();
      assertTrue(queue.isEmpty());
      assertEquals(0, queue.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      for (Object object : objects) {
        queue.enqueue(object);
      }
      String str = queue.toString();
      StringJoiner expectedStr = new StringJoiner(", ", "Queue {", "}");
      for (Object object : objects) {
        expectedStr.add("" + object);
      }
      assertEquals(expectedStr.toString(), str);
    }
  }
}
