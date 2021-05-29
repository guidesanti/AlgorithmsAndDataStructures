package br.com.eventhorizon.string.matching;

import java.util.Collection;

public interface PatternMatcher {

  Collection<Integer> match(String text, String pattern);

  default Collection<Integer> match(String text, Collection<String> patterns) {
    throw new RuntimeException("Method not implemented");
  }
}
