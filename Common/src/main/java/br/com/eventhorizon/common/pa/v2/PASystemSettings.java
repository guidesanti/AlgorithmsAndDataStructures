package br.com.eventhorizon.common.pa.v2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PASystemSettings {

    public static Optional<Boolean> isTimeLimitTestEnabled() {
        var sysPropValue = System.getProperty("pa.settings.time-limit-test-enabled");
        return StringUtils.isNotBlank(sysPropValue)
                ? Optional.of(Boolean.parseBoolean(sysPropValue)) : Optional.empty();
    }

    public static Optional<Boolean> isStressTestEnabled() {
        var sysPropValue = System.getProperty("pa.settings.stress-test-enabled");
        return StringUtils.isNotBlank(sysPropValue)
                ? Optional.of(Boolean.parseBoolean(sysPropValue)) : Optional.empty();
    }

    public static Optional<Boolean> isCompareTestEnabled() {
        var sysPropValue = System.getProperty("pa.settings.compare-test-enabled");
        return StringUtils.isNotBlank(sysPropValue)
                ? Optional.of(Boolean.parseBoolean(sysPropValue)) : Optional.empty();
    }
}
