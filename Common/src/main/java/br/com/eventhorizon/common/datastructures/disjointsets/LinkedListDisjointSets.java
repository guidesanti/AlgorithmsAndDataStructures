package br.com.eventhorizon.common.datastructures.disjointsets;

/**
 * A collection of disjoint sets with implementation based on linked lists.
 *
 * @param <T> The type of objects that sets contains
 */
public class LinkedListDisjointSets<T> {

  private final Set NULL;

  private int count = 0;

  /**
   * Creates a new collection of disjoint sets.
   */
  public LinkedListDisjointSets() {
    NULL = new Set();
    NULL.next = NULL;
    NULL.previous = NULL;
  }

  /**
   * Creates a new set whose only member is object.
   *
   * @param object The only member of the set to be created
   * @return The created set
   */
  public Node build(T object) {
    if (object == null) {
      throw new IllegalArgumentException("object cannot be null");
    }
    Set set = new Set();
    Node node = new Node(set, object);
    set.first = node;
    set.last = node;
    set.size = 1;
    addSet(set);
    return node;
  }

  /**
   * Merges the two sets that contains node1 and node2.
   *
   * @param node1 Some node of the first set
   * @param node2 Some node of the second set
   * @return The representative node of the resulting set
   */
  public Node union(Node node1, Node node2) {
    Set set1 = node1.set;
    Set set2 = node2.set;
    if (set1.equals(set2)) {
      throw new IllegalStateException("Nodes are already on the same set");
    }
    Set first = set1;
    Set second = set2;
    if (set1.size < set2.size) {
      first = set2;
      second = set1;
    }
    first.last.next = second.first;
    first.last = second.last;
    Node node = second.first;
    while (node != null) {
      node.set = first;
      node = node.next;
    }
    first.size += second.size;
    removeSet(second);
    clearSet(second);
    return first.first;
  }

  /**
   * Finds the representative node of the set containing object.
   *
   * @param object The object to look for its set
   * @return The representative node of the set containing the object or null if not found
   */
  public Node find(T object) {
    if (object == null) {
      return null;
    }
    Set currentSet = NULL.next;
    while (currentSet != NULL) {
      Node node = find(currentSet, object);
      if (node != null) {
        return node.set.first;
      }
      currentSet = currentSet.next;
    }
    return null;
  }

  public int count() {
    return count;
  }

  private void addSet(Set set) {
    set.next = NULL.next;
    set.previous = NULL;
    set.next.previous = set;
    set.previous.next = set;
    count++;
  }

  private void removeSet(Set set) {
    set.next.previous = set.previous;
    set.previous.next = set.next;
    clearSet(set);
    count--;
  }

  private void clearSet(Set set) {
    set.next = null;
    set.previous = null;
    set.first = null;
    set.last = null;
  }

  private Node find(Set set, T object) {
    Node currentNode = set.first;
    while (currentNode != null) {
      if (currentNode.object.equals(object)) {
        return currentNode.set.first;
      }
      currentNode = currentNode.next;
    }
    return null;
  }

  private class Set {

    private Set next;

    private Set previous;

    private Node first;

    private Node last;

    private int size;
  }

  public class Node {

    private Set set;

    private Node next;

    private final T object;

    private Node(Set set, T object) {
      this.set = set;
      this.next = null;
      this.object = object;
    }

    public T getObject() {
      return object;
    }
  }
}
