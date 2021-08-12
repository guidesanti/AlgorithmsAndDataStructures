package br.com.eventhorizon.common.pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FastScanner {

  private String delimiters = null;

  private BufferedReader br;

  private StringTokenizer st;

  public FastScanner(InputStream stream) {
    try {
      br = new BufferedReader(new InputStreamReader(stream));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public FastScanner(InputStream stream, String delimiters) {
    this(stream);
    this.delimiters = delimiters;
  }

  public String next() {
    while (st == null || !st.hasMoreTokens()) {
      try {
        String line = br.readLine();
        if (line == null) {
          return null;
        }
        if (delimiters == null) {
          st = new StringTokenizer(line);
        } else {
          st = new StringTokenizer(line, delimiters, false);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return st.nextToken();
  }

  public Character nextRawChar() {
    try {
      int ch = br.read();
      if (ch == -1) {
        return null;
      }
      return (char) ch;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
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

  public double nextDouble() {
    return Double.parseDouble(next());
  }
}
