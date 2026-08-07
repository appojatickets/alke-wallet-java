package com.alkewallet.viewmodel;

import android.app.Application;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alkewallet.data.repository.WalletRepository;
import com.alkewallet.model.Transaction;

/**
 * ViewModel de la pantalla Home (patrón MVVM).
 *
 * Responsabilidades (según la consigna):
 * - Coordinar la comunicación con Retrofit (vía el Repository) para
 *   acceder a la API REST.
 * - Coordinar con Room (vía el Repository) para acceso local/offline.
 * - Exponer datos a la View mediante LiveData, sin que la View conozca
 *   la lógica de negocio ni de red.
 */
public class HomeViewModel extends AndroidViewModel {

    private final WalletRepository repository;

    private final MutableLiveData<String> mensajeError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);
    private final MutableLiveData<String> mensajeExito = new MutableLiveData<>();

    private String userEmail;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new WalletRepository(application);
    }

    public void inicializar(String userEmail) {
        this.userEmail = userEmail;
        actualizarHistorial();
    }

    /**
     * Historial de transacciones: viene de Room (LiveData), así que la
     * Vista se actualiza sola apenas cambien los datos localmente.
     */
    public LiveData<List<Transaction>> getTransacciones() {
        return repository.getLocalTransactions(userEmail);
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public LiveData<String> getMensajeExito() {
        return mensajeExito;
    }

    public LiveData<Boolean> getCargando() {
        return cargando;
    }

    /**
     * Le pide a la API el historial actualizado y lo sincroniza con Room.
     * Se llama al entrar a Home y también con "pull to refresh" si se agrega.
     */
    public void actualizarHistorial() {
        if (userEmail == null) return;
        cargando.setValue(true);
        repository.refreshTransactionsFromApi(userEmail, new WalletRepository.RepositoryCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                cargando.setValue(false);
            }

            @Override
            public void onError(String mensajeErrorTexto) {
                cargando.setValue(false);
                // No es fatal: seguimos mostrando lo que ya hay en Room.
                mensajeError.setValue(mensajeErrorTexto);
            }
        });
    }

    /**
     * Calcula el saldo disponible a partir del historial local:
     * suma depósitos, resta retiros. Cumple "ver su saldo disponible".
     */
    public double calcularSaldo(List<Transaction> transacciones) {
        return com.alkewallet.util.BalanceCalculator.calcular(transacciones);
    }

    /**
     * Realiza una transacción virtual (depósito o retiro), validando
     * los datos antes de llamar a la API.
     */
    public void realizarTransaccion(double monto, String descripcion, boolean esDeposito) {
        if (monto <= 0) {
            mensajeError.setValue("Ingresa un monto válido");
            return;
        }

        Transaction nueva = new Transaction(
                UUID.randomUUID().toString(),
                userEmail,
                monto,
                descripcion == null || descripcion.trim().isEmpty() ? "Sin descripción" : descripcion.trim(),
                esDeposito ? "DEPOSIT" : "WITHDRAW",
                java.time.LocalDateTime.now().toString()
        );

        cargando.setValue(true);
        repository.createTransaction(nueva, new WalletRepository.RepositoryCallback<Transaction>() {
            @Override
            public void onSuccess(Transaction result) {
                cargando.setValue(false);
                mensajeExito.setValue(esDeposito ? "Depósito realizado con éxito" : "Retiro realizado con éxito");
            }

            @Override
            public void onError(String mensajeErrorTexto) {
                cargando.setValue(false);
                mensajeError.setValue(mensajeErrorTexto);
            }
        });
    }
}