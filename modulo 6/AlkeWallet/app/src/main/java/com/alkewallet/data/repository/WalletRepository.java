package com.alkewallet.data.repository;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

import com.alkewallet.data.local.AppDatabase;
import com.alkewallet.data.local.TransactionDao;
import com.alkewallet.data.remote.ApiService;
import com.alkewallet.data.remote.RetrofitClient;
import com.alkewallet.data.remote.UserDto;
import com.alkewallet.model.Transaction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository central de la app (patrón Repository dentro de MVVM).
 * Coordina Retrofit (API REST) y Room (base de datos local), para que
 * el ViewModel no necesite saber de dónde vienen los datos.
 *
 * Cumple los requerimientos de la consigna sobre manejo de errores:
 * nunca propaga excepciones técnicas hacia arriba, siempre entrega
 * mensajes legibles a través de RepositoryCallback.onError().
 */
public class WalletRepository {

    private final ApiService apiService;
    private final TransactionDao transactionDao;
    private final ExecutorService executor;

    public WalletRepository(Context context) {
        this.apiService = RetrofitClient.getApiService();
        this.transactionDao = AppDatabase.getInstance(context).transactionDao();
        this.executor = Executors.newFixedThreadPool(2);
    }

    /**
     * Callback genérico para no atar la capa de datos a Retrofit ni a
     * ningún framework de UI en particular.
     */
    public interface RepositoryCallback<T> {
        void onSuccess(T result);
        void onError(String mensajeError);
    }

    // ---------------- Autenticación ----------------

    public void login(String email, String password, RepositoryCallback<UserDto> callback) {
        apiService.findUserByEmail(email).enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                    callback.onError("Email o contraseña incorrectos");
                    return;
                }
                UserDto user = response.body().get(0);
                if (user.getPassword() != null && user.getPassword().equals(password)) {
                    callback.onSuccess(user);
                } else {
                    callback.onError("Email o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                callback.onError("No se pudo conectar al servidor. Revisa tu conexión a internet.");
            }
        });
    }

    public void register(UserDto newUser, RepositoryCallback<UserDto> callback) {
        apiService.registerUser(newUser).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("No se pudo crear la cuenta. Intenta nuevamente.");
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                callback.onError("No se pudo conectar al servidor. Revisa tu conexión a internet.");
            }
        });
    }

    public void getUserProfile(String userId, RepositoryCallback<UserDto> callback) {
        apiService.getUserProfile(userId).enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("No se pudo cargar el perfil");
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                callback.onError("No se pudo conectar al servidor. Revisa tu conexión a internet.");
            }
        });
    }

    // ---------------- Transacciones ----------------

    /**
     * Devuelve el historial de transacciones guardado localmente (Room).
     * Es LiveData: la Vista se actualiza sola apenas cambien los datos,
     * incluso sin conexión.
     */
    public LiveData<List<Transaction>> getLocalTransactions(String userEmail) {
        return transactionDao.getTransactionsByUser(userEmail);
    }

    /**
     * Pide el historial actualizado a la API y lo guarda en Room.
     * La Vista no necesita esperar esta llamada: como getLocalTransactions()
     * devuelve LiveData, apenas Room se actualice la UI se refresca sola.
     */
    public void refreshTransactionsFromApi(String userEmail, RepositoryCallback<Void> callback) {
        apiService.getTransactions(userEmail).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> transactionDao.insertAll(response.body()));
                    callback.onSuccess(null);
                } else {
                    callback.onError("No se pudo actualizar el historial de transacciones");
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                // Falla de red: no es un error fatal, seguimos mostrando
                // lo que ya hay guardado en Room (acceso sin conexión).
                callback.onError("Sin conexión: mostrando datos guardados localmente");
            }
        });
    }

    /**
     * Crea una transacción (depósito o retiro) en la API y, si tiene éxito,
     * la guarda también en Room para que quede disponible sin conexión.
     */
    public void createTransaction(Transaction transaction, RepositoryCallback<Transaction> callback) {
        apiService.createTransaction(transaction).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> transactionDao.insert(response.body()));
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("No se pudo registrar la transacción");
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
                callback.onError("No se pudo conectar al servidor. Intenta nuevamente.");
            }
        });
    }
}