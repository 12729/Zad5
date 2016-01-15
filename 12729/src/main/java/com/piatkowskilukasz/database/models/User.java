package com.piatkowskilukasz.database.models;

/**
 * Created by LouL on 15.01.2016.
 */
public class User extends Entity {

    private long personId;
    private String login;
    private String password;

    public User(long id, long personId, String login, String password) {
        this(personId, login, password);
        setId(id);
        setState(EntityState.Unchanged);
    }

    public User(long personId, String login, String password) {
        super(EntityState.New);
        this.personId = personId;
        this.login = login;
        this.password = password;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}