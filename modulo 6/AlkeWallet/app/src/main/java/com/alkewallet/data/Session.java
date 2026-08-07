package com.alkewallet.data;

/**
 * Guarda los datos del usuario logueado mientras la app está abierta.
 * A partir del Módulo 6, la fuente de verdad del usuario es la API REST
 * (vía UserDto), no el modelo local del Módulo 5 — por eso ya no guarda
 * un objeto User, sino los campos sueltos que necesita la UI.
 */
public class Session {

    private static String userId;
    private static String userEmail;
    private static String firstName;
    private static String profileImageUrl;

    private Session() {
    }

    public static void iniciarSesion(String userId, String userEmail,
                                     String firstName, String profileImageUrl) {
        Session.userId = userId;
        Session.userEmail = userEmail;
        Session.firstName = firstName;
        Session.profileImageUrl = profileImageUrl;
    }

    public static boolean haySesionActiva() {
        return userEmail != null;
    }

    public static String getUserId() {
        return userId;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static void cerrarSesion() {
        userId = null;
        userEmail = null;
        firstName = null;
        profileImageUrl = null;
    }
}