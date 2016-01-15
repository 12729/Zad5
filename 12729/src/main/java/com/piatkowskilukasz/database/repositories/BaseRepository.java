package com.piatkowskilukasz.database.repositories;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.Entity;
import com.piatkowskilukasz.database.unitofwork.UnitOfWork;
import com.piatkowskilukasz.database.unitofwork.UnitOfWorkRepository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseRepository<T extends Entity> implements Repository<T>, UnitOfWorkRepository<T> {

    private final String tableName;
    private final String col_Id;
    protected final DataSource dataSource;
    private UnitOfWork unitOfWork;

    public BaseRepository(DataSource dataSource, UnitOfWork unitOfWork, String tableName, String col_Id) {
        this.unitOfWork = unitOfWork;
        this.dataSource = dataSource;
        this.tableName = tableName;
        this.col_Id = col_Id;
        createTableIfNotExists();
    }

    @Override
    public void add(T entity)
    {
        unitOfWork.markAsNew(entity, this);
    }

    @Override
    public void update(T entity)
    {
        unitOfWork.markAsDirty(entity, this);
    }

    @Override
    public void remove(T entity)
    {
        unitOfWork.markAsDeleted(entity, this);
    }

    @Override
    public T getById(long id)  {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql = String.format("SELECT * FROM %s WHERE %s='%s'", tableName, col_Id, id);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        return mapResultSetToModel(resultSet);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<T> getAll() {
        Collection<T> items = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql = String.format("SELECT * FROM %s", tableName);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        items.add(mapResultSetToModel(resultSet));
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public abstract long persistAdd(T item);

    @Override
    public void persistRemove(T item) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = String.format("DELETE FROM %s WHERE %s='%s'", tableName, col_Id, item.getId());

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        try(Connection connection = dataSource.getConnection()) {
            String sql = String.format("DELETE FROM %s", tableName);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract void persistUpdate(T item);

    protected abstract void createTableIfNotExists();

    protected abstract T mapResultSetToModel(ResultSet resultSet) throws SQLException;

    protected long getLastInsertedId(PreparedStatement preparedStatement) throws SQLException {
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if(rs.next())
        {
            return rs.getLong(1);
        }
        return -1;
    }
}
