package br.com.eventhorizon.datastructures.strings;

public class CircularString {

  private final String string;

  private final int hashCode;

  public CircularString(String string) {
    this.string = string;
    this.hashCode = computeHashCode();
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof CircularString)) {
      return false;
    }
    CircularString otherString = (CircularString) obj;
    if (this.string.length() != otherString.string.length()) {
      return false;
    }
    boolean equals = false;
    String curr = this.string;
    for (int i = 0; i < curr.length(); i++) {
      if (curr.equals(otherString.string)) {
        equals = true;
        break;
      }
      curr = curr.substring(1) + curr.charAt(0);
    }
    return equals;
  }

  @Override
  public String toString() {
    return string;
  }

  private int computeHashCode() {
    int hashCode = 0;
    String curr = this.string;
    for (int i = 0; i < curr.length(); i++) {
      hashCode += curr.hashCode();
      curr = curr.substring(1) + curr.charAt(0);
    }
    return hashCode;
  }
}
