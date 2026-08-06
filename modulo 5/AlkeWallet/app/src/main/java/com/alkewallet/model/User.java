package com.alkewallet.model;

/**
 * Representa un usuario de Alke Wallet.
 * Campos alineados 1 a 1 con activity_signup.xml: etNombre, etApellido,
 * etEmail, etPassword.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Account account;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.account = new Account(0.0);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount() {
        return account;
    }
}