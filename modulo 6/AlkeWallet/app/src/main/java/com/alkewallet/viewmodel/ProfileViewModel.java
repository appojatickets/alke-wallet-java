package com.alkewallet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alkewallet.data.repository.WalletRepository;
import com.alkewallet.data.remote.UserDto;

/**
 * ViewModel de la pantalla de Perfil.
 * Coordina con el Repository (Retrofit) para traer los datos del usuario,
 * incluyendo la URL de la imagen de perfil que carga Picasso en la Vista.
 */
public class ProfileViewModel extends AndroidViewModel {

    private final WalletRepository repository;

    private final MutableLiveData<UserDto> perfil = new MutableLiveData<>();
    private final MutableLiveData<String> mensajeError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new WalletRepository(application);
    }

    public LiveData<UserDto> getPerfil() {
        return perfil;
    }

    public LiveData<String> getMensajeError() {
        return mensajeError;
    }

    public LiveData<Boolean> getCargando() {
        return cargando;
    }

    public void cargarPerfil(String userId) {
        if (userId == null) {
            mensajeError.setValue("No se pudo identificar al usuario");
            return;
        }

        cargando.setValue(true);
        repository.getUserProfile(userId, new WalletRepository.RepositoryCallback<UserDto>() {
            @Override
            public void onSuccess(UserDto result) {
                cargando.setValue(false);
                perfil.setValue(result);
            }

            @Override
            public void onError(String mensajeErrorTexto) {
                cargando.setValue(false);
                mensajeError.setValue(mensajeErrorTexto);
            }
        });
    }
}