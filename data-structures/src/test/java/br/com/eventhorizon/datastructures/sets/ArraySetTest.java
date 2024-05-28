package br.com.eventhorizon.datastructures.sets;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

public class ArraySetTest {

  @Test
  public void testArraySet() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);
    ArraySet<Integer> set = new ArraySet<>();
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Add
    int count = 0;
    for (Integer key : keys) {
      assertTrue(set.add(key));
      assertTrue(set.contains(key));
      assertFalse(set.isEmpty());
      count++;
      assertEquals(count, set.size());
    }

    // Add keys already in set
    for (Integer key : keys) {
      assertFalse(set.add(key));
    }
    assertFalse(set.isEmpty());
    assertEquals(count, set.size());

    // Contains
    for (Integer key : keys) {
      assertTrue(set.contains(key));
    }

    // Remove
    for (Integer key : keys) {
      assertTrue(set.remove(key));
      assertFalse(set.contains(key));
      count--;
      assertEquals(count, set.size());
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Remove keys not in set
    for (Integer key : keys) {
      assertFalse(set.remove(key));
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Contains
    for (Integer key : keys) {
      assertFalse(set.contains(key));
    }
  }

  @Test
  public void testArraySetWithInitialCapacity() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);
    ArraySet<Integer> set = new ArraySet<>(1000);
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Add
    int count = 0;
    for (Integer key : keys) {
      assertTrue(set.add(key));
      assertTrue(set.contains(key));
      assertFalse(set.isEmpty());
      count++;
      assertEquals(count, set.size());
    }

    // Add keys already in set
    for (Integer key : keys) {
      assertFalse(set.add(key));
    }
    assertFalse(set.isEmpty());
    assertEquals(count, set.size());

    // Contains
    for (Integer key : keys) {
      assertTrue(set.contains(key));
    }

    // Remove
    for (Integer key : keys) {
      assertTrue(set.remove(key));
      assertFalse(set.contains(key));
      count--;
      assertEquals(count, set.size());
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Remove keys not in set
    for (Integer key : keys) {
      assertFalse(set.remove(key));
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Contains
    for (Integer key : keys) {
      assertFalse(set.contains(key));
    }
  }

  @Test
  public void testArraySetWithInitialKeys() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);
    ArraySet<Integer> set = new ArraySet<>(keys);
    int count = keys.length;
    assertFalse(set.isEmpty());
    assertEquals(count, set.size());

    // Add keys already in set
    for (Integer key : keys) {
      assertFalse(set.add(key));
    }
    assertFalse(set.isEmpty());
    assertEquals(count, set.size());

    // Contains
    for (Integer key : keys) {
      assertTrue(set.contains(key));
    }

    // Remove
    for (Integer key : keys) {
      assertTrue(set.remove(key));
      assertFalse(set.contains(key));
      count--;
      assertEquals(count, set.size());
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Remove keys not in set
    for (Integer key : keys) {
      assertFalse(set.remove(key));
    }
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());

    // Contains
    for (Integer key : keys) {
      assertFalse(set.contains(key));
    }
  }

  @Test
  public void testClear() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);
    ArraySet<Integer> set = new ArraySet<>();

    int count = 0;
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());
    for (Integer key : keys) {
      assertTrue(set.add(key));
      assertTrue(set.contains(key));
      count++;
    }
    assertFalse(set.isEmpty());
    assertEquals(count, set.size());
    set.clear();
    assertTrue(set.isEmpty());
    assertEquals(0, set.size());
    for (Integer key : keys) {
      assertFalse(set.contains(key));
    }
  }

  @Test
  public void testToArray() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);

    // Default constructor
    ArraySet<Integer> set = new ArraySet<>();
    for (Integer key : keys) {
      set.add(key);
    }
    assertArrayEquals(keys, set.toArray());

    // Constructor with initial capacity
    set = new ArraySet<>(keys.length);
    for (Integer key : keys) {
      set.add(key);
    }
    assertArrayEquals(keys, set.toArray());

    // Constructor with array of existing keys
    set = new ArraySet<>(keys);
    assertArrayEquals(keys, set.toArray());
  }

  @Test
  public void testToArrayGeneric() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);
    Integer[] biggerArray = new Integer[keys.length + 1];
    Integer[] equalArray = new Integer[keys.length];
    Integer[] smallerArray = new Integer[keys.length - 1];
    Integer[] result;

    // Default constructor
    ArraySet<Integer> set = new ArraySet<>();
    for (Integer key : keys) {
      set.add(key);
    }
    result = set.toArray(biggerArray);
    assertSame(result, biggerArray);
    assertArrayEquals(keys, Arrays.copyOf(biggerArray, keys.length));
    result = set.toArray(equalArray);
    assertSame(result, equalArray);
    assertArrayEquals(keys, equalArray);
    result = set.toArray(smallerArray);
    assertNotSame(result, smallerArray);
    assertArrayEquals(keys, result);

    // Constructor with initial capacity
    set = new ArraySet<>(keys.length);
    for (Integer key : keys) {
      set.add(key);
    }
    result = set.toArray(biggerArray);
    assertSame(result, biggerArray);
    assertArrayEquals(keys, Arrays.copyOf(biggerArray, keys.length));
    result = set.toArray(equalArray);
    assertSame(result, equalArray);
    assertArrayEquals(keys, equalArray);
    result = set.toArray(smallerArray);
    assertNotSame(result, smallerArray);
    assertArrayEquals(keys, result);

    // Constructor with array of existing keys
    set = new ArraySet<>(keys);
    result = set.toArray(biggerArray);
    assertSame(result, biggerArray);
    assertArrayEquals(keys, Arrays.copyOf(biggerArray, keys.length));
    result = set.toArray(equalArray);
    assertSame(result, equalArray);
    assertArrayEquals(keys, equalArray);
    result = set.toArray(smallerArray);
    assertNotSame(result, smallerArray);
    assertArrayEquals(keys, result);
  }

  @Test
  public void testToString() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);

    // Default constructor
    ArraySet<Integer> set = new ArraySet<>();
    for (Integer key : keys) {
      set.add(key);
    }
    StringJoiner expectedString = new StringJoiner(", ", "ArraySet{size: " + keys.length + ", keys: [", "]}");
    for (Object key : keys) {
      expectedString.add(key.toString());
    }
    String actualString = set.toString();
    assertEquals(expectedString.toString(), actualString);

    // Constructor with initial capacity
    set = new ArraySet<>(keys.length);
    for (Integer key : keys) {
      set.add(key);
    }
    expectedString = new StringJoiner(", ", "ArraySet{size: " + keys.length + ", keys: [", "]}");
    for (Object key : keys) {
      expectedString.add(key.toString());
    }
    actualString = set.toString();
    assertEquals(expectedString.toString(), actualString);

    // Constructor with array of existing keys
    set = new ArraySet<>(keys);
    expectedString = new StringJoiner(", ", "ArraySet{size: " + keys.length + ", keys: [", "]}");
    for (Object key : keys) {
      expectedString.add(key.toString());
    }
    actualString = set.toString();
    assertEquals(expectedString.toString(), actualString);
  }

  @Test
  public void testIterator() {
    Integer[] keys = Utils.getSortedRandomIntegerArray(100, 1000, 0, Integer.MAX_VALUE);

    // Default constructor
    ArraySet<Integer> set = new ArraySet<>();
    for (Integer key : keys) {
      set.add(key);
    }
    Iterator<Integer> iterator = set.iterator();
    int index = 0;
    while(iterator.hasNext()) {
      assertEquals(keys[index++], iterator.next());
    }
    assertThrows(NoSuchElementException.class, iterator::next);

    // Constructor with initial capacity
    set = new ArraySet<>(keys.length);
    for (Integer key : keys) {
      set.add(key);
    }
    iterator = set.iterator();
    index = 0;
    while(iterator.hasNext()) {
      assertEquals(keys[index++], iterator.next());
    }
    assertThrows(NoSuchElementException.class, iterator::next);

    // Constructor with array of existing keys
    set = new ArraySet<>(keys);
    iterator = set.iterator();
    index = 0;
    while(iterator.hasNext()) {
      assertEquals(keys[index++], iterator.next());
    }
    assertThrows(NoSuchElementException.class, iterator::next);
  }
}
