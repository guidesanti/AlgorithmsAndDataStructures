package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {

  private static final Object[] EMPTY_VALUES = {};

  @Test
  public void testArrayList() {
    ArrayList list = new ArrayList<>();
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    assertArrayEquals(EMPTY_VALUES, list.toArray());
  }

  @Test
  public void testArrayListWithInitialCapacity() {
    ArrayList list = new ArrayList<>(1024);
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    assertArrayEquals(EMPTY_VALUES, list.toArray());
  }

  @Test
  public void testArrayListFromNullArray() {
    assertThrows(IllegalArgumentException.class, () -> new ArrayList(null));
  }

  @Test
  public void testArrayListFromEmptyArray() {
    ArrayList list = new ArrayList<>(new Object[0]);
    assertTrue(list.isEmpty());
    assertEquals(0, list.size());
    assertArrayEquals(EMPTY_VALUES, list.toArray());
  }

  @Test
  public void testArrayListFromArray() {
    Object[] objects = new Object[Utils.getRandomInteger(10, 100)];
    for (int i = 0; i < objects.length; i++) {
      objects[i] = new Object();
    }

    ArrayList list = new ArrayList<>(objects);
    assertFalse(list.isEmpty());
    assertEquals(objects.length, list.size());
    assertArrayEquals(objects, list.toArray());
  }

  @Test
  public void testAdd() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>();
      for (Object object : objects) {
        list.add(object);
      }
      assertFalse(list.isEmpty());
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
    }
  }

  @Test
  public void testAddToIndexOutOfBounds() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(Utils.getRandomInteger(Integer.MIN_VALUE, -1), null));
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(Utils.getRandomInteger(Integer.MIN_VALUE, -1), new Object()));
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE), null));
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE), new Object()));
    }
  }

  @Test
  public void testAddToIndex() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>();
      for (int j = 0; j < objects.length; j++) {
        list.add(j, objects[j]);
      }
      assertFalse(list.isEmpty());
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
    }
  }

  @Test
  public void testGetIndexOutOfBounds() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(Utils.getRandomInteger(Integer.MIN_VALUE, -1)));
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE)));
    }
  }

  @Test
  public void testGetEmptyList() {
    for (int i = 0; i < 100; i++) {
      ArrayList list = new ArrayList<>();
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }
  }

  @Test
  public void testGet() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      assertFalse(list.isEmpty());
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
      for (int j = 0; j < objects.length; j++) {
        Object object = list.get(j);
        assertNotNull(object);
        assertEquals(objects[j], object);
      }
    }
  }

  @Test
  public void testRemoveIndexOutOfBounds() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(Utils.getRandomInteger(Integer.MIN_VALUE, -1)));
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE)));
    }
  }

  @Test
  public void testRemoveEmptyList() {
    for (int i = 0; i < 100; i++) {
      ArrayList list = new ArrayList<>();
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE)));
    }
  }

  @Test
  public void testRemove() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      int removeIndex = Utils.getRandomInteger(0, objects.length - 1);
      Object removedObject = list.remove(removeIndex);
      assertNotNull(removedObject);
      assertEquals(objects[removeIndex], removedObject);
      assertEquals(objects.length - 1, list.size());
      assertFalse(list.contains(removedObject));
    }
  }

  @Test
  public void testReplaceIndexOutOfBounds() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(Integer.MIN_VALUE, -1), null));
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(Integer.MIN_VALUE, -1), new Object()));
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE), null));
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(objects.length, Integer.MAX_VALUE), new Object()));
    }
  }

  @Test
  public void testReplaceEmptyList() {
    for (int i = 0; i < 100; i++) {
      ArrayList list = new ArrayList<>();
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE), null));
      assertThrows(IndexOutOfBoundsException.class, () -> list.replace(Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE), new Object()));
    }
  }

  @Test
  public void testReplace() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      int replaceIndex = Utils.getRandomInteger(0, objects.length - 1);
      Object newObject = new Object();
      list.replace(replaceIndex, newObject);
      assertEquals(newObject, list.get(replaceIndex));
      assertNotEquals(objects[replaceIndex], newObject);
      assertEquals(objects.length, list.size());
      assertFalse(list.contains(objects[replaceIndex]));
      assertTrue(list.contains(newObject));
    }
  }

  @Test
  public void testSubListIndexOutOfBounds() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(2, 100);
      ArrayList list = new ArrayList<>(objects);
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(
          Utils.getRandomInteger(Integer.MIN_VALUE, -1),
          Utils.getRandomInteger(0, list.size() - 1)));
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(
          Utils.getRandomInteger(0, list.size()),
          Utils.getRandomInteger(list.size(), Integer.MAX_VALUE)));
      int from = Utils.getRandomInteger(1, list.size());
      int to = Utils.getRandomInteger(0, from - 1);
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(from, to));
    }
  }

  @Test
  public void testSubListEmptyList() {
    for (int i = 0; i < 100; i++) {
      ArrayList list = new ArrayList<>();
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(
          Utils.getRandomInteger(Integer.MIN_VALUE, -1), 0));
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(
          0, Utils.getRandomInteger(list.size(), Integer.MAX_VALUE)));
      assertThrows(IndexOutOfBoundsException.class, () -> list.subList(0, 0));
    }
  }

  @Test
  public void testSubList() {
    for (int i = 0; i < 10000; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      int from = Utils.getRandomInteger(0, objects.length - 1);
      int to = Utils.getRandomInteger(from, objects.length - 1);
      ArrayList subList = list.subList(from, to);
      assertEquals(to - from + 1, subList.size());
      for (int j = 0; j < from; j++) {
        assertFalse(subList.contains(objects[j]));
      }
      for (int j = from; j <= to; j++) {
        assertTrue(subList.contains(objects[j]));
      }
      for (int j = to + 1; j < objects.length; j++) {
        assertFalse(subList.contains(objects[j]));
      }
    }
  }

  @Test
  public void testClear() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      assertFalse(list.isEmpty());
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
      list.clear();
      assertTrue(list.isEmpty());
      assertEquals(0, list.size());
      assertArrayEquals(new Object[0], list.toArray());
    }
  }

  @Test
  public void testContains() {
    for (int i = 0; i < 100; i++) {
      Object[] objects1 = Utils.getRandomObjectArray(1, 100);
      Object[] objects2 = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects1);
      for (int j = 0; j < objects1.length; j++) {
        assertTrue(list.contains(objects1[j]));
      }
      for (int j = 0; j < objects2.length; j++) {
        assertFalse(list.contains(objects2[j]));
      }
    }
  }

  @Test
  public void testIsEmpty() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(1, 100);
      ArrayList list = new ArrayList<>(objects);
      assertFalse(list.isEmpty());
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
      list.clear();
      assertTrue(list.isEmpty());
      assertEquals(0, list.size());
      assertArrayEquals(new Object[0], list.toArray());
    }
  }

  @Test
  public void testSize() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertEquals(objects.length, list.size());
    }
  }

  @Test
  public void testToArray() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertArrayEquals(objects, list.toArray());
    }
  }

  @Test
  public void testToArrayTyped() {
    for (int i = 0; i < 1000; i++) {
      Integer[] values = Utils.getRandomIntegerArray(1, 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
      Integer[] integers = new Integer[values.length];
      for (int j = 0; j < values.length; j++) {
        integers[j] = values[j];
      }
      ArrayList<Integer> list = new ArrayList<>(integers);

      int length = Utils.getRandomInteger(0, values.length - 1);
      Integer[] integers2 = new Integer[length];
      Integer[] result = list.toArray(integers2);
      assertEquals(list.size(), result.length);
      assertArrayEquals(integers, result);

      integers2 = new Integer[integers.length];
      result = list.toArray(integers2);
      assertEquals(list.size(), result.length);
      assertSame(integers2, result);
      assertArrayEquals(integers, result);

      length = Utils.getRandomInteger(list.size() + 1, list.size() + 1000);
      integers2 = new Integer[length];
      result = list.toArray(integers2);
      assertSame(integers2, result);
      assertNull(result[list.size()]);
    }
  }

  @Test
  public void testShuffle() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertArrayEquals(objects, list.toArray());
      list.shuffle();
      assertEquals(objects.length, list.size());
    }
  }

  @Test
  public void testInvert() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 100);
      ArrayList list = new ArrayList<>(objects);
      assertArrayEquals(objects, list.toArray());
      list.invert();
      for (int j = 0; j < objects.length; j++) {
        assertEquals(objects[objects.length - j - 1], list.get(j));
      }
    }
  }

  @Test
  public void testIterator() {
    for (int i = 0; i < 100; i++) {
      Object[] objects = Utils.getRandomObjectArray(0, 1000);
      ArrayList list = new ArrayList<>(objects);
      assertEquals(objects.length, list.size());
      assertArrayEquals(objects, list.toArray());
      Iterator<Object> iterator = list.iterator();
      int count = 0;
      while (iterator.hasNext()) {
        assertSame(objects[count++], iterator.next());
      }
      assertEquals(count, objects.length);
      assertEquals(count, list.size());
    }
  }
}
