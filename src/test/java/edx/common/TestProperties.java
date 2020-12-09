package edx.common;

public class TestProperties {

  private static final String TIME_LIMIT_TEST_DURATION_PROPERTY_KEY = "timeLimitTestDuration";

  private static final String DEFAULT_TIME_LIMIT_TEST_DURATION_IN_MS = "15000";

  private static final int DEFAULT_TIME_LIMIT_IN_MS = 1500;

  private static final String STRESS_TEST_DURATION_PROPERTY_KEY = "stressTestDuration";

  private static final String DEFAULT_STRESS_TEST_DURATION_IN_MS = "15000";

  public static long getTimeLimitTestDuration() {
    return Long.parseLong(System.getProperty(TIME_LIMIT_TEST_DURATION_PROPERTY_KEY, DEFAULT_TIME_LIMIT_TEST_DURATION_IN_MS));
  }

  public static long getTimeLimit() {
    return DEFAULT_TIME_LIMIT_IN_MS;
  }

  public static long getStressTestDuration() {
    return Long.parseLong(System.getProperty(STRESS_TEST_DURATION_PROPERTY_KEY, DEFAULT_STRESS_TEST_DURATION_IN_MS));
  }
}
