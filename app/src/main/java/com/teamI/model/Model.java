package com.teamI.model;

public class Model {
    String firstName;
    String lastName;
    String Email;
    String Password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public Model(){}

    public Model (String fname, String lname, String mail, String pass){
        this.firstName = fname;
        this.lastName = lname;
        this.Email = mail;
        this.Password = pass;
    }

}
