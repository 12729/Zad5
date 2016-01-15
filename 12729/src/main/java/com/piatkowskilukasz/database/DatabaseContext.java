package com.piatkowskilukasz.database;

/**
 * Created by LouL on 15.01.2016.
 */
import com.piatkowskilukasz.database.repositories.*;
import com.piatkowskilukasz.database.unitofwork.UnitOfWork;
import javax.sql.DataSource;
import java.sql.SQLException;

public class DatabaseContext {

    private UnitOfWork unitOfWork;
    private DataSource dataSource;
    private AddressRepository addressRepository;
    private PermissionRepository permissionRepository;
    private PermissionRoleRepository permissionRoleRepository;
    private PersonRepository personRepository;
    private RoleRepository roleRepository;
    private RoleUserRepository roleUserRepository;
    private UserRepository userRepository;

    public DatabaseContext(DataSource dataSource) {
        this.dataSource = dataSource;
        try {
            this.unitOfWork = new UnitOfWork(dataSource.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public AddressRepository addresses() {
        return new AddressRepository(dataSource, unitOfWork);
    }

    public PermissionRepository permissions() {
        return new PermissionRepository(dataSource, unitOfWork);
    }

    public PermissionRoleRepository permissionRoles() {
        return new PermissionRoleRepository(dataSource, unitOfWork);
    }

    public PersonRepository persons() {
        return new PersonRepository(dataSource, unitOfWork);
    }

    public RoleRepository roles() {
        return new RoleRepository(dataSource, unitOfWork);
    }

    public RoleUserRepository roleUsers() {
        return new RoleUserRepository(dataSource, unitOfWork);
    }

    public UserRepository users() {
        return new UserRepository(dataSource, unitOfWork);
    }

    public void saveChanges() {
        unitOfWork.commit();
    }
}
