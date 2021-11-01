package br.com.eventhorizon.common.pa.v2;

public class PATestSettings {

  private boolean memoryLimitTestEnabled;

  private long memoryLimitTestDuration;

  private int memoryLimit;

  private boolean timeLimitTestEnabled;

  private long timeLimitTestDuration;

  private long timeLimit;

  private boolean stressTestEnabled;

  private long stressTestDuration;

  private boolean compareTestEnabled;

  private long compareTestDuration;

  private String inputFormatFile;

  private PATestSettings() {
    this.memoryLimitTestEnabled = Defaults.MEMORY_USAGE_TEST_ENABLED;
    this.memoryLimitTestDuration = Defaults.MEMORY_LIMIT_TEST_DURATION;
    this.memoryLimit = Defaults.MEMORY_LIMIT;
    this.timeLimitTestEnabled = Defaults.TIME_LIMIT_TEST_ENABLED;
    this.timeLimitTestDuration = Defaults.TIME_LIMIT_TEST_DURATION;
    this.timeLimit = Defaults.TIME_LIMIT;
    this.stressTestEnabled = Defaults.STRESS_TEST_ENABLED;
    this.stressTestDuration = Defaults.STRESS_TEST_DURATION;
    this.compareTestEnabled = Defaults.COMPARE_TEST_ENABLED;
    this.compareTestDuration = Defaults.COMPARE_TEST_DURATION;
    this.inputFormatFile = null;
  }

  private PATestSettings(PATestSettings settings) {
    this.memoryLimitTestEnabled = settings.memoryLimitTestEnabled;
    this.memoryLimitTestDuration = settings.memoryLimitTestDuration;
    this.memoryLimit = settings.memoryLimit;
    this.timeLimitTestEnabled = settings.timeLimitTestEnabled;
    this.timeLimitTestDuration = settings.timeLimitTestDuration;
    this.timeLimit = settings.timeLimit;
    this.stressTestEnabled = settings.stressTestEnabled;
    this.stressTestDuration = settings.stressTestDuration;;
    this.compareTestEnabled = settings.compareTestEnabled;
    this.compareTestDuration = settings.compareTestDuration;
    this.inputFormatFile = settings.inputFormatFile;
  }

  public boolean isMemoryLimitTestEnabled() {
    return memoryLimitTestEnabled;
  }

  public long getMemoryLimitTestDuration() {
    return memoryLimitTestDuration;
  }

  public int getMemoryLimit() {
    return memoryLimit;
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

    public Builder memoryLimitTestEnabled(boolean memoryLimitTestEnabled) {
      this.settings.memoryLimitTestEnabled = memoryLimitTestEnabled;
      return this;
    }

    public Builder memoryLimitTestDuration(long memoryLimitTestDuration) {
      this.settings.memoryLimitTestDuration = memoryLimitTestDuration;
      return this;
    }

    public Builder memoryLimit(int memoryLimit) {
      this.settings.memoryLimit = memoryLimit;
      return this;
    }

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
