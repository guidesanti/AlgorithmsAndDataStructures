package br.com.eventhorizon.common.pa.v2;

public class PATestSettings {

  private static final boolean DEFAULT_TIME_LIMIT_TEST_ENABLED = false;

  public static final long DEFAULT_TIME_LIMIT_TEST_DURATION = 15000L;

  public static final long DEFAULT_TIME_LIMIT = 1500L;

  private static final boolean DEFAULT_STRESS_TEST_ENABLED = false;

  public static final long DEFAULT_STRESS_TEST_DURATION = 15000L;

  private static final boolean DEFAULT_COMPARE_TEST_ENABLED = false;

  public static final long DEFAULT_COMPARE_TEST_DURATION = 15000L;

  private boolean timeLimitTestEnabled;

  private long timeLimitTestDuration;

  private long timeLimit;

  private boolean stressTestEnabled;

  private long stressTestDuration;

  private boolean compareTestEnabled;

  private long compareTestDuration;

  private String inputFormatFile;

  private PATestSettings() {
    this.timeLimitTestEnabled = DEFAULT_TIME_LIMIT_TEST_ENABLED;
    this.timeLimitTestDuration = DEFAULT_TIME_LIMIT_TEST_DURATION;
    this.timeLimit = DEFAULT_TIME_LIMIT;
    this.stressTestEnabled = DEFAULT_STRESS_TEST_ENABLED;
    this.stressTestDuration = DEFAULT_STRESS_TEST_DURATION;
    this.compareTestEnabled = DEFAULT_COMPARE_TEST_ENABLED;
    this.compareTestDuration = DEFAULT_COMPARE_TEST_DURATION;
    this.inputFormatFile = null;
  }

  private PATestSettings(PATestSettings settings) {
    this.timeLimitTestEnabled = settings.timeLimitTestEnabled;
    this.timeLimitTestDuration = settings.timeLimitTestDuration;
    this.timeLimit = settings.timeLimit;
    this.stressTestEnabled = settings.stressTestEnabled;
    this.stressTestDuration = settings.stressTestDuration;;
    this.compareTestEnabled = settings.compareTestEnabled;
    this.compareTestDuration = settings.compareTestDuration;
    this.inputFormatFile = settings.inputFormatFile;
  }

  public boolean isTimeLimitTestEnabled() {
    return timeLimitTestEnabled;
  }

  public long getTimeLimitTestDuration() {
    return timeLimitTestDuration;
  }

  public long getTimeLimit() {
    return timeLimit;
  }

  public boolean isStressTestEnabled() {
    return stressTestEnabled;
  }

  public long getStressTestDuration() {
    return stressTestDuration;
  }

  public boolean isCompareTestEnabled() {
    return compareTestEnabled;
  }

  public long getCompareTestDuration() {
    return compareTestDuration;
  }

  public String getInputFormatFile() {
    return inputFormatFile;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final PATestSettings settings = new PATestSettings();

    private Builder() { }

    public Builder timeLimitTestEnabled(boolean timeLimitTestEnabled) {
      this.settings.timeLimitTestEnabled = timeLimitTestEnabled;
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

    public Builder stressTestEnabled(boolean stressTestEnabled) {
      this.settings.stressTestEnabled = stressTestEnabled;
      return this;
    }

    public Builder stressTestDuration(long stressTestDuration) {
      this.settings.stressTestDuration = stressTestDuration;
      return this;
    }

    public Builder compareTestEnabled(boolean compareTestEnabled) {
      this.settings.compareTestEnabled = compareTestEnabled;
      return this;
    }

    public Builder compareTestDuration(long compareTestDuration) {
      this.settings.compareTestDuration = compareTestDuration;
      return this;
    }

    public Builder inputFormatFile(String inputFormatFile) {
      this.settings.inputFormatFile = inputFormatFile;
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
