package com.alkewallet.util;

import java.util.List;

import com.alkewallet.model.Transaction;

/**
 * Lógica pura de cálculo de saldo, separada del ViewModel para poder
 * probarla con una prueba unitaria simple (sin depender de Android).
 */
public class BalanceCalculator {

    private BalanceCalculator() {
    }

    /**
     * Suma depósitos y resta retiros para obtener el saldo disponible.
     */
    public static double calcular(List<Transaction> transacciones) {
        double saldo = 0;
        if (transacciones == null) {
            return saldo;
        }
        for (Transaction t : transacciones) {
            if ("DEPOSIT".equals(t.getType())) {
                saldo += t.getAmount();
            } else if ("WITHDRAW".equals(t.getType())) {
                saldo -= t.getAmount();
            }
        }
        return saldo;
    }
}