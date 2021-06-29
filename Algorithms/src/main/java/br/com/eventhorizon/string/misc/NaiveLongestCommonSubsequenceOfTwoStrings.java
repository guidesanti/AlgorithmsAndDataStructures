package br.com.eventhorizon.string.misc;

public class NaiveLongestCommonSubsequenceOfTwoStrings implements LongestCommonSubsequenceOfTwoStrings {

  @Override
  public String find(String s1, String s2) {
    if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
      return "";
    }
    String shortest;
    String longest;
    if (s1.length() < s2.length()) {
      shortest = s1;
      longest = s2;
    } else {
      shortest = s2;
      longest = s1;
    }
    boolean[] deletions = new boolean[shortest.length()];
    String result = "";
    boolean stop = false;
    while (!stop) {
      StringBuilder subsequence = new StringBuilder();
      int shortestIndex = 0;
      int longestIndex = 0;
      while (shortestIndex < shortest.length() && longestIndex < longest.length()) {
        stop = false;
        while (deletions[shortestIndex]) {
          shortestIndex++;
          if (shortestIndex == deletions.length) {
            stop = true;
            break;
          }
        }
        if (stop) {
          break;
        }
        if (shortest.charAt(shortestIndex) == longest.charAt(longestIndex)) {
          subsequence.append(shortest.charAt(shortestIndex));
          shortestIndex++;
        }
        longestIndex++;
      }
      if (subsequence.length() > result.length()) {
        result = subsequence.toString();
      }
      next(deletions);
    }
    return result;
  }

  private void next(boolean[] deletions) {
    int count = 0;
    while (count < deletions.length && deletions[count]) {
      deletions[count] = false;
      count++;
    }
    if (count < deletions.length) {
      deletions[count] = true;
    }
  }
}
