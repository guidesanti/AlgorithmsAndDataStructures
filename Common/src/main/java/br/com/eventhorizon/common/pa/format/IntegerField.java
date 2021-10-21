package br.com.eventhorizon.common.pa.format;

public class IntegerField extends Field {

  private int minimum;

  private int maximum;

  public IntegerField() {
    super(FieldType.INTEGER);
  }

  public int getMinimum() {
    return minimum;
  }

  public void setMinimum(int minimum) {
    this.minimum = minimum;
  }

  public int getMaximum() {
    return maximum;
  }

  public void setMaximum(int maximum) {
    this.maximum = maximum;
  }
}
