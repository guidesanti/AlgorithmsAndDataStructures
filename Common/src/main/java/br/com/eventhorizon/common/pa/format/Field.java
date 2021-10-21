package br.com.eventhorizon.common.pa.format;

public abstract class Field {

  private FieldType type;

  public Field(FieldType type) {
    this.type = type;
  }

  public FieldType getType() {
    return type;
  }
}
