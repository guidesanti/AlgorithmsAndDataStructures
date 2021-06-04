package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.datastructures.Alphabet;
import br.com.eventhorizon.common.datastructures.trees.SuffixTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SuffixTreeShortestNonSharedPattern implements ShortestNonSharedPattern {

  private int limit;

  @Override
  public String find(String text1, String text2) {
    limit = text1.length();
    String result = null;
    int min = Integer.MAX_VALUE;
    String text = text1 + "#" + text2;
    SuffixTree suffixTree = new SuffixTree(new Alphabet(), text);
    Map<SuffixTree.Node, SuffixTree.Node> parent = new HashMap<>();
    Queue<SuffixTree.Node> queue = new LinkedList<>();
    for (SuffixTree.Node node : suffixTree.root().next()) {
      if (node == null) {
        continue;
      }
      parent.put(node, suffixTree.root());
      queue.add(node);
    }
    while (!queue.isEmpty()) {
      SuffixTree.Node node = queue.remove();
      for (SuffixTree.Node child : node.next()) {
        if (child != null) {
          parent.put(child, node);
          queue.add(child);
        }
      }
      if (node.offset() >= limit) {
        continue;
      }
      if (!isShared(node)) {
        StringBuilder str = new StringBuilder();
        str.append(text.charAt(node.offset()));
        node = parent.get(node);
        while (node != suffixTree.root()) {
          str.insert(0, text.substring(node.offset(), node.offset() + node.length()));
          node = parent.get(node);
        }
        if (str.length() < min) {
          min = str.length();
          result = str.toString();
        }
      }
    }
    if (result != null && result.length() > text2.length()) {
      return null;
    }
    return result;
  }

  private boolean isShared(SuffixTree.Node node) {
    Shared shared = new Shared();
    isShared(node, shared);
    return shared.onText1 && shared.onText2;
  }

  private void isShared(SuffixTree.Node node, Shared shared) {
    for (SuffixTree.Node child : node.next()) {
      if (child == null) {
        continue;
      }
      if (child.isLeaf()) {
        if (child.offset() <= limit) {
          shared.onText1 = true;
        } else {
          shared.onText2 = true;
        }
      } else {
        isShared(child, shared);
      }
    }
  }

  private static class Shared {

    boolean onText1;

    boolean onText2;
  }
}
