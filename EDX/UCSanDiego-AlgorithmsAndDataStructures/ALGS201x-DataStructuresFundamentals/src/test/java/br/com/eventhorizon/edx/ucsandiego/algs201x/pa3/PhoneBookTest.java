package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class PhoneBookTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/phone-book.csv";

  private static final String[] QUERY_TYPES = { "add", "del", "find" };

  public PhoneBookTest() {
    super(new PhoneBook(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Test
  public void testFinalSolutionWorstCase() {
    String input = generateWorstCaseInput();
    reset(input);
    assertTimeoutPreemptively(ofMillis(getSettings().getTimeLimit()), pa::finalSolution);
  }

  protected String generateWorstCaseInput() {
    StringBuilder input = new StringBuilder();
    Contact[] contacts = generateContacts(100000 / 2);
    int numberOfQueries = 100000;
    input.append(numberOfQueries);
    for (int i = 0; i < contacts.length; i++) {
      input.append(" add ").append(contacts[i].number).append(" ").append(contacts[i].name);
    }
    for (int i = 0; i < contacts.length; i++) {
      input.append(" find ").append(contacts[Utils.getRandomInteger(0, contacts.length - 1)].number);
    }
    return input.toString();
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    Contact[] contacts = generateContacts(Utils.getRandomInteger(10, 100000));
    int numberOfQueries;
    switch (type) {
      case TIME_LIMIT_TEST:
        numberOfQueries = Utils.getRandomInteger(1, 100000);
        break;
      case STRESS_TEST:
      default:
        numberOfQueries = Utils.getRandomInteger(1, 100);
        break;
    }
    input.append(numberOfQueries);
    for (int i = 0; i < numberOfQueries; i++) {
      input.append(" ").append(generateQuery(contacts));
    }
    return input.toString();
  }

  private String generateQuery(Contact[] contacts) {
    String query = QUERY_TYPES[Utils.getRandomInteger(0, 2)];
    Contact contact = contacts[Utils.getRandomInteger(0, contacts.length - 1)];
    if (query.equals("add")) {
      query += " " + contact.number + " " + contact.name;
    } else {
      query += " " + contact.number;
    }
    return query;
  }

  private Contact[] generateContacts(int numberOfContacts) {
    Contact[] contacts = new Contact[numberOfContacts];
    for (int i = 0; i < numberOfContacts; i++) {
      int nameLength = Utils.getRandomInteger(1, 15);
      StringBuilder name = new StringBuilder();
      for (int j = 0; j < nameLength; j++) {
        name.append((char)Utils.getRandomInteger(97, 122));
      }
      contacts[i] = new Contact(name.toString(), Utils.getRandomInteger(1, 9999999));
    }
    return contacts;
  }

  private static class Contact {

    String name;

    int number;

    public Contact(String name, int number) {
      this.name = name;
      this.number = number;
    }
  }
}
