package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings, S1 and S2, the task is to find the length of the Longest Common Subsequence,
 * i.e. longest subsequence present in both of the strings.
 * A longest common subsequence (LCS) is defined as the longest subsequence which is common in all given input sequences.
 */
public class LongestCommonSubsequence implements PA {

    private String s1;

    private String s2;

    private long lcsLength;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read s1 and s2
        s1 = scanner.next();
        s2 = scanner.next();
    }

    private void writeOutput() {
        System.out.printf("%s", lcsLength);
    }

    @Override
    public void reset() {
        s1 = null;
        s2 = null;
        lcsLength = 0;
    }

    @Override
    public void trivialSolution() {
        readInput();
        trivialSolutionImpl();
        writeOutput();
    }

    private void trivialSolutionImpl() {
        lcsLength = lcsLength(s1, s2, s1.length(), s2.length());
    }

    private long lcsLength(String s1, String s2, int m, int n) {
        if (m == 0 || n == 0) {
            return 0;
        }
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return 1 + lcsLength(s1, s2, m - 1, n - 1);
        } else {
            return Math.max(lcsLength(s1, s2, m - 1, n), lcsLength(s1, s2, m, n - 1));
        }
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
//        lcsLengthByMemoization();
        lcsLengthByTabulation();
    }

    private void lcsLengthByMemoization() {
        lcsLength = lcsLength(s1, s2, s1.length(), s2.length(), new HashMap<>());
    }

    private long lcsLength(String s1, String s2, int m, int n, Map<String, Long> cache) {
        if (m == 0 || n == 0) {
            return 0;
        }
        var key = m + ":" + n;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        long value;
        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            value = 1 + lcsLength(s1, s2, m - 1, n - 1, cache);
        } else {
            value = Math.max(lcsLength(s1, s2, m - 1, n, cache), lcsLength(s1, s2, m, n - 1, cache));
        }
        cache.put(key, value);
        return value;
    }

    private void lcsLengthByTabulation() {
        var dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 0;
        }
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (s1.charAt(i -1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        lcsLength = dp[s1.length()][s2.length()];
    }
}
