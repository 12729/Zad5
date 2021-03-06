package com.piatkowskilukasz.database.repositories;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.models.Person;
import com.piatkowskilukasz.database.unitofwork.UnitOfWork;
import javax.sql.DataSource;
import java.sql.*;

public class PersonRepository extends BaseRepository<Person> {

    private static final String tableName = "Persons";
    private static final String col_Id = "Id";
    private static final String col_FirstName = "FirstName";
    private static final String col_LastName = "LastName";
    private static final String col_PhoneNumber = "PhoneNumber";

    public PersonRepository(DataSource dataSource, UnitOfWork unitOfWork) {
        super(dataSource, unitOfWork, tableName, col_Id);
    }

    @Override
    public long persistAdd(Person person) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format("INSERT INTO %s (", tableName);
            sql += String.format("%s, ", col_FirstName);
            sql += String.format("%s, ", col_LastName);
            sql += String.format("%s) VALUES (?, ?, ?)", col_PhoneNumber);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setString(3, person.getPhoneNumber());
                preparedStatement.executeUpdate();
                return getLastInsertedId(preparedStatement);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void persistUpdate(Person person) {
        try(Connection connection = dataSource.getConnection()) {
            String sql;
            sql  = String.format ("UPDATE %s SET ", tableName);
            sql += String.format ("%s=?, ", col_FirstName);
            sql += String.format ("%s=?, ", col_LastName);
            sql += String.format ("%s=? ", col_PhoneNumber);
            sql += String.format ("WHERE %s=?", col_Id);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getLastName());
                preparedStatement.setString(3, person.getPhoneNumber());
                preparedStatement.setLong(4, person.getId());
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
            sql += String.format("%s VARCHAR(256) NOT NULL, ", col_FirstName);
            sql += String.format("%s VARCHAR(256) NOT NULL, ", col_LastName);
            sql += String.format("%s VARCHAR(256) NOT NULL)", col_PhoneNumber);

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.executeUpdate();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Person mapResultSetToModel(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getLong(col_Id),
                resultSet.getString(col_FirstName),
                resultSet.getString(col_LastName),
                resultSet.getString(col_PhoneNumber));
    }
}
