package utils;

import models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PersonRepository {
    private final static Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    public static void addPersonsToTablePerson(List<Person> personsList) throws SQLException {
        String statement = "insert into person (uid, name, age) values (?, ?, ?)";
        PreparedStatement preparedStatement = DbUtils.getPreparedStatement(statement);
        for (Person person : personsList) {
            preparedStatement.setLong(1, person.getUid());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.getConnection().commit();
        personsList.forEach(person -> logger.info(String.format("Person with uid %s was inserted to table PERSON", person.getUid())));
    }

    public static int getNumberOfEntriesInTablePerson() {
        String query = "select count(*) from person";
        int numberOfEntries = Integer.parseInt(DbUtils.executeSelect(query));
        logger.info(String.format("Number of entries in table PERSON is %s", numberOfEntries));
        return numberOfEntries;
    }

    public static void clearTablePerson() {
        String query = "delete from person where uid is not null";
        DbUtils.executeUpdate(query);
    }

    public static List<Person> getAllPersonsFromTablePerson() {
        String query = "select * from person";
        List<Person> persons = DbUtils.executeAllPersonsSelect(query);
        persons.forEach(person -> logger.info(String.format("Person with uid %s is added to list of persons", person.getUid())));
        return persons;
    }
}
