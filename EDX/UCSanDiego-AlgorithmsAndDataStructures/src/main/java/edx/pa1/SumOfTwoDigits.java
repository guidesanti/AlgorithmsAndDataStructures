package edx.pa1;

import br.com.eventhorizon.common.pa.PA;

import java.util.Scanner;

public class SumOfTwoDigits implements PA {

  @Override
  public void naiveSolution() {
    Scanner s = new Scanner(System.in);
    int a = s.nextInt();
    int b = s.nextInt();
    System.out.println(a + b);
  }

  @Override
  public void finalSolution() {
    Scanner s = new Scanner(System.in);
    int a = s.nextInt();
    int b = s.nextInt();
    System.out.println(a + b);
  }
}
