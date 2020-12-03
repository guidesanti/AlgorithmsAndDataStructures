package edx.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FastScanner {

  private BufferedReader br;

  private StringTokenizer st;

  public FastScanner(InputStream stream) {
    try {
      br = new BufferedReader(new InputStreamReader(stream));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String next() {
    while (st == null || !st.hasMoreTokens()) {
      try {
        st = new StringTokenizer(br.readLine());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return st.nextToken();
  }

  public int nextInt() {
    return Integer.parseInt(next());
  }
}
