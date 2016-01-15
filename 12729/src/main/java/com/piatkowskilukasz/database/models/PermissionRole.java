package com.piatkowskilukasz.database.models;

/**
 * Created by LouL on 15.01.2016.
 */
public class PermissionRole extends Entity {

    private long roleId;
    private long permissionId;

    public PermissionRole(long id, long roleId, long permissionId) {
        this(roleId, permissionId);
        setId(id);
        setState(EntityState.Unchanged);
    }

    public PermissionRole(long roleId, long permissionId) {
        super(EntityState.New);
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
