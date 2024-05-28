package br.com.eventhorizon.datastructures.tries;

import br.com.eventhorizon.common.utils.UnsupportedSymbolException;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TrieTest {

  private static final int NUMBER_OF_TESTS = 100;

  private static final int ALPHABET_SIZE = 128;

  @Test
  public void testAddNullKey() {
    Trie trie = new Trie();
    assertThrows(IllegalArgumentException.class, () -> trie.add(null));
  }

  @Test
  public void testAddEmptyKey() {
    Trie trie = new Trie();
    assertThrows(IllegalArgumentException.class, () -> trie.add(""));
  }

  @Test
  public void testAddDuplicateKey() {
    Trie trie = new Trie();
    assertTrue(trie.isEmpty());
    trie.add("something");
    assertFalse(trie.isEmpty());
    assertEquals(1, trie.size());
    trie.add("something");
    assertFalse(trie.isEmpty());
    assertEquals(1, trie.size());
  }

  @Test
  public void testAddKeyWithUnsupportedSymbol() {
    Trie trie = new Trie();
    for (int i = 0; i < ALPHABET_SIZE; i++) {
      char[] symbol = { (char) i };
      String key = new String(symbol);
      assertFalse(trie.contains(key));
      trie.add(key);
      assertTrue(trie.contains(key));
    }
    for (int i = ALPHABET_SIZE; i < Character.MAX_VALUE; i++) {
      char[] symbol = { (char) i };
      String key = new String(symbol);
      assertThrows(UnsupportedSymbolException.class, () -> trie.add(key));
    }
  }

  @Test
  public void testAddRandomKeys() {
    for (int test = 0; test < NUMBER_OF_TESTS; test++) {
      Set<String> keys = Arrays.stream(Utils.getRandomStringArray(Utils.CharType.ALL_ASCII, 1, 256, 1, 1000)).collect(Collectors.toSet());
      Trie trie = new Trie();
      assertTrue(trie.isEmpty());
      int expectedSize = 0;
      for (String key : keys) {
        assertFalse(trie.contains(key));
        assertEquals(expectedSize++, trie.size());
        trie.add(key);
        assertFalse(trie.isEmpty());
        assertTrue(trie.contains(key));
        assertEquals(expectedSize, trie.size());
      }
    }
  }

  @Test
  public void testWithSimpleKeys() {
    String[] keys = {
        "sh",
        "shell",
        "hell",
        "he",
        "guilherme",
        "gui"
    };
    Trie trie = new Trie(keys);
    assertFalse(trie.isEmpty());
    assertEquals(keys.length, trie.size());
    for (String key : keys) {
      assertTrue(trie.contains(key));
    }
  }

  @Test
  public void testRemoveFromEmptyTrie() {
    Trie trie = new Trie();
    assertTrue(trie.isEmpty());
    assertEquals(0, trie.size());
    assertThrows(NoSuchElementException.class, () -> trie.remove("something"));
  }

  @Test
  public void testRemoveNonExistingKey() {
    String[] keys = {
        "sh",
        "shell",
        "hell",
        "he",
        "guilherme",
        "gui"
    };
    Trie trie = new Trie(keys);
    assertFalse(trie.isEmpty());
    assertEquals(keys.length, trie.size());
    for (String key : keys) {
      assertTrue(trie.contains(key));
    }
    assertThrows(NoSuchElementException.class, () -> trie.remove("something"));
  }

  @Test
  public void testRemove1() {
    String[] keys = {
        "sh",
        "shell",
        "hell",
        "he",
        "guilherme",
        "gui"
    };
    int size = keys.length;
    Trie trie = new Trie(keys);
    assertFalse(trie.isEmpty());
    assertEquals(keys.length, trie.size());
    for (String key : keys) {
      assertTrue(trie.contains(key));
    }

    assertThrows(NoSuchElementException.class, () -> trie.remove("she"));

    trie.remove("sh");
    size--;
    assertEquals(size, trie.size());
    assertFalse(trie.contains("sh"));
    assertTrue(trie.contains("shell"));

    trie.remove("shell");
    size--;
    assertEquals(size, trie.size());
    assertFalse(trie.contains("sh"));
    assertFalse(trie.contains("shell"));
  }

  @Test
  public void testRemove2() {
    String[] keys = {
        "sh",
        "shell",
        "hell",
        "he",
        "guilherme",
        "gui"
    };
    int size = keys.length;
    Trie trie = new Trie(keys);
    assertFalse(trie.isEmpty());
    assertEquals(keys.length, trie.size());
    for (String key : keys) {
      assertTrue(trie.contains(key));
    }

    assertThrows(NoSuchElementException.class, () -> trie.remove("she"));

    trie.remove("shell");
    size--;
    assertEquals(size, trie.size());
    assertTrue(trie.contains("sh"));
    assertFalse(trie.contains("shell"));

    trie.remove("sh");
    size--;
    assertEquals(size, trie.size());
    assertFalse(trie.contains("sh"));
    assertFalse(trie.contains("shell"));
  }

  @Test
  public void testRemoveRandomKeys() {
    for (int test = 0; test < NUMBER_OF_TESTS; test++) {
      String[] keys = Utils.getRandomStringArray(Utils.CharType.ALL_ASCII, 1, 256, 1, 100);
      Set<String> keySet = Arrays.stream(keys).collect(Collectors.toSet());
      List<String> keyList = new ArrayList<>(keySet);
      Collections.shuffle(keyList);
      Trie trie = new Trie(keys);
      assertFalse(trie.isEmpty());
      int size = keyList.size();
      assertEquals(size, trie.size());
      for (String key : keySet) {
        assertTrue(trie.contains(key));
      }
      for (String key : keyList) {
        trie.remove(key);
        size--;
        assertEquals(size, trie.size());
        assertFalse(trie.contains(key));
        keySet.remove(key);
        for (String key1 : keySet) {
          assertTrue(trie.contains(key1));
        }
      }
    }
  }
}
