/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

/**
 *
 * @author DELL
 */
public class User {
    private String email;
    private String name;
    private String roleId;
    private String password;
    private int status;

    public User() {
    }

    public User(String email, String name, String roleId, String password, int status) {
        this.email = email;
        this.name = name;
        this.roleId = roleId;
        this.password = password;
        this.status = status;
    }

    public User(String email, String name, String roleId, int status) {
        this.email = email;
        this.name = name;
        this.roleId = roleId;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
