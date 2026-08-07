package com.alkewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alkewallet.data.Session;
import com.alkewallet.data.repository.WalletRepository;
import com.alkewallet.data.remote.UserDto;

/**
 * ViewModel de las pantallas de autenticación (Login / Signup).
 * Cumple el requerimiento: "gestión de sesiones y autenticación segura",
 * coordinando Retrofit (vía el Repository) sin exponer detalles de red
 * a la Vista.
 */
public class AuthViewModel extends AndroidViewModel {

    private final WalletRepository repository;

    private final MutableLiveData<Boolean> loginExitoso = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registroExitoso = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);

    public AuthViewModel(@NonNull Application application) {
        super(application);
        repository = new WalletRepository(application);
    }

    public LiveData<Boolean> getLoginExitoso() {
        return loginExitoso;
    }

    public LiveData<Boolean> getRegistroExitoso() {
        return registroExitoso;
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public LiveData<Boolean> getCargando() {
        return cargando;
    }

    public void login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            mensajeError.setValue("Completa email y contraseña");
            return;
        }

        cargando.setValue(true);
        repository.login(email.trim(), password, new WalletRepository.RepositoryCallback<UserDto>() {
            @Override
            public void onSuccess(UserDto user) {
                cargando.setValue(false);
                Session.iniciarSesion(
                        user.getId(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getProfileImageUrl()
                );
                loginExitoso.setValue(true);
            }

            @Override
            public void onError(String mensajeErrorTexto) {
                cargando.setValue(false);
                mensajeError.setValue(mensajeErrorTexto);
            }
        });
    }

    public void registrar(String firstName, String lastName, String email,
                          String password, String confirmPassword) {
        if (firstName == null || firstName.trim().isEmpty()
                || lastName == null || lastName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {
            mensajeError.setValue("Completa todos los campos");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mensajeError.setValue("Las contraseñas no coinciden");
            return;
        }

        UserDto nuevoUsuario = new UserDto(firstName.trim(), lastName.trim(), email.trim(), password);

        cargando.setValue(true);
        repository.register(nuevoUsuario, new WalletRepository.RepositoryCallback<UserDto>() {
            @Override
            public void onSuccess(UserDto user) {
                cargando.setValue(false);
                registroExitoso.setValue(true);
            }

            @Override
            public void onError(String mensajeErrorTexto) {
                cargando.setValue(false);
                mensajeError.setValue(mensajeErrorTexto);
            }
        });
    }
}