package br.com.eventhorizon.common.pa.format;

public class LongField extends Field {

  private long minimum;

  private long maximum;

  public LongField() {
    super(FieldType.LONG);
  }

  public long getMinimum() {
    return minimum;
  }

  public void setMinimum(long minimum) {
    this.minimum = minimum;
  }

  public long getMaximum() {
    return maximum;
  }

  public void setMaximum(long maximum) {
    this.maximum = maximum;
  }
}
