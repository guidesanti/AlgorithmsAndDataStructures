package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.io.IOException;

public class P1236 implements PA {

  private FastScanner scanner;

  @Override
  public void reset() {
    scanner = new FastScanner(System.in);
  }

  @Override
  public void finalSolution() {
    reset();
    int testCaseCount = scanner.nextInt();
    while (testCaseCount-- > 0) {
      try {
        processTestCase();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void processTestCase() throws IOException {
    byte state = 0;
    int count = 0;
    StringBuilder str = new StringBuilder();
    byte[] output = new byte[2000];
    int outputLength = 0;
    Character prevSymbol = scanner.nextRawChar();
    Character currSymbol;
    if (prevSymbol == '0' || prevSymbol == ' ') {
      count = 1;
      state = 1;
    } else {
      output[outputLength++] = (byte) prevSymbol.charValue();
      str.append(prevSymbol);
    }
    while (true) {
      currSymbol = scanner.nextRawChar();
      if (currSymbol == null || currSymbol == '\n') {
        break;
      }
      if (state == 0) {
        if (currSymbol == '0' || currSymbol == ' ') {
          count = 1;
          state = 1;
        } else {
          output[outputLength++] = (byte) currSymbol.charValue();
          str.append(currSymbol);
        }
      } else {
        if (currSymbol == prevSymbol) {
          count++;
          if (count == 255) {
            count = 0;
            output[outputLength++] = currSymbol == '0' ? (byte) '#' : (byte) '$';
            output[outputLength++] = (byte) 255;
            str.append(currSymbol == '0' ? '#' : '$');
            str.append((char) 255);
          }
        } else {
          if (count <= 2) {
            for (int i = 0; i < count; i++) {
              output[outputLength++] = (byte) prevSymbol.charValue();
              str.append(prevSymbol);
            }
          } else {
            output[outputLength++] = prevSymbol == '0' ? (byte) '#' : (byte) '$';
            output[outputLength++] = (byte) count;
            str.append(prevSymbol == '0' ? '#' : '$');
            str.append((char) count);
          }
          if (currSymbol == '0' || currSymbol == ' ') {
            count = 1;
          } else {
            output[outputLength++] = (byte) currSymbol.charValue();
            str.append(currSymbol);
            state = 0;
          }
        }
      }
      prevSymbol = currSymbol;
    }
    if (state == 1) {
      if (count <= 2) {
        for (int i = 0; i < count; i++) {
          output[outputLength++] = (byte) prevSymbol.charValue();
          str.append(prevSymbol);
        }
      } else {
        output[outputLength++] = prevSymbol == '0' ? (byte) '#' : (byte) '$';
        output[outputLength++] = (byte) count;
        str.append(prevSymbol == '0' ? '#' : '$');
        str.append((char) count);
      }
    }
    output[outputLength++] = (byte) '\n';
    // Use System.out.println() for testing and System.out.write() for submission
//    System.out.write(output, 0, outputLength);
    System.out.println(str);
  }
}
