package utils;

import models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PersonRepository {
    private final static Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    public static void addPersonsToTablePerson(List<Person> personList) {
        String query = "INSERT into person (uid, name, age) VALUES ";
        for (Person person : personList) {
            query = query.concat(String.format("(%s, '%s', %s), ", person.getUid(), person.getName(), person.getAge()));
        }
        query = query.substring(0, query.length() - 2);
        DbUtils.executeUpdate(query);
    }

    public static int getNumberOfEntriesInTablePerson() {
        String query = "SELECT count(*) from person";
        int numberOfEntries = Integer.parseInt(DbUtils.executeSelect(query));
        logger.info(String.format("Number of entries in table Person is %s", numberOfEntries));
        return numberOfEntries;
    }

    public static void clearTablePerson() {
        String query = "DELETE from person where uid is not null";
        DbUtils.executeUpdate(query);
    }

    public static List<Person> getAllNamesAndAgesFromTablePerson() {
        String query = "SELECT * from person";
        List<Person> persons = DbUtils.executeAllPersonsSelect(query);
        persons.forEach(person -> logger.info(String.format("Person with uid %s added to list of persons", person.getUid())));
        return persons;
    }
}
