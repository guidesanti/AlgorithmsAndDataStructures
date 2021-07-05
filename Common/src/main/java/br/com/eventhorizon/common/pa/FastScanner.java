package br.com.eventhorizon.common.pa;

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
        String line = br.readLine();
        if (line == null) {
          return null;
        }
        st = new StringTokenizer(line);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return st.nextToken();
  }

  public char nextChar() {
    return next().charAt(0);
  }

  public int nextInt() {
    return Integer.parseInt(next());
  }

  public long nextLong() {
    return Long.parseLong(next());
  }
}
