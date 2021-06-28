package br.com.eventhorizon.string.misc;

/**
 * Given two texts text1 and text2, find the shortest non-shared pattern from text1.
 * In other words find the shortest pattern that appears in text1 but not in text2.
 */
public interface ShortestNonSharedPattern {

  String find(String text1, String text2);
}
