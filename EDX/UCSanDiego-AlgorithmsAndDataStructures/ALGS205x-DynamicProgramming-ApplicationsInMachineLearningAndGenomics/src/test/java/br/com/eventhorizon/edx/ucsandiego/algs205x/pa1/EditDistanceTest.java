package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.Objects;

public class EditDistanceTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/edit-distance.csv";

  public EditDistanceTest() {
    super(new EditDistance(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
      if (Objects.requireNonNull(type) == PATestType.TIME_LIMIT_TEST) {
          return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000) +
                  " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
      }
      return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10) +
              " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 10);
  }
}
