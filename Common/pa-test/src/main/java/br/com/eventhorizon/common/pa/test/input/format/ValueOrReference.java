package br.com.eventhorizon.common.pa.test.input.format;

public class ValueOrReference {

  private final Object valueOrReference;

  public ValueOrReference(Integer valueOrReference) {
    this.valueOrReference = valueOrReference;
  }

  public ValueOrReference(String valueOrReference) {
    this.valueOrReference = valueOrReference;
  }

  public boolean isReference() {
    return valueOrReference.getClass().equals(String.class);
  }

  public int asValue() {
    return (int) valueOrReference;
  }

  public String asReference() {
    return (String) valueOrReference;
  }

  @Override
  public String toString() {
    return "ValueOrReference{" + "valueOrReference=" + valueOrReference + '}';
  }
}
