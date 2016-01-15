package com.piatkowskilukasz.database.models;

/**
 * Created by LouL on 15.01.2016.
 */
public class Permission extends Entity {

    private String name;

    public Permission(long id, String name) {
        this(name);
        setId(id);
        setState(EntityState.Unchanged);
    }

    public Permission(String name) {
        super(EntityState.New);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
