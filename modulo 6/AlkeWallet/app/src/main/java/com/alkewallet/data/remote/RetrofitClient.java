package com.alkewallet.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Punto único de configuración de Retrofit (patrón Singleton).
 *
 * IMPORTANTE: BASE_URL apunta hoy a un endpoint de prueba (MockAPI.io)
 * mientras no tengamos la API oficial del curso. El día que la tengas,
 * este es el ÚNICO archivo que hay que tocar: cambiar BASE_URL.
 */
public class RetrofitClient {

    // TODO: reemplazar por la URL real de la API del curso cuando la tengas.
    // Mientras tanto, crea un proyecto gratis en https://mockapi.io con
    // recursos "users" y "transactions" y pega tu URL base aquí (con "/" final).
    private static final String BASE_URL = "https://REEMPLAZAR.mockapi.io/api/v1/";

    private static Retrofit retrofit;

    private RetrofitClient() {
    }

    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }
}