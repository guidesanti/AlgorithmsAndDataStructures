package br.com.eventhorizon.edx.ucsandiego.algs201x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.PriorityQueue;

public class ParallelProcessing implements PA {

  private static int numberOfWorkers;

  private static int numberOfJobs;

  private static int[] jobs;

  private static int[] assignedWorker;

  private static long[] startTime;

  private static Worker[] workers;

  private static PriorityQueue<Worker> queue;

  @Override
  public void naiveSolution() {
    readInput();
    naiveAssignJobs();
    writeOutput();
  }

  private static void naiveAssignJobs() {
    assignedWorker = new int[numberOfJobs];
    startTime = new long[numberOfJobs];
    long[] nextFreeTime = new long[numberOfWorkers];
    for (int i = 0; i < jobs.length; i++) {
      int duration = jobs[i];
      int bestWorker = 0;
      for (int j = 0; j < numberOfWorkers; ++j) {
        if (nextFreeTime[j] < nextFreeTime[bestWorker])
          bestWorker = j;
      }
      assignedWorker[i] = bestWorker;
      startTime[i] = nextFreeTime[bestWorker];
      nextFreeTime[bestWorker] += duration;
    }
  }

  @Override
  public void finalSolution() {
    readInput();
    finalAssignJobs();
    writeOutput();
  }

  private static void finalAssignJobs() {
    assignedWorker = new int[numberOfJobs];
    startTime = new long[numberOfJobs];
    queue = new PriorityQueue<>((w1, w2) -> {
      if (w1.startTime != w2.startTime) {
        long a = w1.startTime - w2.startTime;
        if (a < 0) {
          return -1;
        }
        return 1;
      }
      return w1.number - w2.number;
    });

    workers = new Worker[numberOfWorkers];
    for (int i = 0; i < numberOfWorkers; i++) {
      workers[i] = new Worker(i, 0);
      queue.add(workers[i]);
    }

    for (int i = 0; i < numberOfJobs; i++) {
      Worker worker = queue.poll();
      assignedWorker[i] = worker.number;
      startTime[i] = worker.startTime;
      worker.startTime += jobs[i];
      queue.add(worker);
    }
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    numberOfWorkers = scanner.nextInt();
    numberOfJobs = scanner.nextInt();
    jobs = new int[numberOfJobs];
    for (int i = 0; i < numberOfJobs; ++i) {
      jobs[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    for (int i = 0; i < jobs.length; ++i) {
      System.out.println(assignedWorker[i] + " " + startTime[i]);
    }
  }

  private static class Worker {

    final int number;

    long startTime;

    public Worker(int number, long startTime) {
      this.number = number;
      this.startTime = startTime;
    }
  }
}
