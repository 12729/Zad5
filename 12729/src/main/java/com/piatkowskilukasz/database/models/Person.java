package com.piatkowskilukasz.database.models;

/**
 * Created by LouL on 15.01.2016.
 */

public class Person extends Entity {

    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Person(long id, String firstName, String lastName, String phoneNumber) {
        this(firstName, lastName, phoneNumber);
        setId(id);
        setState(EntityState.Unchanged);
    }

    public Person(String firstName, String lastName, String phoneNumber) {
        super(EntityState.New);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
