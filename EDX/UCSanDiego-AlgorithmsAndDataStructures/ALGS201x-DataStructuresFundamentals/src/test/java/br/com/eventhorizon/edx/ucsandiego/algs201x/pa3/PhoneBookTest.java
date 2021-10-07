package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class PhoneBookTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/phone-book.csv";

  private static final String[] QUERY_TYPES = { "add", "del", "find" };

  public PhoneBookTest() {
    super(new PhoneBook());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Test
  public void testFinalSolutionWorstCase() {
    String input = generateWorstCaseInput();
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    resetOutput();
    assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), pa::finalSolution);
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
  protected String generateInput(PATestType type) {
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
