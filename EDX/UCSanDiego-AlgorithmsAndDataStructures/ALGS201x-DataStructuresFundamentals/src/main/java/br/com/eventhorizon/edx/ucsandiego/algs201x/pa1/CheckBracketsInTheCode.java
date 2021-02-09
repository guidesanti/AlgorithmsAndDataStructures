package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Stack;

public class CheckBracketsInTheCode implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();

    Stack<Bracket> openingBrackets = new Stack<>();
    for (int position = 0; position < text.length(); ++position) {
      char next = text.charAt(position);

      if (next == '(' || next == '[' || next == '{') {
        openingBrackets.push(new Bracket(next, position));
      }

      if (next == ')' || next == ']' || next == '}') {
        if (openingBrackets.isEmpty()) {
          System.out.println(position + 1);
          return;
        } else {
          Bracket openingBracket = openingBrackets.pop();
          if (!openingBracket.match(next)) {
            System.out.println(position + 1);
            return;
          }
        }
      }
    }
    if (openingBrackets.isEmpty()) {
      System.out.println("Success");
    } else {
      System.out.println(openingBrackets.pop().position + 1);
    }
  }

  @Override
  public void finalSolution() {
    naiveSolution();
  }

  private static class Bracket {

    char type;

    int position;

    Bracket(char type, int position) {
      this.type = type;
      this.position = position;
    }

    boolean match(char c) {
      if (this.type == '[' && c == ']')
        return true;
      if (this.type == '{' && c == '}')
        return true;
      if (this.type == '(' && c == ')')
        return true;
      return false;
    }
  }
}
