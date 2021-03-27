package br.com.eventhorizon.common.datastructures.disjointsets;

/**
 * A collection of disjoint sets with implementation based rooted trees.
 * The heuristics union by rank and path compression are used to improve performance.
 */
public class RootedTreeDisjointSets {

  private RootedTreeDisjointSets() { }

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
    return new Node<>(object);
  }

  /**
   * Merges the two sets that contains node1 and node2.
   *
   * @param node1 Some node of the first set
   * @param node2 Some node of the second set
   * @return The representative node of the resulting set
   */
  public static <T> Node<T> union(Node<T> node1, Node<T> node2) {
    Node<T> root1 = findRoot(node1);
    Node<T> root2 = findRoot(node2);
    if (root1 == root2) {
      return root1;
    }
    if (root1.rank < root2.rank) {
      root1.parent = root2;
      root2.rank++;
      return root2;
    } else {
      root2.parent = root1;
      root1.rank++;
      return root1;
    }
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
    return findRoot(node);
  }

  private static <T> Node<T> findRoot(Node<T> node) {
    if (node.parent != node) {
      node.parent = findRoot(node.parent);
    }
    return node.parent;
  }

  public static class Node<T> {

    protected Node<T> parent;

    protected int rank;

    private final T object;

    public Node(T object) {
      this.parent = this;
      this.rank = 0;
      this.object = object;
    }

    public T getObject() {
      return object;
    }
  }
}
