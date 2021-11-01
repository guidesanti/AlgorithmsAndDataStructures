package br.com.eventhorizon.common.pa.v2.input.format;

public class Field {

  private static final ValueOrReference DEFAULT_COUNT = new ValueOrReference(1);

  private String key;

  private ValueOrReference count;

  private FieldType type;

  private Object value;

  private ValueOrReference length;

  private Number min;

  private Number max;

  private char[] alphabet;

  public Field() {
    this.count = DEFAULT_COUNT;
  }

  public String getKey() {
    return key;
  }

  public ValueOrReference getCount() {
    return count;
  }

  public FieldType getType() {
    return type;
  }

  @SuppressWarnings("unchecked")
  public <T> T getValue() {
    return (T) value;
  }

  public ValueOrReference getLength() {
    return length;
  }

  public Number getMin() {
    return min;
  }

  public Number getMax() {
    return max;
  }

  public char[] getAlphabet() {
    return alphabet;
  }
}
