package com.alkewallet.data;

import com.alkewallet.model.User;

/**
 * Guarda el usuario actualmente logueado mientras la app está abierta.
 * Cumple el rol que en una app web cumpliría la sesión HTTP.
 */
public class Session {

    private static User usuarioActual;

    private Session() {
    }

    public static void iniciarSesion(User user) {
        usuarioActual = user;
    }

    public static User getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }
}