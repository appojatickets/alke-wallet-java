package com.alkewallet.model;

/**
 * Representa la billetera/cuenta de un usuario.
 * Clase de MODELO (patrón MVC): concentra la lógica de negocio
 * de administración de fondos que pide la consigna del Módulo 5.
 */
public class Account {

    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a 0");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        balance -= amount;
    }
}