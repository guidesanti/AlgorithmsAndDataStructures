package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuildSuffixArray implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();
    List<String> suffixes = new ArrayList<>();
    for (int index = 0; index < text.length(); index++) {
      suffixes.add(text.substring(index));
    }
    Collections.sort(suffixes);
    suffixes.forEach(suffix -> System.out.print(text.length() - suffix.length() + " "));
  }
}
