package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MergingTables implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int maxNumberOfRows = Integer.MIN_VALUE;
    int numberOfTables = scanner.nextInt();
    int numberOfMerges = scanner.nextInt();
    Table[] tables = new Table[numberOfTables];
    for (int i = 0; i < numberOfTables; ++i) {
      int numberOfRows = scanner.nextInt();
      tables[i] = new Table(numberOfRows);
      maxNumberOfRows = Math.max(maxNumberOfRows, numberOfRows);
    }
    for (int i = 0; i < numberOfMerges; ++i) {
      maxNumberOfRows = Math.max(maxNumberOfRows, merge(tables[scanner.nextInt() - 1], tables[scanner.nextInt() - 1]));
      System.out.println(maxNumberOfRows);
    }
  }

  private static int merge(Table src, Table dst) {
    Table actualSrc = src.findParent();
    Table actualDst = dst.findParent();
    if (actualSrc == actualDst) {
      return Integer.MIN_VALUE;
    }
    int numberOfRows = actualSrc.numberOfRows + actualDst.numberOfRows;
    if (actualSrc.rank > actualDst.rank) {
      actualDst.parent = actualSrc;
      actualSrc.numberOfRows = numberOfRows;
      actualSrc.rank++;
    } else {
      actualSrc.parent = actualDst;
      actualDst.numberOfRows = numberOfRows;
      actualDst.rank++;
    }
    return numberOfRows;
  }

  private static class Table {

    Table parent;

    int rank;

    int numberOfRows;

    Table(int numberOfRows) {
      this.numberOfRows = numberOfRows;
      this.rank = 0;
      this.parent = this;
    }

    Table findParent() {
      if (this.parent.parent != this.parent) {
        this.parent = this.parent.findParent();
      }
      return this.parent;
    }
  }
}
