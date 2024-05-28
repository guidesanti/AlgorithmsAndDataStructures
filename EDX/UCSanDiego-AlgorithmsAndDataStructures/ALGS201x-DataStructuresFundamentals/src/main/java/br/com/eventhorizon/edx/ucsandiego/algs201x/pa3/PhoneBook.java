package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class PhoneBook implements PA {

  private static FastScanner in;

  @Override
  public void trivialSolution() {
    in = new FastScanner(System.in);
    List<Contact> contacts = new ArrayList<>();
    int numberOfQueries = in.nextInt();
    for (int i = 0; i < numberOfQueries; ++i)
      naiveProcessQuery(contacts, readQuery());
  }

  private static void naiveProcessQuery(List<Contact> contacts, Query query) {
    if (query.type.equals("add")) {
      // if we already have contact with such number,
      // we should rewrite contact's name
      boolean found = false;
      for (Contact contact : contacts)
        if (contact.number == query.number) {
          contact.name = query.name;
          found = true;
          break;
        }
      // otherwise, just add it
      if (!found)
        contacts.add(new Contact(query.name, query.number));
    } else if (query.type.equals("del")) {
      for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); )
        if (it.next().number == query.number) {
          it.remove();
          break;
        }
    } else {
      String response = "not found";
      for (Contact contact: contacts)
        if (contact.number == query.number) {
          response = contact.name;
          break;
        }
      writeResponse(response);
    }
  }

  @Override
  public void finalSolution() {
    in = new FastScanner(System.in);
    Map<Integer, String> contacts = new HashMap<>();
    int numberOfQueries = in.nextInt();
    for (int i = 0; i < numberOfQueries; ++i)
      finalProcessQuery(contacts, readQuery());
  }

  private static void finalProcessQuery(Map<Integer, String> contacts, Query query) {
    if (query.type.equals("add")) {
      contacts.put(query.number, query.name);
    } else if (query.type.equals("del")) {
      contacts.remove(query.number);
    } else {
      writeResponse(contacts.getOrDefault(query.number, "not found"));
    }
  }

  private static Query readQuery() {
    String type = in.next();
    int number = in.nextInt();
    if (type.equals("add")) {
      String name = in.next();
      return new Query(type, name, number);
    } else {
      return new Query(type, number);
    }
  }

  private static void writeResponse(String response) {
    System.out.println(response);
  }

  private static class Contact {

    String name;

    int number;

    public Contact(String name, int number) {
      this.name = name;
      this.number = number;
    }
  }

  private static class Query {

    String type;

    String name;

    int number;

    public Query(String type, String name, int number) {
      this.type = type;
      this.name = name;
      this.number = number;
    }

    public Query(String type, int number) {
      this.type = type;
      this.number = number;
    }
  }
}
