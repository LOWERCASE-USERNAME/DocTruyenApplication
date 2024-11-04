package com.example.doctruyenapplication.object;
import java.util.List;
public class Account {
    private int AccountId;
    private String Email;
    private String Password;
    private  int RoleId;
    public List<Role> Role;

    public Account() {
    }


    public Account(String email, String password, int roleId) {

        Email = email;
        Password = password;
        RoleId = roleId;
    }

    public int getAccountId() {
        return AccountId;
    }

    public void setAccountId(int accountId) {
        AccountId = accountId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPasswordHarsh() {
        return Password;
    }

    public void setPasswordHarsh(String passwordHarsh) {
        Password = passwordHarsh;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public List<Role> getRole() {
        return Role;
    }

    public void setRole(List<Role> role) {
        Role = role;
    }
}
