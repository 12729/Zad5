package com.piatkowskilukasz.database.repositories;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.RoleUser;
import com.piatkowskilukasz.database.unitofwork.UnitOfWork;
import javax.sql.DataSource;
import java.sql.*;

public class RoleUserRepository extends BaseRepository<RoleUser> {

    private static final String tableName = "RolesUsers";
    private static final String col_IdUser = "IdUser";
    private static final String col_IdRole = "IdRole";

    public RoleUserRepository(DataSource dataSource, UnitOfWork unitOfWork) {
        super(dataSource, unitOfWork, tableName, col_IdRole);
    }

    @Override
    public long persistAdd(RoleUser roleUser) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format("INSERT INTO %s (", tableName);
            sql += String.format("%s, ", col_IdUser);
            sql += String.format("%s) VALUES (?, ?)", col_IdRole);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setLong(1, roleUser.getUserId());
                preparedStatement.setLong(2, roleUser.getRoleId());
                preparedStatement.executeUpdate();
                return getLastInsertedId(preparedStatement);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void persistUpdate(RoleUser roleUser) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format ("UPDATE %s SET ", tableName);
            sql += String.format ("%s=?, ", col_IdUser);
            sql += String.format ("%s=? ", col_IdRole);
            sql += String.format ("WHERE %s=?", col_IdUser);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, roleUser.getUserId());
                preparedStatement.setLong(2, roleUser.getRoleId());
                preparedStatement.setLong(3, roleUser.getUserId());
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
            sql += String.format("%s BIGINT NOT NULL, ", col_IdUser);
            sql += String.format("%s BIGINT NOT NULL)", col_IdRole);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected RoleUser mapResultSetToModel(ResultSet resultSet) throws SQLException {
        return new RoleUser(
                resultSet.getLong(col_IdUser),
                resultSet.getLong(col_IdRole));
    }
}
