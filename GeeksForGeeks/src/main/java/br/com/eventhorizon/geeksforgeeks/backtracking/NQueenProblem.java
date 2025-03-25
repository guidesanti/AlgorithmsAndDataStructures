package br.com.eventhorizon.geeksforgeeks.backtracking;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

/**
 * The n-queens puzzle is the problem of placing n queens on a (n × n) chessboard such that no two queens can attack
 * each other. Note that two queens attack each other if they are placed on the same row, the same column,
 * or the same diagonal.
 * <p>
 * Given an integer n, find all distinct solutions to the n-queens puzzle.
 * You can return your answer in any order but each solution should represent a distinct board configuration of the
 * queen placements, where the solutions are represented as permutations of [1, 2, 3, ..., n]. In this representation,
 * the number in the ith position denotes the row in which the queen is placed in the ith column.
 */
public class NQueenProblem implements PA {

    private int n;

    private List<List<Integer>> solutions;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n
        n = scanner.nextInt();
    }

    private void writeOutput() {
        if (solutions.isEmpty()) {
            return;
        }
        var iterator = solutions.iterator();
        printSolution(iterator.next());
        while (iterator.hasNext()) {
            System.out.print(",");
            printSolution(iterator.next());
        }
    }

    void printSolution(List<Integer> solution) {
        var iterator = solution.iterator();
        System.out.printf("%d", iterator.next());
        while (iterator.hasNext()) {
            System.out.printf(" %d", iterator.next());
        }
    }

    @Override
    public void reset() {
        n = 0;
        solutions = new ArrayList<>();
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        var board = new int[n];
        var stack = new Stack<Queen>();

        Arrays.fill(board, -1);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (isSafe(board, row, col)) {
                    board[col] = row;
                    if (stack.size() < n - 1) {
                        stack.push(new Queen(row, col));
                        break;
                    } else {
                        addSolution(solutions, board);
                        board[col] = -1;
                        if (!stack.isEmpty()) {
                            var queen = stack.pop();
                            row = queen.row;
                            col = queen.col;
                            board[col] = -1;
                        }
                    }
                }
                while (col == n - 1 && !stack.isEmpty()) {
                    var queen = stack.pop();
                    row = queen.row;
                    col = queen.col;
                    board[col] = -1;
                }
            }
        }
    }

    private boolean isSafe(int[] board, int row, int col) {
        if (board[col] != -1) {
            return false;
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == row) {
                return false;
            }
            if (board[i] != -1) {
                if ((row - col) == (board[i] - i)) {
                    return false;
                }
                if ((row + col) == (board[i] + i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addSolution(List<List<Integer>> solutions, int[] board) {
        var solution = new ArrayList<Integer>();
        for (var row : board) {
            solution.add(row + 1);
        }
        solutions.add(solution);
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

    private record Queen(int row, int col) {
    }
}
