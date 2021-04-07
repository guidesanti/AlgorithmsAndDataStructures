package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

  private static final int NUMBER_OF_TESTS = 100;

  @Test
  public void testStack() {
    Stack<Object> stack = new Stack<>();
    assertTrue(stack.isEmpty());
    assertEquals(0, stack.size());
    assertThrows(NoSuchElementException.class, stack::peek);
    assertThrows(NoSuchElementException.class, stack::pop);
  }

  @Test
  public void testPushPeekAndPop() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Stack<Object> stack = new Stack<>();
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      int count = 0;
      for (int j = 0; j < objects.length; j++) {
        stack.push(objects[j]);
        count++;
        assertFalse(stack.isEmpty());
        assertEquals(count, stack.size());
        assertEquals(objects[j], stack.peek());
        assertEquals(count, stack.size());
      }
      for (int j = 0; j < objects.length; j++) {
        Object peekedObject = stack.peek();
        assertEquals(objects[count - 1], peekedObject);
        assertEquals(count, stack.size());
        Object poppedObjected = stack.pop();
        assertEquals(objects[count - 1], poppedObjected);
        count--;
        assertEquals(count, stack.size());
      }
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
    }
  }

  @Test
  public void testClear() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Stack<Object> stack = new Stack<>();
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      for (Object object : objects) {
        stack.push(object);
      }
      assertFalse(stack.isEmpty());
      assertEquals(objects.length, stack.size());
      stack.clear();
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
    }
  }

  @Test
  public void testIterator() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Stack<Object> stack = new Stack<>();
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      int count = 0;
      for (int j = 0; j < objects.length; j++) {
        stack.push(objects[j]);
        count++;
        assertFalse(stack.isEmpty());
        assertEquals(count, stack.size());
        assertEquals(objects[j], stack.peek());
      }
      count = objects.length - 1;
      for (Object object : stack) {
        assertEquals(objects[count--], object);
      }
    }
  }

  @Test
  public void testToString() {
    for (int i = 0; i < NUMBER_OF_TESTS; i++) {
      Stack<Object> stack = new Stack<>();
      assertTrue(stack.isEmpty());
      assertEquals(0, stack.size());
      Object[] objects = Utils.getRandomObjectArray(100, 1000);
      for (Object object : objects) {
        stack.push(object);
      }
      String str = stack.toString();
      StringJoiner expectedStr = new StringJoiner(", ", "Stack {", "}");
      int j = objects.length - 1;
      while (j >= 0) {
        expectedStr.add("" + objects[j--]);
      }
      assertEquals(expectedStr.toString(), str);
    }
  }
}
