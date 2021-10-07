package br.com.eventhorizon.common.datastructures.tries;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrieMapTest {

  private static final int NUMBER_OF_TESTS = 100;

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
    Integer[] values = new Integer[keys.length];
    for (int i = 0; i < values.length; i++) {
      values[i] = i;
    }
    TrieMap<Integer> trie = new TrieMap<>(keys, values);
    assertFalse(trie.isEmpty());
    assertEquals(keys.length, trie.size());
    for (String key : keys) {
      assertTrue(trie.containsKey(key));
    }
  }

  @Test
  public void testWithRandomKeys() {
    for (int test = 0; test < NUMBER_OF_TESTS; test++) {
      String[] keys = Utils.getRandomStringArray(Utils.CharType.ALL_ASCII, 1, 256, 1, 1000);
      Integer[] values = new Integer[keys.length];
      for (int i = 0; i < values.length; i++) {
        values[i] = i;
      }
      TrieMap<Integer> trie = new TrieMap<>(keys, values);
      assertFalse(trie.isEmpty());
      assertEquals(keys.length, trie.size());
      for (String key : keys) {
        assertTrue(trie.containsKey(key));
      }
    }
  }
}
