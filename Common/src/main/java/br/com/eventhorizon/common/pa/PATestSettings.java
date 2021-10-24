package br.com.eventhorizon.common.pa;

import br.com.eventhorizon.common.pa.format.InputFormat;

public class PATestSettings {

  private static final boolean DEFAULT_SKIP_TIME_LIMIT_TEST = true;

  public static final long DEFAULT_TIME_LIMIT_TEST_DURATION = 15000L;

  public static final long DEFAULT_TIME_LIMIT = 1500L;

  private static final boolean DEFAULT_SKIP_STRESS_TEST = true;

  public static final long DEFAULT_STRESS_TEST_DURATION = 15000L;

  private static final boolean DEFAULT_SKIP_COMPARE_TEST = true;

  public static final long DEFAULT_COMPARE_TEST_DURATION = 15000L;

  private boolean skipTimeLimitTest;

  private long timeLimitTestDuration;

  private long timeLimit;

  private boolean skipStressTest;

  private long stressTestDuration;

  private boolean skipCompareTest;

  private long compareTestDuration;

  private InputFormat inputFormat;

  private PATestSettings() {
    this.skipTimeLimitTest = DEFAULT_SKIP_TIME_LIMIT_TEST;
    this.timeLimitTestDuration = DEFAULT_TIME_LIMIT_TEST_DURATION;
    this.timeLimit = DEFAULT_TIME_LIMIT;
    this.skipStressTest = DEFAULT_SKIP_STRESS_TEST;
    this.stressTestDuration = DEFAULT_STRESS_TEST_DURATION;
    this.skipCompareTest = DEFAULT_SKIP_COMPARE_TEST;
    this.compareTestDuration = DEFAULT_COMPARE_TEST_DURATION;
    this.inputFormat = null;
  }

  private PATestSettings(PATestSettings settings) {
    this.skipTimeLimitTest = settings.skipTimeLimitTest;
    this.timeLimitTestDuration = settings.timeLimitTestDuration;
    this.timeLimit = settings.timeLimit;
    this.skipStressTest = settings.skipStressTest;
    this.stressTestDuration = settings.stressTestDuration;;
    this.skipCompareTest = settings.skipCompareTest;
    this.compareTestDuration = settings.compareTestDuration;
    this.inputFormat = settings.inputFormat;
  }

  public boolean isSkipTimeLimitTest() {
    return skipTimeLimitTest;
  }

  public long getTimeLimitTestDuration() {
    return timeLimitTestDuration;
  }

  public long getTimeLimit() {
    return timeLimit;
  }

  public boolean isSkipStressTest() {
    return skipStressTest;
  }

  public long getStressTestDuration() {
    return stressTestDuration;
  }

  public boolean isSkipCompareTest() {
    return skipCompareTest;
  }

  public long getCompareTestDuration() {
    return compareTestDuration;
  }

  public InputFormat getInputFormat() {
    return inputFormat;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final PATestSettings settings = new PATestSettings();

    private Builder() { }

    public Builder skipTimeLimitTest(boolean skipTimeLimitTest) {
      this.settings.skipTimeLimitTest = skipTimeLimitTest;
      return this;
    }

    public Builder timeLimitTestDuration(long timeLimitTestDuration) {
      this.settings.timeLimitTestDuration = timeLimitTestDuration;
      return this;
    }

    public Builder timeLimit(long timeLimit) {
      this.settings.timeLimit = timeLimit;
      return this;
    }

    public Builder skipStressTest(boolean skipStressTest) {
      this.settings.skipStressTest = skipStressTest;
      return this;
    }

    public Builder stressTestDuration(long stressTestDuration) {
      this.settings.stressTestDuration = stressTestDuration;
      return this;
    }

    public Builder skipCompareTest(boolean skipCompareTest) {
      this.settings.skipCompareTest = skipCompareTest;
      return this;
    }

    public Builder compareTestDuration(long compareTestDuration) {
      this.settings.compareTestDuration = compareTestDuration;
      return this;
    }

    public Builder inputFormat(InputFormat inputFormat) {
      this.settings.inputFormat = inputFormat;
      return this;
    }

    public PATestSettings build() {
      return new PATestSettings(this.settings);
    }

    public static PATestSettings defaultSettings() {
      return new Builder().build();
    }
  }
}
