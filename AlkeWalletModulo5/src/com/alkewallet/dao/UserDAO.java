package com.alkewallet.dao;

import java.util.HashMap;
import java.util.Map;

import com.alkewallet.model.User;

/**
 * DAO de usuarios. Para esta entrega usa un mapa en memoria (Singleton simple)
 * en lugar de base de datos, para mantener el foco en el patrón MVC.
 * Se puede reemplazar fácilmente por una implementación con JDBC más adelante.
 */
public class UserDAO {

    // Instancia única compartida por todos los Servlets (patrón Singleton)
    private static UserDAO instance;
    private Map<String, User> users;

    private UserDAO() {
        users = new HashMap<>();
        // Usuario de prueba para poder loguearse sin pasar por signup
        users.put("demo@alkewallet.com", new User("Usuario", "Demo", "demo@alkewallet.com", "1234"));
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }

    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return users.get(email);
    }

    /**
     * Valida credenciales de login.
     * @return el User si las credenciales son correctas, null en caso contrario
     */
    public User validateLogin(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
