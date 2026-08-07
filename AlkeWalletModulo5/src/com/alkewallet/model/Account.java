package com.alkewallet.model;

/**
 * Representa la cuenta/billetera de un usuario.
 * Contiene la lógica de negocio para administración de fondos
 * (requerimiento general de la consigna del Módulo 5).
 */
public class Account {

    private String ownerEmail;
    private double balance;

    public Account(String ownerEmail, double balance) {
        this.ownerEmail = ownerEmail;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    /**
     * Deposita fondos en la cuenta.
     * @param amount monto a depositar, debe ser mayor a 0
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0");
        }
        this.balance += amount;
    }

    /**
     * Retira fondos de la cuenta, validando que exista saldo suficiente.
     * @param amount monto a retirar, debe ser mayor a 0 y menor o igual al saldo
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a 0");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.balance -= amount;
    }
}
