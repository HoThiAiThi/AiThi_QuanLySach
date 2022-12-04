package com.example.aithi_quanlysach;

public class Users {
    String user_name, user_password, user_phone, user_email, user_firstname, user_lastname;

    public Users(String user_name, String user_password, String user_phone, String user_email, String user_firstname, String user_lastname) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }
}
