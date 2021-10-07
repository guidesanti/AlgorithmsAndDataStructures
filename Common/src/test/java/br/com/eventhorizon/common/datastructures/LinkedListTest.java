package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

  private static final Integer[] VALUES = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

  private static final Integer[] EMPTY_LONG_ARRAY = new Integer[0];

  @Test
  public void testConstructors() {
    LinkedList<Integer> list = new LinkedList<>();
    assertNotNull(list);
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    list = new LinkedList<>(VALUES);
    assertNotNull(list);
    assertFalse(list.isEmpty());
    assertEquals(VALUES.length, list.size());
    verifyValues(VALUES, list);
  }

  @Test
  public void testAdd() {
    LinkedList<Integer> list = new LinkedList<>();
    for (int i = 0; i < 10; i++) {
      list.add(i, i);
      assertEquals(i + 1, list.size());
    }
    for (int i = 0; i < 10; i++) {
      assertEquals(i, list.get(i));
    }
    list.add(0, 11);
    list.add(5, 12);
    list.add(list.size(), 13);
    assertEquals(11, list.get(0));
    assertEquals(0, list.get(1));
    assertEquals(1, list.get(2));
    assertEquals(2, list.get(3));
    assertEquals(3, list.get(4));
    assertEquals(12, list.get(5));
    assertEquals(4, list.get(6));
    assertEquals(5, list.get(7));
    assertEquals(6, list.get(8));
    assertEquals(7, list.get(9));
    assertEquals(8, list.get(10));
    assertEquals(9, list.get(11));
    assertEquals(13, list.get(12));
  }

  @Test
  public void testAddFirst()  {
    LinkedList<Integer> list = new LinkedList<>();
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    int n = Utils.getRandomInteger(0, 100);
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      Integer value = Utils.getRandomInteger(0, Integer.MAX_VALUE);
      values[n - i - 1] = value;
      list.addFirst(value);
      assertFalse(list.isEmpty());
      assertEquals(i + 1, list.size());
      assertEquals(value, list.get(0));
      assertEquals(value, list.getFirst());
    }
    verifyValues(values, list);
  }

  @Test
  public void testAddLast()  {
    LinkedList<Integer> list = new LinkedList<>();
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    int n = Utils.getRandomInteger(0, 100);
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      Integer value = Utils.getRandomInteger(0, Integer.MAX_VALUE);
      values[i] = value;
      list.addLast(value);
      assertFalse(list.isEmpty());
      assertEquals(i + 1, list.size());
      assertEquals(value, list.get(list.size() - 1));
      assertEquals(value, list.getLast());
    }
    verifyValues(values, list);
  }

  @Test
  public void testRemove() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    int size = list.size();
    assertFalse(list.isEmpty());
    assertEquals(values.length, list.size());
    assertArrayEquals(values, list.toArray());
    assertEquals(values[values.length - 1], list.remove(list.size() - 1));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[0], list.remove(0));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[1], list.remove(0));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[10], list.remove(8));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[11], list.remove(8));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[20], list.remove(16));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[values.length - 2], list.remove(list.size() - 1));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());

    assertEquals(values[values.length - 3], list.remove(list.size() - 1));
    assertFalse(list.isEmpty());
    assertEquals(--size, list.size());
  }

  @Test
  public void testRemoveFirst() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    verifyValues(values, list);
    for (int i = 0; i < values.length; i++) {
      assertEquals(values[i], list.removeFirst());
      assertEquals(values.length - i - 1, list.size());
    }
    verifyValues(new Integer[0], list);
  }

  @Test
  public void testRemoveLast() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    verifyValues(values, list);
    for (int i = 0; i < values.length; i++) {
      assertEquals(values[values.length - i - 1], list.removeLast());
      assertEquals(values.length - i - 1, list.size());
    }
    verifyValues(new Integer[0], list);
  }

  @Test
  public void testReplace() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    verifyValues(values, list);
    for (int i = 0; i < values.length; i++) {
      list.replace(i, i);
      values[i] = i;
      verifyValues(values, list);
    }
  }

  @Test
  public void testSubList() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    verifyValues(values, list);

    LinkedList<Integer> subList1 = list.subList(0, 50);
    Integer[] subValues1 = Arrays.copyOfRange(values, 0, 50);
    verifyValues(subValues1, subList1);

    LinkedList<Integer> subList2 = list.subList(25, 50);
    Integer[] subValues2 = Arrays.copyOfRange(values, 25, 50);
    verifyValues(subValues2, subList2);
  }

  @Test
  public void testClear() {
    LinkedList<Integer> list = new LinkedList<>();
    verifyValues(EMPTY_LONG_ARRAY, list);
    list.clear();
    verifyValues(EMPTY_LONG_ARRAY, list);
    list.addFirst(1);
    list.clear();
    verifyValues(EMPTY_LONG_ARRAY, list);
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    list = new LinkedList<>(values);
    verifyValues(values, list);
    list.clear();
    verifyValues(EMPTY_LONG_ARRAY, list);
  }

  @Test
  public void testToArray() {
    LinkedList<Integer> list = new LinkedList<>();
    assertNotNull(list.toArray());
    assertEquals(0, list.toArray().length);
    int n = Utils.getRandomInteger(0, 100);
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      Integer value = Utils.getRandomInteger(0, Integer.MAX_VALUE);
      values[i] = value;
      list.add(i, value);
    }
    assertNotNull(list.toArray());
    assertArrayEquals(values, list.toArray());
  }

  @Test
  public void testToString() {
    Integer[] values = Utils.getRandomIntegerArray(50, 100, 0, Integer.MAX_VALUE);
    LinkedList<Integer> list = new LinkedList<>(values);
    verifyValues(values, list);

    StringBuilder str = new StringBuilder();
    str.append("LinkedList{size=").append(values.length).append(", values = { ");
    for (int i = 0; i < values.length; i++) {
      str.append(values[i]);
      if (i < values.length - 1) {
        str.append(", ");
      }
    }
    str.append(" }}");
    assertEquals(str.toString(), list.toString());
  }

  private void verifyValues(Integer[] expectedValues, LinkedList<Integer> list) {
    assertEquals(expectedValues.length, list.size());
    assertArrayEquals(expectedValues, list.toArray());
    if (expectedValues.length == 0) {
      assertTrue(list.isEmpty());
    } else {
      assertFalse(list.isEmpty());
      for (int i = 0; i < expectedValues.length; i++) {
        assertTrue(list.contains(expectedValues[i]));
        assertEquals(expectedValues[i], list.get(i));
      }
      assertEquals(expectedValues[0], list.getFirst());
      assertEquals(expectedValues[expectedValues.length - 1], list.getLast());
    }
  }
}
