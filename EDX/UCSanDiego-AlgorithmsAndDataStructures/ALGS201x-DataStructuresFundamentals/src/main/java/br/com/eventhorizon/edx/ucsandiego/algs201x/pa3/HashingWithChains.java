package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HashingWithChains implements PA {

  private static final int PRIME = 1000000007;

  private static final int MULTIPLIER = 263;

  private static FastScanner in;

  private static PrintWriter out;

  private static int bucketCount;

  @Override
  public void trivialSolution() {
    in = new FastScanner(System.in);
    out = new PrintWriter(new BufferedOutputStream(System.out));
    bucketCount = in.nextInt();
    List<String> elements = new ArrayList<>();
    int queryCount = in.nextInt();
    for (int i = 0; i < queryCount; ++i) {
      naiveProcessQuery(elements, readQuery());
    }
    out.close();
  }

  private static void naiveProcessQuery(List<String> elements, Query query) {
    switch (query.type) {
      case "add":
        if (!elements.contains(query.string)) {
          elements.add(0, query.string);
        }
        break;
      case "del":
        elements.remove(query.string);
        break;
      case "find":
        writeSearchResult(elements.contains(query.string));
        break;
      case "check":
        StringJoiner str = new StringJoiner(" ");
        for (String element : elements) {
          if (hashFunc(element) == query.index) {
            str.add(element);
          }
        }
        out.println(str);
        break;
      default:
        throw new RuntimeException("Unknown query: " + query.type);
    }
  }

  @Override
  public void finalSolution() {
    in = new FastScanner(System.in);
    out = new PrintWriter(new BufferedOutputStream(System.out));
    bucketCount = in.nextInt();
    ArrayList[] buckets = new ArrayList[bucketCount];
    for (int i = 0; i < bucketCount; i++) {
      buckets[i] = new ArrayList<String>();
    }
    int queryCount = in.nextInt();
    for (int i = 0; i < queryCount; ++i) {
      finalProcessQuery(buckets, readQuery());
    }
    out.close();
  }

  private static void finalProcessQuery(List<String>[] buckets, Query query) {
    int bucketIndex;
    switch (query.type) {
      case "add":
        bucketIndex = hashFunc(query.string);
        if (!buckets[bucketIndex].contains(query.string)) {
          buckets[bucketIndex].add(0, query.string);
        }
        break;
      case "del":
        bucketIndex = hashFunc(query.string);
        buckets[bucketIndex].remove(query.string);
        break;
      case "find":
        bucketIndex = hashFunc(query.string);
        writeSearchResult(buckets[bucketIndex].contains(query.string));
        break;
      case "check":
        StringJoiner str = new StringJoiner(" ");
        for (String element : buckets[query.index]) {
          if (hashFunc(element) == query.index) {
            str.add(element);
          }
        }
        out.println(str);
        break;
      default:
        throw new RuntimeException("Unknown query: " + query.type);
    }
  }

  private static Query readQuery() {
    String type = in.next();
    if (!type.equals("check")) {
      String s = in.next();
      return new Query(type, s);
    } else {
      int ind = in.nextInt();
      return new Query(type, ind);
    }
  }

  private static void writeSearchResult(boolean wasFound) {
    out.println(wasFound ? "yes" : "no");
  }

  private static int hashFunc(String string) {
    long hash = 0;
    for (int i = string.length() - 1; i >= 0; --i)
      hash = (hash * MULTIPLIER + string.charAt(i)) % PRIME;
    return (int) hash % bucketCount;
  }

  private static class Query {

    String type;

    String string;

    int index;

    public Query(String type, String string) {
      this.type = type;
      this.string = string;
    }

    public Query(String type, int index) {
      this.type = type;
      this.index = index;
    }
  }
}
