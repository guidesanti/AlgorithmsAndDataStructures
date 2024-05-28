package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class P1023 implements PA {

  private FastScanner scanner;

  public P1023() {
    Locale.setDefault(Locale.ENGLISH);
    reset();
  }

  @Override
  public void reset() {
    scanner = new FastScanner(System.in);
  }

  @Override
  public void finalSolution() {
    int cityCount = 0;
    int propertyCount = scanner.nextInt();
    while (propertyCount > 0) {
      cityCount++;
      int cityResidentCount = 0;
      int totalConsumption = 0;
      Map<Integer, Integer> consumptions = new HashMap<>();
      for (int i = 0; i < propertyCount; i++) {
        int propertyResidentCount = scanner.nextInt();
        int propertyConsumption = scanner.nextInt();
        int consumption = propertyConsumption / propertyResidentCount;
        int count = consumptions.getOrDefault(consumption, 0);
        count += propertyResidentCount;
        consumptions.put(consumption, count);
        cityResidentCount += propertyResidentCount;
        totalConsumption += propertyConsumption;
      }
      List<Integer> sortedKeys = consumptions.keySet().stream().sorted().collect(Collectors.toList());
      double averageConsumption = (double) totalConsumption / cityResidentCount;
      averageConsumption = ((int) (averageConsumption * 100)) / 100.0;
      System.out.println("Cidade# " + cityCount + ":");
      Integer key = sortedKeys.get(0);
      System.out.print(consumptions.get(key) + "-" + key);
      for (int i = 1; i < sortedKeys.size(); i++) {
        key = sortedKeys.get(i);
        System.out.print(" " + consumptions.get(key) + "-" + key);
      }
      System.out.println();
      System.out.printf("Consumo medio: %.2f m3.\n", averageConsumption);
      System.out.println();
      propertyCount = scanner.nextInt();
    }
  }
}
