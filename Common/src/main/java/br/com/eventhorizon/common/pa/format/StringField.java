package br.com.eventhorizon.common.pa.format;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;

import java.util.ArrayList;
import java.util.List;

public class StringField extends Field {

  private Reference lengthRef;

  private int length;

  private int minLength;

  private int maxLength;

  private Alphabet alphabet;

  public StringField() {
    super(FieldType.STRING);
  }

  public Reference getLengthRef() {
    return lengthRef;
  }

  public void setLengthRef(Reference lengthRef) {
    this.lengthRef = lengthRef;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getMinLength() {
    return minLength;
  }

  public void setMinLength(int minLength) {
    this.minLength = minLength;
  }

  public int getMaxLength() {
    return maxLength;
  }

  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  public Alphabet getAlphabet() {
    return alphabet;
  }

  public void setAlphabet(Alphabet alphabet) {
    this.alphabet = alphabet;
  }
}
