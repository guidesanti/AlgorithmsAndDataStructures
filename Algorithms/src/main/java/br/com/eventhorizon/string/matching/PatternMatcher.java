package br.com.eventhorizon.string.matching;

import java.util.Collection;

public interface PatternMatcher {

  Collection<Integer> match(String text, String pattern);

  Collection<Integer> match(String text, Collection<String> patterns);
}
