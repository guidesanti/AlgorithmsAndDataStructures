package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class ReconstructStringFromItsGenomePath implements PA {

  private static List<String> kMers;

  private static String text;

  private static void init() {
    kMers = new ArrayList<>();
    text = "";
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read K-mers
    String str = scanner.next();
    while (str != null) {
      kMers.add(str);
      str = scanner.next();
    }
  }

  private static void postReadInputInit() {
    // Do nothing
  }

  private static void writeOutput() {
    System.out.println(text);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    postReadInputInit();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    StringBuffer str = new StringBuffer();
    String firstKMers = kMers.get(0);
    int k = firstKMers.length();
    str.append(kMers.get(0));
    for (int i = 1; i < kMers.size(); i++) {
      str.append(kMers.get(i).charAt(k - 1));
    }
    text = str.toString();
  }
}
