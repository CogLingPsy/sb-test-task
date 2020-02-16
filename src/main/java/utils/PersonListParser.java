package utils;

import models.Person;
import org.assertj.core.api.Fail;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonListParser {

    @SuppressWarnings("unchecked")
    public static List<Person> createPersonListFromJson(int numberOfPersons, String filePath) {
        List<Person> personsList = new ArrayList<>();
        HashMap<String, HashMap> mapOfPersons = parseList(filePath);
        int counter = 0;
        for (Map.Entry<String, HashMap> entry : mapOfPersons.entrySet()) {
            if (counter == numberOfPersons) break;
            HashMap<String, String> value = entry.getValue();
            personsList.add(new Person(Long.parseLong(value.get("uid")), value.get("name"), Integer.parseInt(value.get("age"))));
            counter++;
        }
        return personsList;
    }

    @SuppressWarnings("unchecked")
    private static HashMap<String, HashMap> parseList(String fileName) {
        JSONParser parser = new JSONParser();
        Object parsedList = null;
        try {
            parsedList = parser.parse(new FileReader(fileName));
        } catch (Exception e) {
            Fail.fail("Parsing of json file failed");
        }
        HashMap<String, HashMap> mapOfPersons = (HashMap<String, HashMap>) parsedList;
        return mapOfPersons;
    }

}
