package com.mhl.mycompanybackend.pojo;

public class RoleRequest {
    private String rolename;
    private String permission;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
