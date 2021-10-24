package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import br.com.eventhorizon.common.pa.PAv2;

import java.util.SortedSet;
import java.util.TreeSet;

public class P1215 implements PAv2 {

  private StringBuilder text;

  private SortedSet<String> dictionary;

  private void init() {
    text = new StringBuilder();
    dictionary = new TreeSet<>();
  }

  private void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    String part = scanner.next();
    while (part != null) {
      text.append(part).append(" ");
      part = scanner.next();
    }
  }

  private void writeOutput() {
    dictionary.forEach(System.out::println);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private void finalSolutionImpl() {
    int state = 0;
    int start = 0;
    int end = 0;
    for (int index = 0; index < text.length(); index++) {
      char symbol = text.charAt(index);
      switch (state) {
        case 0:
          if (isValidSymbol(symbol)) {
            start = index;
            end = start + 1;
            state = 1;
          }
          break;
        case 1:
          if (isValidSymbol(symbol)) {
            end++;
          } else {
            dictionary.add(text.substring(start, end).toLowerCase());
            state = 0;
          }
          break;
      }
    }
  }

  private boolean isValidSymbol(char symbol) {
    return (symbol >= 'A' && symbol <= 'Z') || (symbol >= 'a' && symbol <= 'z');
  }
}
