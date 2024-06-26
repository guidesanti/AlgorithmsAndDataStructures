package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.datastructures.tries.Trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Pattern matching implementation with prefix trie.
 * This algorithm first builds a trie with all prefixes, that is all the patterns that are potential
 * prefixes of the text to be searched and then the text is scanned char by char for a match with
 * the prefixes within the trie.
 */
public class TriePatternMatcher implements PatternMatcher {

  @Override
  public Collection<Integer> match(String text, String pattern) {
    String[] patterns = { pattern };
    Trie trie = new Trie(patterns);
    List<Integer> shifts = new ArrayList<>();
    for (int index = 0; index < text.length(); index++) {
      if (trie.match(text, index)) {
        shifts.add(index);
      }
    }
    return shifts;
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    String[] patternArray = new String[patterns.size()];
    int index = 0;
    for (String pattern : patterns) {
      patternArray[index++] = pattern;
    }
    Trie trie = new Trie(patternArray);
    List<Integer> shifts = new ArrayList<>();
    for (index = 0; index < text.length(); index++) {
      if (trie.match(text, index)) {
        shifts.add(index);
      }
    }
    return shifts;
  }
}
