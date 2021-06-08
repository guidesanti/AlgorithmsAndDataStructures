package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BurrowsWheelerTransform implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    System.out.println(burrowsWheelerTransform(scanner.next()));
  }

  private static String burrowsWheelerTransform(String text) {
    int index = 0;
    List<String> rotations = new ArrayList<>(text.length());
    while (index < text.length()) {
      rotations.add(text.substring(text.length() - index) + text.substring(0, text.length() - index));
      index++;
    }
    Collections.sort(rotations);
    StringBuilder str = new StringBuilder();
    for (String rotation : rotations) {
      str.append(rotation.charAt(rotation.length() - 1));
    }
    return str.toString();
  }
}
