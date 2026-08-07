package com.alkewallet.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.alkewallet.model.Transaction;

/**
 * Pruebas unitarias del cálculo de saldo (requerimiento de la consigna:
 * "Crear pruebas unitarias... asegurándote de que las interacciones
 * entre el modelo y la base de datos funcionen correctamente").
 */
public class BalanceCalculatorTest {

    @Test
    public void listaVacia_devuelveSaldoCero() {
        double resultado = BalanceCalculator.calcular(new ArrayList<>());
        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    public void listaNull_devuelveSaldoCero() {
        double resultado = BalanceCalculator.calcular(null);
        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    public void unSoloDeposito_sumaCorrectamente() {
        Transaction deposito = new Transaction(
                "1", "test@alkewallet.com", 100000.0, "Depósito inicial", "DEPOSIT", "2026-08-07");

        double resultado = BalanceCalculator.calcular(Collections.singletonList(deposito));

        assertEquals(100000.0, resultado, 0.001);
    }

    @Test
    public void unSoloRetiro_restaCorrectamente() {
        Transaction retiro = new Transaction(
                "1", "test@alkewallet.com", 50000.0, "Retiro", "WITHDRAW", "2026-08-07");

        double resultado = BalanceCalculator.calcular(Collections.singletonList(retiro));

        assertEquals(-50000.0, resultado, 0.001);
    }

    @Test
    public void depositosYRetiros_calculaSaldoNeto() {
        List<Transaction> transacciones = Arrays.asList(
                new Transaction("1", "test@alkewallet.com", 500000.0, "Depósito", "DEPOSIT", "2026-08-07"),
                new Transaction("2", "test@alkewallet.com", 100000.0, "Retiro", "WITHDRAW", "2026-08-07"),
                new Transaction("3", "test@alkewallet.com", 200000.0, "Depósito", "DEPOSIT", "2026-08-08")
        );

        double resultado = BalanceCalculator.calcular(transacciones);

        // 500.000 - 100.000 + 200.000 = 600.000
        assertEquals(600000.0, resultado, 0.001);
    }

    @Test
    public void tipoDesconocido_seIgnoraEnElCalculo() {
        Transaction tipoRaro = new Transaction(
                "1", "test@alkewallet.com", 999.0, "Tipo inválido", "TRANSFER", "2026-08-07");

        double resultado = BalanceCalculator.calcular(Collections.singletonList(tipoRaro));

        assertEquals(0.0, resultado, 0.001);
    }
}