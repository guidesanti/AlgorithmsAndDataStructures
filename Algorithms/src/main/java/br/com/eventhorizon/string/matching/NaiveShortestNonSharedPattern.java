package br.com.eventhorizon.string.matching;

public class NaiveShortestNonSharedPattern implements ShortestNonSharedPattern {

  @Override
  public String find(String text1, String text2) {
    int min = Math.min(text1.length(), text2.length());
    for (int subStringLength = 1; subStringLength <= min; subStringLength++) {
      for (int subStringIndex1 = 0; subStringIndex1 <= text1.length() - subStringLength; subStringIndex1++) {
        int index = subStringIndex1;
        for (int subStringIndex2 = 0; subStringIndex2 <= text2.length() - subStringLength; subStringIndex2++) {
          int offset = 0;
          while (offset < subStringLength && text1.charAt(subStringIndex1 + offset) == text2.charAt(subStringIndex2 + offset)) {
            offset++;
          }
          if (offset == subStringLength) {
            index = -1;
            break;
          }
        }
        if (index >= 0) {
          return text1.substring(index, index + subStringLength);
        }
      }
    }
    return null;
  }
}
