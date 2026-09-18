package model;

import java.io.Serializable;

public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    // =========================================================
    // FIELDS
    // =========================================================

    private String adminId;
    private String name;
    private String username;
    private String password;
    private String email;
    private String contactNumber;


    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Admin() {
        this.adminId = "";
        this.name = "";
        this.username = "";
        this.password = "";
        this.email = "";
        this.contactNumber = "";
    }


    // =========================================================
    // PARAMETERIZED CONSTRUCTOR
    // =========================================================

    public Admin(
            String adminId,
            String name,
            String username,
            String password,
            String email,
            String contactNumber
    ) {
        this.adminId = adminId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contactNumber = contactNumber;
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    // =========================================================
    // TO STRING
    // =========================================================

    @Override
    public String toString() {
        return adminId + " - " + name;
    }
}