package br.com.eventhorizon.common.pa.format;

import java.util.ArrayList;
import java.util.List;

public class Line {

  private static final int DEFAULT_COUNT = 1;

  private Reference countRef;

  private int count;

  private List<Field> fields;

  public Line() {
    this.count = DEFAULT_COUNT;
    this.fields = new ArrayList<>();
  }

  public Reference getCountRef() {
    return countRef;
  }

  public void setCountRef(Reference countRef) {
    this.countRef = countRef;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public List<Field> getFields() {
    return fields;
  }

  public void setFields(List<Field> fields) {
    this.fields = fields;
  }
}
