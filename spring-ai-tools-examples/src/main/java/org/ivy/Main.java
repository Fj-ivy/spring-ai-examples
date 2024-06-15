package org.ivy;

import org.ivy.types.Person;

public class Main {
    public static void main(String[] args) {
        Person person = new Person().withAge(1).withName("aaa");
        System.out.println(person);
    }
}