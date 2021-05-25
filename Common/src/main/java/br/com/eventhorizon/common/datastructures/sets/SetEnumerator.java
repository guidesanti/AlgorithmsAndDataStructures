package br.com.eventhorizon.common.datastructures.sets;

import java.util.HashSet;
import java.util.Set;

public class SetEnumerator {

  private int elements;

  public SetEnumerator() {
    elements = 0;
  }

  public void reset() {
    elements = 0;
  }

  public boolean hasNext() {
    return elements < Integer.MAX_VALUE;
  }

  public Set<Integer> next() {
    elements++;
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < 32; i++) {
      if ((elements & (1 << i)) > 0) {
        set.add(i);
      }
    }
    return set;
  }
}
