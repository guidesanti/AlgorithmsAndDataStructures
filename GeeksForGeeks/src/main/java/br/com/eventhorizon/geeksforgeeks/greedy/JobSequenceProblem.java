package br.com.eventhorizon.geeksforgeeks.greedy;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import lombok.Data;

import java.util.*;

/**
 * Given an array of jobs where every job has a deadline and associated profit if the job is finished before the deadline.
 * It is also given that every job takes a single unit of time, so the minimum possible deadline for any job is 1.
 * Maximize the total profit if only one job can be scheduled at a time.
 */
public class JobSequenceProblem implements PA {

    private int n;

    private List<Job> jobs;

    private long maxProfit;

    private void readInput() {
        FastScanner scanner = new FastScanner(System.in);

        // Read n
        n = scanner.nextInt();

        // Read jobs
        jobs = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            jobs.add(new Job(scanner.nextInt(), scanner.nextInt()));
        }
    }

    private void writeOutput() {
        System.out.printf("%d", maxProfit);
    }

    @Override
    public void reset() {
        n = 0;
        jobs = null;
        maxProfit = 0;
    }

    @Override
    public void finalSolution() {
        readInput();
        finalSolutionImpl();
        writeOutput();
    }

    private void finalSolutionImpl() {
        var jobSchedule = new Job[n];

        // Sort the by profit in reverse order
        jobs.sort((o1, o2) -> o2.profit - o1.profit);

        // Schedule jobs
        for (var job : jobs) {
            for (var i = job.deadline - 1; i >= 0; i--) {
                if (jobSchedule[i] == null) {
                    jobSchedule[i] = job;
                    break;
                }
            }
        }

        // Calculate maximum profit
        Arrays.stream(jobSchedule).filter(Objects::nonNull).forEach(job -> maxProfit += job.getProfit());
    }

    @Data
    private static class Job {

        private final int deadline;

        private final int profit;
    }
}
