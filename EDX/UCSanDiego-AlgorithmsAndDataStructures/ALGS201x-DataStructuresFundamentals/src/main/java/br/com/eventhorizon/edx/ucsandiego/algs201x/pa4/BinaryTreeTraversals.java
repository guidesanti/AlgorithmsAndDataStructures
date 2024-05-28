package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;

public class BinaryTreeTraversals implements PA {

  private static int n;

  private static int[] keys;

  private static int[] leftChildren;

  private static int[] rightChildren;

  @Override
  public void trivialSolution() {
    readInput();
    print(recursiveInOrder(new ArrayList<>(),0));
    print(recursivePreOrder(new ArrayList<>(), 0));
    print(recursivePostOrder(new ArrayList<>(), 0));
  }

  private static List<Integer> recursiveInOrder(List<Integer> l, int i) {
    if (i == -1) {
      return l;
    }
    recursiveInOrder(l, leftChildren[i]);
    l.add(keys[i]);
    recursiveInOrder(l, rightChildren[i]);
    return l;
  }

  private static List<Integer> recursivePreOrder(List<Integer> l, int i) {
    if (i == -1) {
      return l;
    }
    l.add(keys[i]);
    recursivePreOrder(l, leftChildren[i]);
    recursivePreOrder(l, rightChildren[i]);
    return l;
  }

  private static List<Integer> recursivePostOrder(List<Integer> l, int i) {
    if (i == -1) {
      return l;
    }
    recursivePostOrder(l, leftChildren[i]);
    recursivePostOrder(l, rightChildren[i]);
    l.add(keys[i]);
    return l;
  }

  @Override
  public void finalSolution() {
    readInput();
    print(iterativeInOrder());
    print(iterativePreOrder());
    print(iterativePostOrder());
  }

  private static List<Integer> iterativeInOrder() {
    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    boolean[] visited = new boolean[n];
    List<Integer> output = new ArrayList<>();
    while (!stack.isEmpty()) {
      int index = stack.pop();
      if (!visited[index]) {
        if (rightChildren[index] != -1 && !visited[rightChildren[index]]) {
          stack.push(rightChildren[index]);
        }
        if (leftChildren[index] != -1 && !visited[leftChildren[index]]) {
          stack.push(index);
          stack.push(leftChildren[index]);
        } else {
          output.add(keys[index]);
          visited[index] = true;
        }
      }
    }
    return output;
  }

  private static List<Integer> iterativePreOrder() {
    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    boolean[] visited = new boolean[n];
    List<Integer> output = new ArrayList<>();
    while (!stack.isEmpty()) {
      int index = stack.pop();
      if (!visited[index]) {
        output.add(keys[index]);
        visited[index] = true;
        if (rightChildren[index] != -1 && !visited[rightChildren[index]]) {
          stack.push(rightChildren[index]);
        }
        if (leftChildren[index] != -1 && !visited[leftChildren[index]]) {
          stack.push(leftChildren[index]);
        }
      }
    }
    return output;
  }

  private static List<Integer> iterativePostOrder() {
    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    boolean[] visited = new boolean[n];
    List<Integer> output = new ArrayList<>();
    while (!stack.isEmpty()) {
      int index = stack.peek();
      if (leftChildren[index] != -1 && !visited[leftChildren[index]]) {
        stack.push(leftChildren[index]);
      } else if (rightChildren[index] != -1 && !visited[rightChildren[index]]) {
        stack.push(rightChildren[index]);
      } else {
        output.add(keys[index]);
        visited[index] = true;
        stack.pop();
      }
    }
    return output;
  }

  private static void readInput() {
    FastScanner in = new FastScanner(System.in);
    n = in.nextInt();
    keys = new int[n];
    leftChildren = new int[n];
    rightChildren = new int[n];
    for (int i = 0; i < n; i++) {
      keys[i] = in.nextInt();
      leftChildren[i] = in.nextInt();
      rightChildren[i] = in.nextInt();
    }
  }

  public static void print(List<Integer> x) {
    StringJoiner str = new StringJoiner(" ");
    for (Integer a : x) {
      str.add("" + a);
    }
    System.out.println(str.toString());
  }
}
