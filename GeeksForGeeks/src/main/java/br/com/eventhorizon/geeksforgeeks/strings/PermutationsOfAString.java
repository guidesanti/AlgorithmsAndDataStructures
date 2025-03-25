package br.com.eventhorizon.geeksforgeeks.strings;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

/**
 * Given a string s, which may contain duplicate characters, your task is to generate and return an array of all unique
 * permutations of the string. You can return your answer in any order.
 */
public class PermutationsOfAString implements PA {

    private String string;

    private List<String> permutations;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read string
        string = scanner.next();
    }

    private void writeOutput() {
        var iterator = permutations.iterator();
        System.out.printf("%s", iterator.next());
        do {
            System.out.printf(" %s", iterator.next());
        } while (iterator.hasNext());
    }

    @Override
    public void reset() {
        string = null;
        permutations = null;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        var permutationsSet = new HashSet<String>();
        findPermutations(0, new boolean[string.length()], string, new StringBuilder(), permutationsSet);
        permutations = new ArrayList<>(permutationsSet).stream().sorted().toList();
    }

    private void findPermutations(int index, boolean[] used, String string, StringBuilder permutation, Set<String> permutations) {
        if (index == string.length()) {
            permutations.add(permutation.toString());
        }

        for (int i = 0; i < string.length(); i++) {
            if (!used[i]) {
                used[i] = true;
                permutation.append(string.charAt(i));
                findPermutations(index + 1, used, string, permutation, permutations);
                used[i] = false;
                permutation.deleteCharAt(permutation.length() - 1);
            }
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        trivialSolutionImpl();
    }
}
