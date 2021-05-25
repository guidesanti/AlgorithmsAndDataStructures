package br.com.eventhorizon.common.datastructures.sets;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SetEnumeratorTest {

  @Test
  public void testSetEnumerator() {
    SetEnumerator setEnumerator = new SetEnumerator();
    assertTrue(setEnumerator.hasNext());

    Set<Integer> expectedSet = new HashSet<>();
    expectedSet.add(0);
    Set<Integer> actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(1);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(0);
    expectedSet.add(1);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(2);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(0);
    expectedSet.add(2);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(1);
    expectedSet.add(2);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    expectedSet = new HashSet<>();
    expectedSet.add(0);
    expectedSet.add(1);
    expectedSet.add(2);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);

    setEnumerator.reset();
    expectedSet = new HashSet<>();
    expectedSet.add(0);
    actualSet = setEnumerator.next();
    assertEquals(expectedSet, actualSet);
  }
}
