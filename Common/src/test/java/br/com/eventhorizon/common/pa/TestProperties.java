package br.com.eventhorizon.common.pa;

public class TestProperties {

  public static final String TIME_LIMIT_TEST_DURATION_PROPERTY_KEY = "timeLimitTestDuration";

  public static final String DEFAULT_TIME_LIMIT_TEST_DURATION_IN_MS = "15000";

  public static final String TIME_LIMIT_IN_MS_PROPERTY_KEY = "timeLimit";

  public static final String DEFAULT_TIME_LIMIT_IN_MS = "1500";

  public static final String STRESS_TEST_DURATION_PROPERTY_KEY = "stressTestDuration";

  public static final String DEFAULT_STRESS_TEST_DURATION_IN_MS = "15000";

  public static long getTimeLimitTestDuration() {
    return Long.parseLong(System.getProperty(TIME_LIMIT_TEST_DURATION_PROPERTY_KEY, DEFAULT_TIME_LIMIT_TEST_DURATION_IN_MS));
  }

  public static void setTimeLimitTestDuration(int timeLimitDuration) {
    System.setProperty(TIME_LIMIT_TEST_DURATION_PROPERTY_KEY, Integer.toString(timeLimitDuration));
  }

  public static long getTimeLimit() {
    return Long.parseLong(System.getProperty(TIME_LIMIT_IN_MS_PROPERTY_KEY, DEFAULT_TIME_LIMIT_IN_MS));
  }

  public static void setTimeLimit(int timeLimit) {
    System.setProperty(TIME_LIMIT_IN_MS_PROPERTY_KEY, Integer.toString(timeLimit));
  }

  public static long getStressTestDuration() {
    return Long.parseLong(System.getProperty(STRESS_TEST_DURATION_PROPERTY_KEY, DEFAULT_STRESS_TEST_DURATION_IN_MS));
  }

  public static void setStressTestDuration(int stressTestDuration) {
    System.setProperty(STRESS_TEST_DURATION_PROPERTY_KEY, Integer.toString(stressTestDuration));
  }
}
