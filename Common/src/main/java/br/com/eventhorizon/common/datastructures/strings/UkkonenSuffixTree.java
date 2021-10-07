package br.com.eventhorizon.common.datastructures.strings;

/**
 * Suffix tree implementation.
 * This suffix tree implementation uses the Ukkonen's algorithm to build the tree.
 * For more details about this follow the links below:
 *  - http://web.stanford.edu/~mjkay/gusfield.pdf
 *  - https://www.geeksforgeeks.org/ukkonens-suffix-tree-construction-part-1/
 */
public class UkkonenSuffixTree {

  private static final char EMPTY_CHAR = 0;

  private final Alphabet alphabet;

  private final String text;

  private final Node root;

  public UkkonenSuffixTree(Alphabet alphabet, String text) {
    this.alphabet = new Alphabet(alphabet.symbols());
    this.alphabet.add(EMPTY_CHAR);
    this.text = text + EMPTY_CHAR;
    this.root = new Node();
    buildSuffixTree();
  }

  public Node root() {
    return root;
  }

  private void buildSuffixTree() {
    int leafCount = 0;
    End end = new End(0);

    // Initialize active point composed by activeNode, activeEdge and activeLength
    Node activeNode = root;
    int activeEdge = -1;
    int activeLength = 0;

    int remainingSuffixCount = 0;
    Node lastCreatedNode = null;

    for (int index = 0; index < text.length(); index++) {
      // Rule 1: add current character to all leaves
      end.set(index);

      // Increment remaining suffix count
      remainingSuffixCount++;

      while (remainingSuffixCount > 0) {
        // Handle active point change for active length zero
        if (activeLength == 0) {
          activeEdge = index;
        }

        int activeEdgeIndex = alphabet.symbolToIndex(text.charAt(activeEdge));

        if (activeNode.children[activeEdgeIndex] == null) {
          // Rule 2: add new leaf
          activeNode.children[activeEdgeIndex] = new Node(index, end, leafCount);
          leafCount++;
          if (lastCreatedNode != null) {
            lastCreatedNode.suffixLink = activeNode;
            lastCreatedNode = null;
          }
        } else {
          // Handle active point for walk down
          Node next = activeNode.children[activeEdgeIndex];
          int edgeLength = next.end.value - next.start + 1;
          if (activeLength >= edgeLength)  {
            activeEdge += edgeLength;
            activeLength -= edgeLength;
            activeNode = next;
            continue;
          }

          // Rule 3:
          if (text.charAt(next.start + activeLength) == text.charAt(index)) {
            if (lastCreatedNode != null) {
              lastCreatedNode.suffixLink = activeNode;
              lastCreatedNode = null;
            }

            // Handle active point for rule 3
            activeLength++;
            // Rule 3 is show stopper
            break;
          }

          // Rule 2: add new leaf ad new internal node
          Node leaf = new Node(index, end, leafCount);
          leafCount++;
          Node intermediate = new Node(next.start, new End(next.start + activeLength - 1));
          next.start += activeLength;
          intermediate.children[alphabet.symbolToIndex(text.charAt(next.start))] = next;
          intermediate.children[alphabet.symbolToIndex(text.charAt(leaf.start))] = leaf;
          activeNode.children[alphabet.symbolToIndex(text.charAt(intermediate.start))] = intermediate;
          if (lastCreatedNode != null) {
            lastCreatedNode.suffixLink = intermediate;
          }
          lastCreatedNode = intermediate;
        }

        remainingSuffixCount--;

        if (activeNode == root && activeLength > 0) {
          // Handle active point change for rule 2 case 1
          activeLength--;
          activeEdge = index - remainingSuffixCount + 1;
        } else if (activeNode != root) {
          // Handle active point change for rule 2 case 2
          activeNode = activeNode.suffixLink;
        }
      }
    }
  }

  private static class End {

    int value;

    End(int value) {
      this.value = value;
    }

    public int get() {
      return value;
    }

    public void set(int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "" + value;
    }
  }

  public class Node {

    private final Node[] children;

    private int start;

    private final End end;

    private final int suffixIndex;

    private Node suffixLink;

    public Node() {
      this.children = new Node[alphabet.size()];
      this.start = -1;
      this.end = new End(-1);
      this.suffixIndex = -1;
    }

    public Node(int start, End end) {
      this.children = new Node[alphabet.size()];
      this.start = start;
      this.end = end;
      this.suffixIndex = -1;
    }

    public Node(int start, End end, int suffixIndex) {
      this.children = new Node[alphabet.size()];
      this.start = start;
      this.end = end;
      this.suffixIndex = suffixIndex;
    }

    public Node[] children() {
      return children;
    }

    public int start() {
      return start;
    }

    public int end() {
      return end.value;
    }

    public int suffixIndex() {
      return suffixIndex;
    }

    public boolean isRoot() {
      return start < 0;
    }

    public boolean isLeaf() {
      return suffixIndex >= 0;
    }

    @Override
    public String toString() {
      if (start == -1) {
        return "ROOT";
      }
      return "Node{" + text.substring(start, end.value + 1) + "}";
    }
  }
}
