package com.alkewallet.data.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alkewallet.model.Transaction;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Pruebas de integración de Retrofit usando un servidor HTTP simulado
 * (MockWebServer), sin depender de la API real ni de conexión a internet.
 *
 * Cumple el requerimiento de la consigna: "pruebas de integración para
 * garantizar que Retrofit realice las solicitudes correctamente y maneje
 * las respuestas adecuadamente".
 */
public class ApiServiceTest {

    private MockWebServer server;
    private ApiService apiService;

    @Before
    public void iniciarServidorSimulado() {
        server = new MockWebServer();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @After
    public void detenerServidor() throws IOException {
        server.shutdown();
    }

    @Test
    public void getTransactions_respuestaExitosa_parseaLaListaCorrectamente() throws IOException, InterruptedException {
        String jsonRespuesta = "["
                + "{\"id\":\"1\",\"userEmail\":\"test@alkewallet.com\",\"amount\":100000,"
                + "\"description\":\"Depósito\",\"type\":\"DEPOSIT\",\"date\":\"2026-08-07\"}"
                + "]";

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonRespuesta)
                .addHeader("Content-Type", "application/json"));

        Response<List<Transaction>> response = apiService
                .getTransactions("test@alkewallet.com")
                .execute();

        assertTrue(response.isSuccessful());
        assertEquals(1, response.body().size());
        assertEquals("Depósito", response.body().get(0).getDescription());
        assertEquals(100000.0, response.body().get(0).getAmount(), 0.001);

        // Verifica que Retrofit haya armado bien la URL con el query param
        okhttp3.mockwebserver.RecordedRequest recorded = server.takeRequest();
        assertTrue(recorded.getPath().contains("userEmail=test%40alkewallet.com"));
    }

    @Test
    public void createTransaction_respuestaExitosa_devuelveLaTransaccionCreada() throws IOException {
        String jsonRespuesta = "{\"id\":\"99\",\"userEmail\":\"test@alkewallet.com\",\"amount\":50000,"
                + "\"description\":\"Retiro\",\"type\":\"WITHDRAW\",\"date\":\"2026-08-07\"}";

        server.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(jsonRespuesta)
                .addHeader("Content-Type", "application/json"));

        Transaction nueva = new Transaction(
                "99", "test@alkewallet.com", 50000.0, "Retiro", "WITHDRAW", "2026-08-07");

        Response<Transaction> response = apiService.createTransaction(nueva).execute();

        assertTrue(response.isSuccessful());
        assertEquals("99", response.body().getId());
    }

    @Test
    public void getTransactions_errorDelServidor_respuestaNoExitosa() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(500));

        Response<List<Transaction>> response = apiService
                .getTransactions("test@alkewallet.com")
                .execute();

        assertTrue(!response.isSuccessful());
        assertEquals(500, response.code());
    }
}