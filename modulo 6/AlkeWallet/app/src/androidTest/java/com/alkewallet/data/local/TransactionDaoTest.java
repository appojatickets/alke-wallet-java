package com.alkewallet.data.local;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.alkewallet.model.Transaction;

/**
 * Pruebas de integración del DAO de Room (requerimiento de la consigna:
 * "pruebas unitarias para... el acceso a la base de datos con Room,
 * asegurándote de que las interacciones entre el modelo y la base de
 * datos funcionen correctamente").
 *
 * Usa una base de datos en memoria (se borra al terminar cada prueba),
 * así no toca la base real de la app.
 */
@RunWith(AndroidJUnit4.class)
public class TransactionDaoTest {

    private AppDatabase db;
    private TransactionDao dao;

    @Before
    public void crearBaseEnMemoria() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries() // solo aceptable en pruebas
                .build();
        dao = db.transactionDao();
    }

    @After
    public void cerrarBase() throws IOException {
        db.close();
    }

    @Test
    public void insertar_yConsultarPorUsuario_devuelveLaTransaccion() throws InterruptedException {
        Transaction t = new Transaction(
                "1", "test@alkewallet.com", 100000.0, "Depósito", "DEPOSIT", "2026-08-07");

        dao.insert(t);

        List<Transaction> resultado = obtenerValorSincronico("test@alkewallet.com");

        assertEquals(1, resultado.size());
        assertEquals("Depósito", resultado.get(0).getDescription());
    }

    @Test
    public void insertarVarias_seOrdenanPorFechaDescendente() throws InterruptedException {
        dao.insert(new Transaction("1", "test@alkewallet.com", 1000.0, "Primera", "DEPOSIT", "2026-08-01"));
        dao.insert(new Transaction("2", "test@alkewallet.com", 2000.0, "Segunda", "DEPOSIT", "2026-08-05"));

        List<Transaction> resultado = obtenerValorSincronico("test@alkewallet.com");

        assertEquals(2, resultado.size());
        assertEquals("Segunda", resultado.get(0).getDescription()); // la más reciente primero
    }

    @Test
    public void eliminarTodasDeUnUsuario_dejaListaVacia() throws InterruptedException {
        dao.insert(new Transaction("1", "test@alkewallet.com", 1000.0, "Depósito", "DEPOSIT", "2026-08-07"));

        dao.deleteAllForUser("test@alkewallet.com");

        List<Transaction> resultado = obtenerValorSincronico("test@alkewallet.com");
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void insertarConMismoId_reemplazaLaAnterior() throws InterruptedException {
        dao.insert(new Transaction("1", "test@alkewallet.com", 1000.0, "Original", "DEPOSIT", "2026-08-07"));
        dao.insert(new Transaction("1", "test@alkewallet.com", 5000.0, "Actualizada", "DEPOSIT", "2026-08-07"));

        List<Transaction> resultado = obtenerValorSincronico("test@alkewallet.com");

        assertEquals(1, resultado.size());
        assertEquals("Actualizada", resultado.get(0).getDescription());
    }

    /**
     * Convierte el LiveData del DAO en un valor sincrónico para poder
     * usarlo en una prueba simple sin observadores.
     */
    private List<Transaction> obtenerValorSincronico(String userEmail) throws InterruptedException {
        Transaction[] holder = new Transaction[0];
        final Object[] resultado = new Object[1];
        final java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(1);

        androidx.lifecycle.Observer<List<Transaction>> observer = value -> {
            resultado[0] = value;
            latch.countDown();
        };

        androidx.lifecycle.LiveData<List<Transaction>> liveData = dao.getTransactionsByUser(userEmail);

        new android.os.Handler(android.os.Looper.getMainLooper())
                .post(() -> liveData.observeForever(observer));

        latch.await(2, java.util.concurrent.TimeUnit.SECONDS);

        new android.os.Handler(android.os.Looper.getMainLooper())
                .post(() -> liveData.removeObserver(observer));

        //noinspection unchecked
        return (List<Transaction>) resultado[0];
    }
}