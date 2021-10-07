package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Stack;

public class SuffixTreeFromSuffixArray implements PA {

  private static final char[] ALPHABET_SYMBOLS = { '$', 'A', 'C', 'G', 'T' };

  private static final Alphabet ALPHABET = new Alphabet(ALPHABET_SYMBOLS);

  private static String text;

  private static int[] suffixArray;

  private static int[] lpcArray;

  private static SuffixTreeNode root;

  @Override
  public void naiveSolution() {
    readInput();
    naiveBuildSuffixTree();
    writeOutput();
  }

  private static void naiveBuildSuffixTree() {
    for (int offset = text.length() - 1; offset >= 0; offset--) {
      addSuffix(offset, text.length() - offset);
    }
  }

  private static void addSuffix(int offset, int length) {
    SuffixTreeNode node = root;
    int symbolIndex = ALPHABET.symbolToIndex(text.charAt(offset));
    while (true) {
      if (node.children[symbolIndex] == null) {
        node.children[symbolIndex] = new SuffixTreeNode(null, offset, length);
        break;
      }
      int commonLength = compareSuffixes(offset, node.children[symbolIndex].offset, node.children[symbolIndex].partialLength);
      if (node.children[symbolIndex].isLeaf() || commonLength < node.children[symbolIndex].partialLength) {
        SuffixTreeNode newSuffixNode = new SuffixTreeNode(null, offset + commonLength, length - commonLength);
        SuffixTreeNode partialSuffixNode = new SuffixTreeNode(null, offset, commonLength);
        partialSuffixNode.children[ALPHABET.symbolToIndex(text.charAt(offset + commonLength))] = newSuffixNode;
        partialSuffixNode.children[ALPHABET.symbolToIndex(text.charAt(node.children[symbolIndex].offset + commonLength))] = node.children[symbolIndex];
        node.children[symbolIndex].offset += commonLength;
        node.children[symbolIndex].partialLength -= commonLength;
        node.children[symbolIndex] = partialSuffixNode;
        break;
      } else {
        node.children[symbolIndex].offset = offset;
        offset += commonLength;
        length -= commonLength;
        node = node.children[symbolIndex];
        symbolIndex = ALPHABET.symbolToIndex(text.charAt(offset));
      }
    }
  }

  private static int compareSuffixes(int offset1, int offset2, int maxLength) {
    int length = 0;
    while (offset1 < text.length() && offset2 < text.length() &&
        text.charAt(offset1) == text.charAt(offset2) && length < maxLength) {
      offset1++;
      offset2++;
      length++;
    }
    return length;
  }

  @Override
  public void finalSolution() {
    readInput();
    finalBuildSuffixTree();
    writeOutput();
  }

  private static void finalBuildSuffixTree() {
    int prevLcp = 0;
    SuffixTreeNode node = root;
    for (int i = 0; i < text.length(); i++) {
      int suffixIndex = suffixArray[i];
      while (node.fullLength > prevLcp) {
        node = node.parent;
      }
      if (node.fullLength == prevLcp) {
        node = createNewLeaf(node, suffixIndex);
      } else {
        int edgeStart = suffixArray[i - 1] + node.fullLength;
        int offset = prevLcp - node.fullLength;
        SuffixTreeNode midNode = breakEdge(node, edgeStart, offset);
        node = createNewLeaf(midNode, suffixIndex);
      }
      if (i < text.length() - 1) {
        prevLcp = lpcArray[i];
      }
    }
  }

  private static SuffixTreeNode createNewLeaf(SuffixTreeNode parent, int suffixIndex) {
    SuffixTreeNode leaf = new SuffixTreeNode(parent, suffixIndex + parent.fullLength, text.length() - (suffixIndex + parent.fullLength), text.length() - suffixIndex);
    parent.children[ALPHABET.symbolToIndex(text.charAt(leaf.offset))] = leaf;
    return leaf;
  }

  private static SuffixTreeNode breakEdge(SuffixTreeNode parent, int start, int offset) {
    int startSymbol = ALPHABET.symbolToIndex(text.charAt(start));
    int midSymbol = ALPHABET.symbolToIndex(text.charAt(start + offset));
    SuffixTreeNode midNode = new SuffixTreeNode(parent, start, offset, parent.fullLength + offset);
    midNode.children[midSymbol] = parent.children[startSymbol];
    parent.children[startSymbol].parent = midNode;
    parent.children[startSymbol].offset += offset;
    parent.children[startSymbol].partialLength -= offset;
    parent.children[startSymbol] = midNode;
    return midNode;
  }

  private static void writeOutput() {
    System.out.println(text);
    Stack<SuffixTreeNode> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      SuffixTreeNode node = stack.pop();
      for (int childIndex = ALPHABET.size() - 1; childIndex >= 0; childIndex--) {
        SuffixTreeNode child = node.children[childIndex];
        if (child != null && !child.visited) {
          stack.push(child);
        }
      }
      if (node != root) {
        System.out.println(node.offset + " " + (node.offset + node.partialLength));
      }
      node.visited = true;
    }
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    text = scanner.next();
    suffixArray = new int[text.length()];
    lpcArray = new int[text.length() - 1];
    for (int i = 0; i < text.length(); i++) {
      suffixArray[i] = scanner.nextInt();
    }
    for (int i = 0; i < text.length() - 1; i++) {
      lpcArray[i] = scanner.nextInt();
    }
    root = new SuffixTreeNode(null, 0, 0);
  }

  public static class SuffixTreeNode {

    SuffixTreeNode parent;

    SuffixTreeNode[] children;

    int offset;

    int partialLength;

    int fullLength;

    boolean visited;

    SuffixTreeNode(SuffixTreeNode parent, int offset, int partialLength) {
      this.parent = parent;
      this.children = new SuffixTreeNode[ALPHABET.size()];
      this.offset = offset;
      this.partialLength = partialLength;
      this.visited = false;
    }

    SuffixTreeNode(SuffixTreeNode parent, int offset, int partialLength, int fullLength) {
      this.parent = parent;
      this.children = new SuffixTreeNode[ALPHABET.size()];
      this.offset = offset;
      this.partialLength = partialLength;
      this.fullLength = fullLength;
      this.visited = false;
    }

    public boolean isLeaf() {
      for (SuffixTreeNode child : children) {
        if (child != null) {
          return false;
        }
      }
      return true;
    }

    @Override
    public String toString() {
      return "Node{" + text.substring(offset, offset + partialLength) + "}";
    }
  }
}
