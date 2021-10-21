package br.com.eventhorizon.common.pa.format;

public class DoubleField extends Field {

  private double minimum;

  private double maximum;

  public DoubleField() {
    super(FieldType.DOUBLE);
  }

  public double getMinimum() {
    return minimum;
  }

  public void setMinimum(double minimum) {
    this.minimum = minimum;
  }

  public double getMaximum() {
    return maximum;
  }

  public void setMaximum(double maximum) {
    this.maximum = maximum;
  }
}
