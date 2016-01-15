package com.piatkowskilukasz.database.unitofwork;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.Entity;

public interface UnitOfWorkRepository<T extends Entity> {

    long persistAdd(T entity);
    void persistUpdate(T entity);
    void persistRemove(T entity);
}
