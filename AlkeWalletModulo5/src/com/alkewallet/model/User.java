package com.alkewallet.model;

/**
 * Representa un usuario de Alke Wallet.
 * Reutiliza el concepto de usuario ya manejado en Login/SignupActivity (Módulo 4),
 * ahora como clase de Modelo dentro del patrón MVC.
 */
public class User {

    // Nombre y Apellido separados: así están definidos los campos reales
    // en activity_signup.xml del Módulo 4 (etNombre / etApellido)
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
        // Cada usuario nuevo arranca con una cuenta en $0
        this.account = new Account(this.email, 0.0);
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
