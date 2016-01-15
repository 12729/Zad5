package com.piatkowskilukasz.database.repositories;

/**
 * Created by LouL on 15.01.2016.
 */
import java.util.Collection;

public interface Repository<T> {

    T getById(long id);
    Collection<T> getAll();
    void add(T item);
    void remove(T item);
    void removeAll();
    void update(T item);
}
