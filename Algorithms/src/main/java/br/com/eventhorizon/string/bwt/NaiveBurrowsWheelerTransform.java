package br.com.eventhorizon.string.bwt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaiveBurrowsWheelerTransform implements BurrowsWheelerTransform {

  @Override
  public String transform(String text) {
    int index = 0;
    List<String> rotations = new ArrayList<>(text.length());
    while (index <= text.length()) {
      rotations.add(text.substring(text.length() - index) + "$" + text.substring(0, text.length() - index));
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
