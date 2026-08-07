package com.alkewallet.data.remote;

import com.google.gson.annotations.SerializedName;

/**
 * DTO (Data Transfer Object) del usuario tal como viaja hacia/desde la API REST.
 * Se usa exclusivamente en la capa de red (Retrofit); el ViewModel lo traduce
 * al modelo local cuando corresponde, sin exponer este DTO a la Vista.
 */
public class UserDto {

    @SerializedName("id")
    private String id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("profileImageUrl")
    private String profileImageUrl;

    public UserDto() {
        // Constructor vacío requerido por Gson
    }

    public UserDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}