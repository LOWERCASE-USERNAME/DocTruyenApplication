package com.example.doctruyenapplication.object;
import java.io.Serializable;
import java.util.List;
public class Account implements Serializable {
    private int accountId;
    private String email;
    private String password;
    private  int roleId;
    public List<Role> role;

    public Account() {
    }


    public Account(String email, String password, int roleId) {

        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHarsh() {
        return password;
    }

    public void setPasswordHarsh(String passwordHarsh) {
        password = passwordHarsh;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}
