package br.com.eventhorizon.datastructures.graphs;

public class WeightedUndirectedEdge implements Comparable<WeightedUndirectedEdge> {

  private final int hashCode;

  protected final int vertex1;

  protected final int vertex2;

  protected final double weight;

  public WeightedUndirectedEdge(int vertex1, int vertex2, double weight) {
    String str = vertex1 < vertex2 ? vertex1 + ":" + vertex2 : vertex2 + ":" + vertex1;
    str += ":" + weight;
    hashCode = str.hashCode();
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.weight = weight;
  }

  public int vertex1() {
    return vertex1;
  }

  public int vertex2() {
    return vertex2;
  }

  public int either() {
    return vertex1;
  }

  public int other(int vertex) {
    if (vertex == vertex1) {
      return vertex2;
    } else if (vertex == vertex2) {
      return vertex1;
    } else {
      throw new IllegalArgumentException("Invalid vertex for this edge");
    }
  }

  public double weight() {
    return weight;
  }

  @Override
  public String toString() {
    return "WeightedDirectEdge {(" + weight + ") " + vertex1 + " - " + vertex2 + "}";
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    if (!(object instanceof  WeightedUndirectedEdge)) {
      return false;
    }
    WeightedUndirectedEdge other = (WeightedUndirectedEdge) object;
    return (this.vertex1 == other.vertex1 && this.vertex2 == other.vertex2
        && this.weight == other.weight) || (this.vertex1 == other.vertex2
        && this.vertex2 == other.vertex1 && this.weight == other.weight);
  }

  @Override
  public int compareTo(WeightedUndirectedEdge other) {
    return Double.compare(this.weight, other.weight);
  }
}
