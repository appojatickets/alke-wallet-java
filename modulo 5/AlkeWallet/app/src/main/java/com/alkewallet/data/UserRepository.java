package com.alkewallet.data;

import java.util.HashMap;
import java.util.Map;

import com.alkewallet.model.User;

/**
 * Repositorio en memoria de usuarios (patrón Singleton).
 * Como el proyecto no usa base de datos, guarda los usuarios mientras
 * la app está abierta. Se puede reemplazar más adelante por Room/SQLite.
 */
public class UserRepository {

    private static UserRepository instance;
    private final Map<String, User> users = new HashMap<>();

    private UserRepository() {
        // Usuario de prueba para poder loguearse sin pasar por signup
        users.put("demo@alkewallet.com", new User("Usuario", "Demo", "demo@alkewallet.com", "1234"));
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }

    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    /**
     * Valida credenciales de login.
     * @return el User si son correctas, null si no.
     */
    public User validateLogin(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}