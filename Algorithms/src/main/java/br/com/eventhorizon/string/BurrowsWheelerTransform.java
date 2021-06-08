package br.com.eventhorizon.string;

/**
 * Calculates the Burrows-Wheeler transform of a text.
 * The text should ends with '$', other wise it will be appended at the end.
 */
public interface BurrowsWheelerTransform {

  String transform(String text);
}
