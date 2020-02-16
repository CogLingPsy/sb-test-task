package utils;

import com.google.gson.Gson;
import models.Person;
import org.assertj.core.api.Fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonListParser {

    public static List<Person> createPersonListFromJson(int numberOfPersons, String filePath) {
        String jsonStr = null;
        try {
            jsonStr = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            Fail.fail("Json file failed to be read");
        }
        assertThat(jsonStr).isNotNull();
        Person[] personsArray = new Gson().fromJson(jsonStr, Person[].class);
        List<Person> personsList = new ArrayList<>(Arrays.asList(personsArray).subList(0, numberOfPersons));
        return personsList;
    }
}
