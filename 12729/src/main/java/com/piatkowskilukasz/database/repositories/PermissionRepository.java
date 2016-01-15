package com.piatkowskilukasz.database.repositories;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.Permission;
import com.piatkowskilukasz.database.unitofwork.UnitOfWork;
import javax.sql.DataSource;
import java.sql.*;

public class PermissionRepository extends BaseRepository<Permission> {

    private static final String tableName = "Permissions";
    private static final String col_Id = "Id";
    private static final String col_Name = "Name";

    public PermissionRepository(DataSource dataSource, UnitOfWork unitOfWork) {
        super(dataSource, unitOfWork, tableName, col_Id);
    }

    public Permission getByName(String name)  {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql = String.format("SELECT * FROM %s WHERE %s='%s'", tableName, col_Name, name);

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
    public long persistAdd(Permission permission) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format("INSERT INTO %s (", tableName);
            sql += String.format("%s) VALUES (?)", col_Name);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, permission.getName());
                preparedStatement.executeUpdate();
                return getLastInsertedId(preparedStatement);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void persistUpdate(Permission permission) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format ("UPDATE %s SET ", tableName);
            sql += String.format ("%s=? ", col_Name);
            sql += String.format ("WHERE %s=?", col_Id);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, permission.getName());
                preparedStatement.setLong(2, permission.getId());
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void createTableIfNotExists() {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql = String.format("CREATE TABLE IF NOT EXISTS %s (", tableName);
            sql += String.format("%s BIGINT GENERATED BY DEFAULT AS IDENTITY, ", col_Id);
            sql += String.format("%s VARCHAR(256) NOT NULL)", col_Name);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Permission mapResultSetToModel(ResultSet resultSet) throws SQLException {
        return new Permission(
                resultSet.getLong(col_Id),
                resultSet.getString(col_Name));
    }
}
