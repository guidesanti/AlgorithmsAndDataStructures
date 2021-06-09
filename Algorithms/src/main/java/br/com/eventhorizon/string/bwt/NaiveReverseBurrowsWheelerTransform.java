package br.com.eventhorizon.string.bwt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaiveReverseBurrowsWheelerTransform implements ReverseBurrowsWheelerTransform {

  @Override
  public String reverse(String burrowsWheelerTransform) {
    List<String> rotations = new ArrayList<>(burrowsWheelerTransform.length());
    for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
      rotations.add("");
    }
    for (int length = 0; length < burrowsWheelerTransform.length(); length++) {
      for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
        rotations.set(index, burrowsWheelerTransform.charAt(index) + rotations.get(index));
      }
      Collections.sort(rotations);
    }
    return rotations.get(0).substring(1);
  }
}
