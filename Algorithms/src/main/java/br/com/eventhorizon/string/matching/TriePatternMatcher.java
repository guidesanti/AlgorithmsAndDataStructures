package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.datastructures.tries.Trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
