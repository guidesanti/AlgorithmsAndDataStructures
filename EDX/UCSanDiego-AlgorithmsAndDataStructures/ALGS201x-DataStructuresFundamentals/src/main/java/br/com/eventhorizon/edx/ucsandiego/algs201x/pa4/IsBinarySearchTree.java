package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Stack;

public class IsBinarySearchTree implements PA {

  private static int n;

  private static int[] keys;

  private static int[] leftChildren;

  private static int[] rightChildren;

  private static int lastVisited;

  @Override
  public void trivialSolution() {
    readInput();
    lastVisited = Integer.MIN_VALUE;
    System.out.println(recursiveInOrder(0) ? "CORRECT" : "INCORRECT");
  }

  private static boolean recursiveInOrder(int i) {
    if (n == 0) {
      return true;
    }
    if (i == -1) {
      return true;
    }
    boolean r = recursiveInOrder(leftChildren[i]);
    if (keys[i] < lastVisited) {
      return false;
    }
    lastVisited = keys[i];
    r &= recursiveInOrder(rightChildren[i]);
    return r;
  }

  @Override
  public void finalSolution() {
    readInput();
    System.out.println(iterativeInOrder() ? "CORRECT" : "INCORRECT");
  }

  private static boolean iterativeInOrder() {
    if (n == 0) {
      return true;
    }
    Stack<Integer> stack = new Stack<>();
    stack.push(0);
    boolean[] visited = new boolean[n];
    lastVisited = Integer.MIN_VALUE;
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
          visited[index] = true;
          if (keys[index] < lastVisited) {
            return false;
          }
          lastVisited = keys[index];
        }
      }
    }
    return true;
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
}
