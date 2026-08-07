package com.alkewallet.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Representa una transacción virtual (depósito o retiro).
 *
 * Cumple doble rol dentro de MVVM:
 * - Como ENTIDAD de Room: se guarda en la base de datos local para
 *   permitir historial sin conexión.
 * - Como MODELO de Retrofit/Gson: se arma automáticamente a partir del
 *   JSON que devuelve la API REST externa.
 *
 * Los requerimientos de la consigna que cubre: "fecha, monto y
 * descripción de cada transacción".
 */
@Entity(tableName = "transactions")
public class Transaction {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("userEmail")
    private String userEmail;

    @SerializedName("amount")
    private double amount;

    @SerializedName("description")
    private String description;

    @SerializedName("type")
    private String type; // "DEPOSIT" o "WITHDRAW"

    @SerializedName("date")
    private String date; // fecha en formato ISO-8601, ej: "2026-08-07T10:45:00"

    public Transaction() {
        // Constructor vacío requerido por Room y por Gson
    }

    public Transaction(@NonNull String id, String userEmail, double amount,
                       String description, String type, String date) {
        this.id = id;
        this.userEmail = userEmail;
        this.amount = amount;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}