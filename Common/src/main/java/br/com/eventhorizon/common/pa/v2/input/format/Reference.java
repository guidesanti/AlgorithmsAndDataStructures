package br.com.eventhorizon.common.pa.v2.input.format;

public class Reference {

  private static final String START = "$";

  private final String key;

  private int line;

  private int field;

  public Reference(String key) {
    this.key = key;
    parse(key);
  }

  public Reference(int line, int field) {
    this.key = START + "line[" + line + "].field[" + field + "]";
    this.line = line;
    this.field = field;
  }

  public String getKey() {
    return key;
  }

  public int getLine() {
    return line;
  }

  public int getField() {
    return field;
  }

  private void parse(String key) {
    if (!key.startsWith(START)) {
      throw new RuntimeException("Invalid key:" + key);
    }
    String[] values = key.substring(1).split("\\.");

    String lineStr = values[0];
    if (!lineStr.startsWith("line[")) {
      throw new RuntimeException("Invalid key: " + key);
    }
    line = Integer.parseInt(lineStr.substring(5, lineStr.length() - 1));

    String fieldStr = values[1];
    if (!fieldStr.startsWith("field[")) {
      throw new RuntimeException("Invalid key: " + key);
    }
    field = Integer.parseInt(fieldStr.substring(6, fieldStr.length() - 1));
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Reference)) {
      return false;
    }
    return this.key.equals(((Reference) obj).key);
  }

  @Override
  public String toString() {
    return "Reference{" + "key='" + key + '\'' + ", line=" + line + ", field=" + field + '}';
  }
}
