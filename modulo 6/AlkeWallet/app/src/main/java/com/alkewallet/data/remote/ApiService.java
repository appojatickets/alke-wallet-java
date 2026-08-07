package com.alkewallet.data.remote;

import java.util.List;

import com.alkewallet.model.Transaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Define los endpoints REST que consume la app, usando Retrofit.
 * Cumple el requerimiento de la consigna: "Implementar las interfaces de
 * Retrofit para hacer las solicitudes GET y POST a la API REST externa".
 */
public interface ApiService {

    // ---- Usuarios ----

    /**
     * Registra un nuevo usuario (signup).
     */
    @POST("users")
    Call<UserDto> registerUser(@Body UserDto user);

    /**
     * Busca un usuario por email, usado para validar el login
     * (la API mock no soporta autenticación real, así que el password
     * se compara en el ViewModel una vez recibida la respuesta).
     */
    @GET("users")
    Call<List<UserDto>> findUserByEmail(@Query("email") String email);

    /**
     * Obtiene el perfil de un usuario por id (para la pantalla de Perfil).
     */
    @GET("users/{id}")
    Call<UserDto> getUserProfile(@Path("id") String userId);

    // ---- Transacciones ----

    /**
     * Obtiene el historial de transacciones de un usuario.
     */
    @GET("transactions")
    Call<List<Transaction>> getTransactions(@Query("userEmail") String userEmail);

    /**
     * Registra una nueva transacción virtual (depósito o retiro).
     */
    @POST("transactions")
    Call<Transaction> createTransaction(@Body Transaction transaction);
}