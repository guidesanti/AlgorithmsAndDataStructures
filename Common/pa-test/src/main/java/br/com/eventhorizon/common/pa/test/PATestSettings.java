package br.com.eventhorizon.common.pa.test;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PATestSettings {

  private String simpleDataSetCsvFilePath;

  @Builder.Default
  private boolean memoryLimitTestEnabled = Defaults.MEMORY_USAGE_TEST_ENABLED;

  @Builder.Default
  private long memoryLimitTestDuration = Defaults.MEMORY_LIMIT_TEST_DURATION;

  @Builder.Default
  private long memoryLimit = Defaults.MEMORY_LIMIT;

  @Builder.Default
  private boolean timeLimitTestEnabled = Defaults.TIME_LIMIT_TEST_ENABLED;

  @Builder.Default
  private long timeLimitTestDuration = Defaults.TIME_LIMIT_TEST_DURATION;

  @Builder.Default
  private long timeLimit = Defaults.TIME_LIMIT;

  @Builder.Default
  private boolean stressTestEnabled = Defaults.STRESS_TEST_ENABLED;

  @Builder.Default
  private long stressTestDuration = Defaults.STRESS_TEST_DURATION;

  @Builder.Default
  private boolean compareTestEnabled = Defaults.COMPARE_TEST_ENABLED;

  @Builder.Default
  private long compareTestDuration = Defaults.COMPARE_TEST_DURATION;

  @Builder.Default
  private String inputFormatFile = null;

  public static PATestSettings defaultPATestSettings() {
    return PATestSettings.builder().build();
  }
}
