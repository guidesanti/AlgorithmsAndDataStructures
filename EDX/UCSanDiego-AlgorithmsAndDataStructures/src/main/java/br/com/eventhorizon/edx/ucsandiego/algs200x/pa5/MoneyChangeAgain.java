package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MoneyChangeAgain implements PA {

  private static final int[] COINS = { 1, 3, 4 };

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    System.out.println(naiveMoneyChange(m));
  }

  private static int naiveMoneyChange(int m) {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < 1000; i++) {
      for (int j = 0; j < 334; j++) {
        for (int k = 0; k < 250; k++) {
          int change = i * COINS[0] + j * COINS[1] + k * COINS[2];
          int sum = i + j + k;
          if (change == m && sum < min) {
            min = sum;
          }
        }
      }
    }
    return min;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    int[] table = new int[m];
    System.out.println(finalMoneyChange(m, table));
  }

  private static int finalMoneyChange(int m, int[] table) {
    if (m == 0) {
      return 0;
    }
    int tableIndex = m - 1;
    if (table[tableIndex] != 0) {
      return table[tableIndex];
    }
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < COINS.length; i++) {
      if (m >= COINS[i]) {
        int numCoins = finalMoneyChange(m - COINS[i], table);
        if (numCoins + 1 < min) {
          min = numCoins + 1;
        }
      }
    }
    table[tableIndex] = min;
    return min;
  }
}
