package br.com.eventhorizon.common.pa.test;

import br.com.eventhorizon.common.pa.PA;
import br.com.eventhorizon.common.pa.PAMethodNotImplementedException;
import br.com.eventhorizon.common.pa.test.input.format.InputFormat;
import br.com.eventhorizon.common.pa.test.input.format.InputGenerator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.util.ExceptionUtils;
import org.opentest4j.AssertionFailedError;
import org.opentest4j.TestAbortedException;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(TimingExtension.class)
public abstract class PATestBase {

    private static final String ANSI_GREEN = "\u001B[32m";

    private static final String ANSI_YELLOW = "\u001B[33m";

    private static final String ANSI_RED = "\u001B[31m";

    private static final String ANSI_RESET = "\u001B[0m";

    protected final PA pa;

    protected final PATestSettings settings;

    protected final InputFormat inputFormat;

    protected OutputStream outputStream;

    protected PATestBase(PA pa) {
        this(pa, PATestSettings.defaultPATestSettings());
    }

    protected PATestBase(PA pa, PATestSettings settings) {
        this.pa = pa;
        this.settings = settings;
        this.inputFormat = settings.getInputFormatFile() != null
                ? InputFormat.parse(settings.getInputFormatFile()) : null;
        Locale.setDefault(Locale.US);
    }

    @Test
    public void trivialSolutionTestWithSimpleDataSet() {
        testSolutionWithSimpleDataSet(PASolution.TRIVIAL);
    }

    @Test
    public void finalSolutionTestWithSimpleDataSet() {
        testSolutionWithSimpleDataSet(PASolution.FINAL);
    }

