package br.com.eventhorizon.common.pa.v2;

public class PATestProperties {

  public static final String TIME_LIMIT_TEST_ENABLED_PROPERTY_KEY = "br.com.eventhorizon.common.pa.timeLimitTestEnabled";

  public static final String STRESS_TEST_ENABLED_PROPERTY_KEY = "br.com.eventhorizon.common.pa.stressTestEnabled";

  public static final String COMPARE_TEST_ENABLED_PROPERTY_KEY = "br.com.eventhorizon.common.pa.compareTestEnabled";

  public static boolean getTimeLimitTestEnabled() {
    return Boolean.parseBoolean(System.getProperty(TIME_LIMIT_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(Defaults.TIME_LIMIT_TEST_ENABLED)));
  }

  public static void setTimeLimitTestEnabled(boolean enabled) {
    System.setProperty(TIME_LIMIT_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(enabled));
  }

  public static boolean getStressTestEnabled() {
    return Boolean.parseBoolean(System.getProperty(STRESS_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(Defaults.STRESS_TEST_ENABLED)));
  }

  public static void setStressTestEnabled(boolean enabled) {
    System.setProperty(STRESS_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(enabled));
  }

  public static boolean getCompareEnabled() {
    return Boolean.parseBoolean(System.getProperty(COMPARE_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(Defaults.COMPARE_TEST_ENABLED)));
  }

  public static void setCompareTestEnabled(boolean enabled) {
    System.setProperty(COMPARE_TEST_ENABLED_PROPERTY_KEY, Boolean.toString(enabled));
  }
}
