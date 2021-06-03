package br.com.eventhorizon.string.matching;

/**
 * Given two texts text1 and text2, find the shortest non-shared pattern between them.
 */
public interface ShortestNonSharedPattern {

  String find(String text1, String text2);
}
