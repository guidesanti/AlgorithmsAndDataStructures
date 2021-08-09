package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class GenerateContigsFromACollectionOfReads implements PA {

  private static int k;

  private static String text;

  private static String[] kMers;

  private static void init() {
    k = 0;
    text = "";
    kMers = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read k
    k = scanner.nextInt();

    // Read text
    text = scanner.next();
  }

  private static void postReadInputInit() {
    kMers = new String[text.length() - k + 1];
  }

  private static void writeOutput() {
    for (String kMer : kMers) {
      System.out.println(kMer);
    }
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
    for (int i = 0; i < kMers.length; i++) {
      kMers[i] = text.substring(i, i + k);
    }
  }
}
