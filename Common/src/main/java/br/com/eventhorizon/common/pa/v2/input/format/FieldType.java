package br.com.eventhorizon.common.pa.v2.input.format;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FieldType {

  BOOLEAN("boolean"),
  INTEGER("integer"),
  LONG("long"),
  DOUBLE("double"),
  STRING("string");

  private final String key;

  FieldType(String key) {
    this.key = key;
  }

  @JsonValue
  public String getKey() {
    return key;
  }
}
