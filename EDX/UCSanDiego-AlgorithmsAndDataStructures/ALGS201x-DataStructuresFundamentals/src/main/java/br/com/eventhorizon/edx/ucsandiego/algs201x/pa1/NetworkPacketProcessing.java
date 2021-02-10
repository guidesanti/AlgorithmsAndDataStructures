package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class NetworkPacketProcessing implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);

    int bufferCapacity = scanner.nextInt();
    Buffer buffer = new Buffer(bufferCapacity);

    List<Request> requests = readQueries(scanner);
    List<Response> responses = naiveProcessRequests(requests, buffer);
    printResponses(responses);
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);

    int bufferCapacity = scanner.nextInt();
    Buffer buffer = new Buffer(bufferCapacity);

    List<Request> requests = readQueries(scanner);
    List<Response> responses = finalSolutionProcessRequests(requests, buffer);
    printResponses(responses);
  }

  private static List<Request> readQueries(FastScanner scanner) {
    int requestsCount = scanner.nextInt();
    List<Request> requests = new ArrayList<>();
    for (int i = 0; i < requestsCount; ++i) {
      int arrivalTime = scanner.nextInt();
      int processTime = scanner.nextInt();
      requests.add(new Request(arrivalTime, processTime));
    }
    return requests;
  }

  private static List<Response> naiveProcessRequests(List<Request> requests, Buffer buffer) {
    List<Response> responses = new ArrayList<>();
    for (int i = 0; i < requests.size(); ++i) {
      responses.add(buffer.process1(requests.get(i)));
    }
    return responses;
  }

  private static List<Response> finalSolutionProcessRequests(List<Request> requests, Buffer buffer) {
    List<Response> responses = new ArrayList<>();
    for (int i = 0; i < requests.size(); ++i) {
      responses.add(buffer.process2(requests.get(i)));
    }
    return responses;
  }

  private static void printResponses(List<Response> responses) {
    for (int i = 0; i < responses.size(); ++i) {
      Response response = responses.get(i);
      if (response.dropped) {
        System.out.println(-1);
      } else {
        System.out.println(response.startTime);
      }
    }
  }

  private static class Request {

    public int arrivalTime;

    public int processTime;

    public Request(int arrivalTime, int processTime) {
      this.arrivalTime = arrivalTime;
      this.processTime = processTime;
    }
  }

  private static class Response {

    public boolean dropped;

    public int startTime;

    public Response(boolean dropped, int startTime) {
      this.dropped = dropped;
      this.startTime = startTime;
    }
  }

  private static class Buffer {

    private final int capacity;

    private final List<Integer> finishTimes;

    private int finishTime;

    public Buffer(int capacity) {
      this.capacity = capacity;
      this.finishTimes = new ArrayList<>();
      this.finishTime = 0;
    }

    public Response process1(Request request) {
      finishTimes.removeIf(v -> v <= request.arrivalTime);
      if (finishTimes.size() >= capacity) {
        return new Response(true, -1);
      }
      int oldFinishTime = finishTime;
      finishTime += request.processTime;
      finishTimes.add(finishTime);
      return new Response(false, Math.max(oldFinishTime, request.arrivalTime));
    }

    public Response process2(Request request) {
      while (!finishTimes.isEmpty() && finishTimes.get(0) <= request.arrivalTime) {
        finishTimes.remove(0);
      }
      if (finishTimes.size() >= capacity) {
        return new Response(true, -1);
      }
      int oldFinishTime = finishTime;
      finishTime += request.processTime;
      finishTimes.add(finishTime);
      return new Response(false, Math.max(oldFinishTime, request.arrivalTime));
    }
  }
}