    @Test
    public void memoryLimitTest() {
        skipIf(!settings.isMemoryLimitTestEnabled(), PATestType.MEMORY_LIMIT_TEST, "Test is disabled");

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        double memoryLimit = (double) settings.getMemoryLimit() / 1_000_000.0;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long initialUsedMemory = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < settings.getMemoryLimitTestDuration()) {
            // Generate input
            String input = generateInput(PATestType.MEMORY_LIMIT_TEST, null);

            // Run
            reset(input);
            pa.finalSolution();

            // Check memory
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            if (usedMemory - initialUsedMemory > settings.getMemoryLimit()) {
                String message = String.format("""
                    Memory limit test %d status: %s
                    Input: %s
                    Memory limit: %.2f MB
                    Memory used: %.2f MB""", count, Status.FAILED.getPrintableString(), input, memoryLimit, (double) usedMemory / 1_000_000);
                log.error(message);
                fail();
            }

            var usedMemoryString = String.format("%.2f", (double) usedMemory / 1000000);
            log.info("Memory limit test {} ({} MB) status: {}", count, usedMemoryString, Status.SUCCESS.getPrintableString());
            count++;
            runtime.gc();
        }
    }

    /**
     * Run a time limit test over the final solution by generating random input through the
     * generateInput() method and verify the time consumed by the execution.
     * This test does not verify if the output is correct, it does only verify the time consumed.
     */
    @Test
    public void timeLimitTest() {
        skipIf(!settings.isTimeLimitTestEnabled(), PATestType.TIME_LIMIT_TEST, "Test is disabled");

        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < settings.getTimeLimitTestDuration()) {
            // Generate input
            String input = generateInput(PATestType.TIME_LIMIT_TEST, null);

            // Run and verify time consumed
            long elapsed = 0;
            reset(input);
            try {
                elapsed = execute(pa::finalSolution, ofMillis(settings.getTimeLimit()));
            } catch (Exception ex) {
                var message = String.format("""
                        Time limit test %d status: %s
                        Input: %s
                        """,
                        count, Status.FAILED.getPrintableString(), input);
                log.error(message, ex);
                fail();
            }

            log.info("Time limit test {} ({} ms) status: {}", count, elapsed, Status.SUCCESS.getPrintableString());
            count++;
        }
    }

    /**
     * Run a stress test over the final solution by generating random input through the
     * generateInput() method and verify the output through the verify() method.
     */
    @Test
    public void stressTest() {
        skipIf(!settings.isStressTestEnabled(), PATestType.STRESS_TEST, "Test is disabled");

        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < settings.getStressTestDuration()) {
            // Generate input and expected output
            StringBuilder expectedOutputBuilder = new StringBuilder();
            var input = generateInput(PATestType.STRESS_TEST, expectedOutputBuilder);
            var expectedOutput = expectedOutputBuilder.toString();

            // Run final solution
            reset(input);
            pa.finalSolution();
            var actualOutput = getActualOutput();

            // Verify result
            var message = String.format("""
                        Stress test %d status: %s
                        Input: %s
                        Expected output: %s
                        Actual output: %s""",
                    count, Status.FAILED.getPrintableString(), input, expectedOutput, actualOutput);
            verify(input, expectedOutput, actualOutput, message);

            log.info("Stress test {} status: {}", count, Status.SUCCESS.getPrintableString());
            count++;
        }
    }

    /**
     * Run both the trivial solution and final solution and compare the results.
     * This test does not verify if the outputs are correct, it does only compare if they are equal.
     */
    @Test
    public void compareTest() {
        skipIf(!settings.isCompareTestEnabled(), PATestType.COMPARE_TEST, "Test is disabled");

        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < settings.getCompareTestDuration()) {
            // Generate input
            var input = generateInput(PATestType.COMPARE_TEST, null);

            // Run trivial solution
            reset(input);
            try {
                pa.trivialSolution();
            } catch (PAMethodNotImplementedException ex) {
                skip(PATestType.COMPARE_TEST, String.format("PA method '%s' is not implemented", ex.getMethod()));
            }
            var trivialSolutionOutput = getActualOutput();

            // Run final solution
            reset(input);
            pa.finalSolution();
            var finalSolutionOutput = getActualOutput();

            // Compare results
            var message = String.format("""
                        Compare test %d status: %s
                        Input: %s
                        Trivial solution output: %s
                        Final solution output: %s""",
                    count, Status.FAILED.getPrintableString(), input, trivialSolutionOutput, finalSolutionOutput);
            verify(input, trivialSolutionOutput, finalSolutionOutput, message);

            log.info("Compare test {} status: {}", count, Status.SUCCESS.getPrintableString());
            count++;
        }
    }

    private void skip(PATestType paTestType, String reason) {
        log.warn(String.format("%s: %s -> %s", paTestType.name(), Status.SKIPPED.getPrintableString(), reason));
        throw new TestAbortedException();
    }

    private void skipIf(boolean condition, PATestType paTestType, String reason) {
        if (condition) {
            skip(paTestType, reason);
        }
    }

    private void testSolutionWithSimpleDataSet(PASolution solution) {
        var paTestType = solution == PASolution.TRIVIAL
                ? PATestType.TRIVIAL_SOLUTION_WITH_SIMPLE_DATASET_TEST
                : PATestType.FINAL_SOLUTION_WITH_SIMPLE_DATASET_TEST;
        skipIf(StringUtils.isBlank(settings.getSimpleDataSetCsvFilePath()),
                paTestType, "Simple dataset CSV file path not provided");

        AtomicInteger count = new AtomicInteger();
        readPADataFromCsvFile(settings.getSimpleDataSetCsvFilePath()).forEach(paData -> {
            // Normalize input and expected output
            var input = paData.input
                    .replace(";", ",")
                    .replace("%", "\n");
            var expectedOutput = paData.expectedOutput
                    .replace(";", ",")
                    .replace("%", "\n").replace("!", "");

            // Run solution
            reset(input);
            try {
                if (solution == PASolution.TRIVIAL) {
                    pa.trivialSolution();
                } else {
                    pa.finalSolution();
                }
            } catch (PAMethodNotImplementedException ex) {
                skip(paTestType, String.format("PA method '%s' is not implemented", ex.getMethod()));
            }
            var actualOutput = getActualOutput();

            // Verify output
            var message = String.format("""
                        %s solution with simple dataset test %d status: %s
                        Input: %s
                        Expected output: %s
                        Actual output: %s""",
                    solution, count.get(), Status.FAILED.getPrintableString(), input, expectedOutput, actualOutput);
            verify(input, expectedOutput, getActualOutput(), message);

            log.info("{} solution with simple dataset test {} status: {}",
                    solution, count, Status.SUCCESS.getPrintableString());
            count.getAndIncrement();
        });
    }

    protected final void testSolution(PASolution solution, String input, String expectedOutput) {
        // Normalize input and output
        input = input.replace(";", ",").replace("%", "\n");
        expectedOutput = expectedOutput.replace(";", ",").replace("%", "\n").replace("!", "");
        reset(input);

        // Run solution
        if (solution == PASolution.TRIVIAL) {
            pa.trivialSolution();
        } else {
            pa.finalSolution();
        }
        var actualOutput = getActualOutput();

        // Verify output
        verify(input, expectedOutput, actualOutput, null);
    }

    protected final void testSolutionFromFile(PASolution solution, String inputFile, String expectedOutputFile) throws IOException {
        InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream(
                inputFile)));
        String input = new String(inputStream.readAllBytes()).trim();
        inputStream.close();
        inputStream = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream(
                expectedOutputFile)));
        String expectedOutput = new String(inputStream.readAllBytes()).trim();
        inputStream.close();
        reset(input);
        // Run test
        testSolution(solution, input, expectedOutput);
    }

    protected final String getActualOutput() {
        return outputStream.toString().trim();
    }

    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        skipIf(inputFormat == null, paTestType,
                "Method PATestBase.generateInput() is not override nor input format specification file was provided");
        return InputGenerator.generate(Objects.requireNonNull(inputFormat));
    }

    protected void verify(String input, String expectedOutput, String actualOutput) {
        assertNotNull(input, "Input is null");
        assertNotNull(expectedOutput, "Expected output is null");
        assertNotNull(actualOutput, "Actual output is null");
        assertEquals(expectedOutput, actualOutput, "Expected output does not match actual output");
    }

    private void verify(String input, String expectedOutput, String actualOutput, String message) {
        message = StringUtils.isNotBlank(message) ? message : "Expected output does not match actual output";
        assertNotNull(input, "Input is null");
        assertNotNull(expectedOutput, "Expected output is null");
        assertNotNull(actualOutput, "Actual output is null");
        try {
            verify(input, expectedOutput, actualOutput);
        } catch (AssertionFailedError ex) {
            log.error(message);
            throw ex;
        }
    }

    protected final void reset(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        pa.reset();
    }

    private static long execute(Executable executable, Duration timeout) {
        AtomicReference<Thread> threadReference = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor(new TimeoutThreadFactory());

        AtomicLong ini = new AtomicLong(System.currentTimeMillis());
        AtomicLong end = new AtomicLong();

        try {
            Future<Void> future = executorService.submit(() -> {
                try {
                    threadReference.set(Thread.currentThread());
                    ini.set(System.currentTimeMillis());
                    executable.execute();
                    end.set(System.currentTimeMillis());
                    return null;
                } catch (Throwable var3) {
                    throw ExceptionUtils.throwAsUncheckedException(var3);
                }
            });
            long timeoutInMillis = timeout.toMillis();

            try {
                future.get(timeoutInMillis, TimeUnit.MILLISECONDS);
            } catch (TimeoutException ex) {
                Thread thread = threadReference.get();
                if (thread != null) {
                    thread.interrupt();
                    ExecutionTimeoutException exception = new ExecutionTimeoutException("Execution timed out in thread " + thread.getName());
                    exception.setStackTrace(thread.getStackTrace());
                    throw exception;
                }
                throw new ExecutionTimeoutException("Execution timed");
            } catch (Throwable ex) {
                log.error(ex.getMessage(), ex);
                throw ExceptionUtils.throwAsUncheckedException(ex);
            }
        } finally {
            executorService.close();
            executorService.shutdown();
        }

        return end.get() - ini.get();
    }

    private List<PAData> readPADataFromCsvFile(String csvFilePath) {
        var paDataList = new ArrayList<PAData>();
        List<List<String>> records = new ArrayList<>();
        try (var csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Skip first line, it shall contain just the headers and not actual data
            csvReader.skip(1);
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                if (values.length != 2) {
                    throw new PACsvFormatException("CSV file contains invalid number of records, expected 2 per line, but found " + values.length);
                }
                paDataList.add(new PAData(values[0], values[1], null));
            }
            return paDataList;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TimeoutThreadFactory implements ThreadFactory {
        private static final AtomicInteger threadNumber = new AtomicInteger(1);

        private TimeoutThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            return new Thread(r, "pa-test-thread-" + threadNumber.getAndIncrement());
        }
    }

    private static class ExecutionTimeoutException extends RuntimeException {

        ExecutionTimeoutException(String message) {
            super(message);
        }
    }

    @Getter
    @RequiredArgsConstructor
    protected enum Status {

        SKIPPED(ANSI_YELLOW + "SKIPPED" + ANSI_RESET),
        ABORTED(ANSI_YELLOW + "ABORTED" + ANSI_RESET),
        FAILED(ANSI_RED + "FAILED" + ANSI_RESET),
        SUCCESS(ANSI_GREEN + "SUCCESS" + ANSI_RESET);

        private final String printableString;
    }

    @AllArgsConstructor
    private static class PAData {

        private String input;

        private String expectedOutput;

        private String actualOutput;
    }
}
