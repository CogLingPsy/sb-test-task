package db;

import models.Person;
import org.assertj.core.api.Fail;
import org.testng.annotations.*;
import utils.DbUtils;
import utils.PersonListParser;
import utils.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DbEntriesTests {

    private final String listOfPersons = this.getClass().getClassLoader().getResource("persons.json").getPath();

    //region setup and teardown
    @BeforeClass
    public void initDbConnection() {
        DbUtils.getInstance();
    }

    @AfterMethod
    public void setEmptyTablePostCondition() {
        PersonRepository.clearTablePerson();
    }

    @AfterClass
    public void closeDbConnection() {
        DbUtils.getInstance().closeConnection();
    }

    @DataProvider(name = "numberOfPersons")
    public static Object[] numberOfPersons() {
        Integer[] total = {2, 0, 7};
        return total;
    }

    @DataProvider(name = "eldestPersonInList")
    public static Object[][] eldestPersonInList() {
        Object[][] total = {{2, "Martin"}, {3, "Alex"}, {7, "Phoebe"}};
        return total;
    }
    //endregion setup and teardown

    @Test(dataProvider = "numberOfPersons")
    public void checkPersonsCountAfterInserting(int numberOfPersons) {
        List<Person> personsList = PersonListParser.createPersonListFromJson(numberOfPersons, listOfPersons);
        addPersonsToTableIfListNotEmpty(personsList);
        int numberOfEntriesInTablePerson = PersonRepository.getNumberOfEntriesInTablePerson();
        assertThat(numberOfEntriesInTablePerson).isEqualTo(numberOfPersons);
    }

    @Test(dataProvider = "eldestPersonInList")
    public void checkComparingAgeGettingEldestPerson(Object[] eldestPersonInList) {
        List<Person> personsList = PersonListParser.createPersonListFromJson((int) eldestPersonInList[0], listOfPersons);
        addPersonsToTableIfListNotEmpty(personsList);
        List<Person> persons = PersonRepository.getAllNamesAndAgesFromTablePerson();
        Optional<Person> eldestPerson = persons.stream().max(Person::compareByAge);
        if (eldestPerson.isPresent()) {
            assertThat(eldestPerson.get().getName()).isEqualTo(eldestPersonInList[1]);
        } else {
            Fail.fail("No eldest person found");
        }
    }

    private void addPersonsToTableIfListNotEmpty(List<Person> personsList) {
        assertThat(personsList.size()).as("No persons to add in the table").isGreaterThan(0);
        PersonRepository.addPersonsToTablePerson(personsList);
    }
}
