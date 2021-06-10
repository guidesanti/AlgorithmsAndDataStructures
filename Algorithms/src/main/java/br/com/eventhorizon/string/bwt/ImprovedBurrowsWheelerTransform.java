package br.com.eventhorizon.string.bwt;

import java.util.ArrayList;
import java.util.List;

public class ImprovedBurrowsWheelerTransform implements BurrowsWheelerTransform {

  private String text;

  @Override
  public String transform(String text) {
    this.text = text;
    List<Integer> rotations = new ArrayList<>(text.length() + 1);
    for (int index = 0; index <= text.length(); index++) {
      rotations.add(index);
    }
    rotations.sort((index1, index2) -> {
      int count = 0;
      while (count < text.length() && charAt(index1) == charAt(index2)) {
        count++;
        index1 = (index1 + 1) % (text.length() + 1);
        index2 = (index2 + 1) % (text.length() + 1);
      }
      return charAt(index1) - charAt(index2);
    });
    StringBuilder str = new StringBuilder();
    for (int index : rotations) {
      str.append(index == 0 ? '$' : charAt(index - 1));
    }
    return str.toString();
  }

  private char charAt(int index) {
    return index < text.length() ? text.charAt(index) : Character.MIN_VALUE;
  }
}
