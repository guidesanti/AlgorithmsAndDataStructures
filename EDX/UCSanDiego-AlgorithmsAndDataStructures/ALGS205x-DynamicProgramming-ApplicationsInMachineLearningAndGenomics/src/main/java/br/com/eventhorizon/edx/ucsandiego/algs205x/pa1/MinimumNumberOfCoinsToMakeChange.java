package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MinimumNumberOfCoinsToMakeChange implements PA {

  private static int money;

  private static List<Integer> coins;

  @Override
  public void finalSolution() {
    readInput();
    int[] numberOfCoins = new int[Math.max(money + 1, coins.get(coins.size() - 1) + 1)];
    for (Integer coin : coins) {
      numberOfCoins[coin] = 1;
    }
    for (int i = 1; i <= money; i++) {
      for (Integer coin : coins) {
        int change = i + coin;
        int nCoins = numberOfCoins[i] + 1;
        if (change <= money && (numberOfCoins[change] == 0 || nCoins < numberOfCoins[change])) {
          numberOfCoins[change] = nCoins;
        }
        if (change == money) {
          break;
        }
      }
    }
    System.out.println(numberOfCoins[money]);
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    money = scanner.nextInt();
    String t = scanner.next();
    String[] a = t.split(",");
    coins = Arrays.stream(a).map(Integer::valueOf).sorted().collect(Collectors.toList());
  }
}
