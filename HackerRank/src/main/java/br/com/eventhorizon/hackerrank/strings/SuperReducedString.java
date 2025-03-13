package br.com.eventhorizon.hackerrank.strings;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Reduce a string of lowercase characters in range ascii[‘a’..’z’]by doing a series of operations. In each operation,
 * select a pair of adjacent letters that match, and delete them.
 * Delete as many characters as possible using this method and return the resulting string.
 * If the final string is empty, return Empty String
 * <p>
 * Example
 * <p>
 * s = 'aab'
 * aab shortens to b in one operation: remove the adjacent a characters.
 * <p>
 * s = 'abba'
 * Remove the two 'b' characters leaving 'aa'. Remove the two 'a' characters to leave ''. Return 'Empty String'.
 * <p>
 * Returns
 * string: the reduced string or Empty String
 * <p>
 * Input Format
 * A single string, s.
 * <p>
 * Constraints
 * 1 <= length of s <= 100
 */
public class SuperReducedString implements PA {

    private String string;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read input string
        string = scanner.next();
    }

    private void writeOutput() {
        System.out.printf(string.isEmpty() ? "Empty String" : string);
    }

    @Override
    public void reset() {
        string = null;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        var removed = false;
        do {
            removed = false;
            int n = string.length() - 1;
            for (int i = 0; i < n; i++) {
                if (string.charAt(i) == string.charAt(i + 1)) {
                    string = string.substring(0, i) + string.substring(i + 2);
                    n = string.length() - 1;
                    i--;
                    removed = true;
                }
            }
        } while (removed && !string.isEmpty());
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var sentinel = new Node('*');
        var first = sentinel;
        var last = first;
        for (int i = 0; i < string.length(); i++) {
            var next = new Node(string.charAt(i), first, last);
            last.next = next;
            first.prev = next;
            last = next;
        }
        first = first.next;

        var removed = false;
        do {
            removed = false;
            var current = first;
            while (current.next != sentinel) {
                if (current.value == current.next.value) {
                    if (current == first) {
                        first = current.next.next;
                    }
                    current.prev.next = current.next.next;
                    current.next.next.prev = current.prev;
                    current = current.next.next;
                    removed = true;
                } else {
                    current = current.next;
                }
            }
        } while (removed && first != sentinel);

        var current = first;
        if (current == sentinel) {
            string = "Empty String";
        } else {
            var stringBuilder = new StringBuilder();
            while (current != sentinel) {
                stringBuilder.append(current.value);
                current = current.next;
            }
            string = stringBuilder.toString();
        }
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    private static class Node {

        private final char value;

        Node next = this;

        Node prev = this;
    }
}
