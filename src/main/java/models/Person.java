package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Person {
    private long uid;
    private String name;
    private int age;

    public int compareByAge(Person otherPerson) {
        return this.age - otherPerson.getAge();
    }
}
