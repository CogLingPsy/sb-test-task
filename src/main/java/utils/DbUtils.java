package utils;

import models.Person;
import org.assertj.core.api.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DbUtils {
    private final static Logger logger = LoggerFactory.getLogger(DbUtils.class);
    private static Connection connection = null;
    private static DbUtils instance = null;

    public static DbUtils getInstance() {
        if (instance == null) {
            instance = new DbUtils();
        }
        return instance;
    }

    private DbUtils() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/test-db.s3db");
            logger.info("Connection to DB established");
        } catch (SQLException e) {
            Fail.fail("Connection to DB failed");
        }
    }

    static void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            logger.info(String.format("%s executed", query));
        } catch (SQLException e) {
            Fail.fail("SQL statement execution failed");
        }
    }

    static String executeSelect(String query) {
        String resultRow = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            logger.info(String.format("%s executed", query));
            if (result.next()) {
                resultRow = result.getString(1);
            }
        } catch (SQLException e) {
            Fail.fail("SQL statement execution failed");
        }
        assertThat(resultRow).isNotBlank();
        return resultRow;
    }

    public void closeConnection() {
        try {
            connection.close();
            logger.info("Connection to DB closed");
        } catch (SQLException e) {
            logger.error("An error occured on closing DB connection");
        }
    }

    static List<Person> executeAllPersonsSelect(String query) {
        List<Person> persons = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            logger.info(String.format("%s executed", query));
            while (result.next()) {
                Person user = new Person(result.getLong(1), result.getString(2), result.getInt(3));
                persons.add(user);
            }
        } catch (SQLException e) {
            Fail.fail("SQL statement execution failed");
        }
        return persons;
    }
}
