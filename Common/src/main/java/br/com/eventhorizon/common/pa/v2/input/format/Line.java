package br.com.eventhorizon.common.pa.v2.input.format;

import java.util.ArrayList;
import java.util.List;

public class Line {

  private static final ValueOrReference DEFAULT_COUNT = new ValueOrReference(1);

  private final ValueOrReference count;

  private final List<Field> fields;

  public Line() {
    this.count = DEFAULT_COUNT;
    this.fields = new ArrayList<>();
  }

  public ValueOrReference getCount() {
    return count;
  }

  public List<Field> getFields() {
    return fields;
  }
}
