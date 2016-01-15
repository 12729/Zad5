package com.piatkowskilukasz.database.unitofwork;


/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.Entity;
import com.piatkowskilukasz.database.models.EntityState;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class UnitOfWork {

    private Connection connection;
    private Map<Entity, UnitOfWorkRepository> entities = new LinkedHashMap<>();

    public UnitOfWork(Connection connection) {
        super();
        this.connection = connection;

        try {
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        for(Entity entity: entities.keySet())
        {
            switch(entity.getState())
            {
                case Changed:
                    entities.get(entity).persistUpdate(entity);
                    break;
                case Deleted:
                    entities.get(entity).persistRemove(entity);
                    break;
                case New:
                    entities.get(entity).persistAdd(entity);
                    break;
                case Unchanged:
                    break;
                default:
                    break;}
        }

        try {
            connection.commit();
            entities.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        entities.clear();
    }

    public void markAsNew(Entity entity, UnitOfWorkRepository repository) {
        entity.setState(EntityState.New);
        entities.put(entity, repository);
    }

    public void markAsDirty(Entity entity, UnitOfWorkRepository repository) {
        entity.setState(EntityState.Changed);
        entities.put(entity, repository);
    }

    public void markAsDeleted(Entity entity, UnitOfWorkRepository repository) {
        entity.setState(EntityState.Deleted);
        entities.put(entity, repository);
    }
}