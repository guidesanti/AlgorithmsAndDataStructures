package br.com.eventhorizon.datastructures.strings;

import br.com.eventhorizon.common.utils.Alphabet;
import br.com.eventhorizon.common.utils.StringUtils;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UkkonenSuffixTreeTest {

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'B', 'C', 'D' };

  @ParameterizedTest
  @ValueSource(strings = { "DCCDCDCD", "AAABAAAB", "CCBCCBBA", "AACAAABA", "BDDBDC", "DACDDBC", "CBCCCAADADAADDADDCA", "ADADD", "DDDBDB", "xabxac", "abcabxabcd" })
  public void test(String text) {
    List<String> expectedSuffixes = StringUtils.generateSuffixes(text);
    Collections.sort(expectedSuffixes);
    UkkonenSuffixTree suffixTree = new UkkonenSuffixTree(Alphabet.DEFAULT, text);
    List<String> actualSuffixes = new ArrayList<>();
    traverse(text, suffixTree.root(), actualSuffixes, "");
    Collections.sort(actualSuffixes);
    assertEquals(expectedSuffixes, actualSuffixes);
  }

  @Test
  public void test() {
    Alphabet alphabet = new Alphabet(ALPHABET_SYMBOLS);
    for (int i = 0; i < 100; i++) {
      String text = Utils.getRandomString(alphabet, 100, 10000);
      List<String> expectedSuffixes = StringUtils.generateSuffixes(text);
      Collections.sort(expectedSuffixes);
      UkkonenSuffixTree suffixTree = null;
      try {
        suffixTree = new UkkonenSuffixTree(alphabet, text);
      } catch (Exception e) {
        fail("Failed: " + text);
      }
      List<String> actualSuffixes = new ArrayList<>();
      traverse(text, suffixTree.root(), actualSuffixes, "");
      Collections.sort(actualSuffixes);
      assertEquals(expectedSuffixes, actualSuffixes, "Failed: " + text);
    }
  }

//  @Test
//  public void testBuildTime() {
//    for (int i = 0; i < 30; i++) {
//      String text = Utils.getRandomString(Alphabet.DEFAULT, 1000000);
//      assertTimeoutPreemptively(Duration.ofMillis(2000), () -> new UkkonenSuffixTree(Alphabet.DEFAULT, text));
//    }
//  }

  private void traverse(String text, UkkonenSuffixTree.Node node, List<String> suffixes, String suffix) {
    if (!node.isRoot()) {
      if (node.isLeaf()) {
        if (node.suffixIndex() < text.length()) {
          suffix += text.substring(node.start(), node.end());
          suffixes.add(suffix);
        }
        return;
      } else {
        suffix += text.substring(node.start(), node.end() + 1);
      }
    }
    for (UkkonenSuffixTree.Node child : Arrays.stream(node.children()).filter(Objects::nonNull).collect(Collectors.toList())) {
      traverse(text, child, suffixes, suffix);
    }
  }
}
