package br.com.eventhorizon.common.datastructures.disjointsets;

/**
 * A collection of disjoint sets with implementation based on linked lists.
 * Weighted union heuristic is used to improve performance.
 */
public class LinkedListDisjointSets {

  /**
   * Creates a new set whose only member is object.
   *
   * @param object The only member of the set to be created
   * @return The representative node of the created set
   */
  public static <T> Node<T> build(T object) {
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null");
    }
    Set<T> set = new Set<>();
    Node<T> node = new Node<>(set, object);
    set.first = node;
    set.last = node;
    set.size = 1;
    return node;
  }

  /**
   * Merges the two sets that contains node1 and node2.
   *
   * @param node1 Some node of the first set
   * @param node2 Some node of the second set
   * @return The representative node of the resulting set
   */
  public static <T> Node<T> union(Node<T> node1, Node<T> node2) {
    Set<T> set1 = node1.set;
    Set<T> set2 = node2.set;
    if (set1.equals(set2)) {
      return set1.first;
    }
    Set<T> first = set1;
    Set<T> second = set2;
    if (set1.size < set2.size) {
      first = set2;
      second = set1;
    }
    first.last.next = second.first;
    first.last = second.last;
    Node<T> node = second.first;
    while (node != null) {
      node.set = first;
      node = node.next;
    }
    first.size += second.size;
    clearSet(second);
    return first.first;
  }

  /**
   * Finds the representative node of the set containing node.
   *
   * @param node The node to look for its set representative
   * @return The representative node of the set containing the object or null if not found
   */
  public static <T> Node<T> find(Node<T> node) {
    if (node == null) {
      return null;
    }
    return node.set.first;
  }

  private static <T> void clearSet(Set<T> set) {
    set.first = null;
    set.last = null;
  }

  private static class Set<T> {

    protected Node<T> first;

    protected Node<T> last;

    protected int size;
  }

  public static class Node<T> {

    protected Set<T> set;

    protected Node<T> next;

    private final T object;

    private Node(Set<T> set, T object) {
      this.set = set;
      this.next = null;
      this.object = object;
    }

    public T getObject() {
      return object;
    }
  }
}
