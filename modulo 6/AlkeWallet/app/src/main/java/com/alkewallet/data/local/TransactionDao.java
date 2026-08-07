package com.alkewallet.data.local;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alkewallet.model.Transaction;

/**
 * DAO de transacciones. Cumple el requerimiento de la consigna:
 * "Implementar la DAO para realizar operaciones CRUD sobre la base de datos".
 *
 * Devuelve LiveData donde tiene sentido, para que la Vista se actualice
 * automáticamente cuando cambian los datos locales (patrón MVVM).
 */
@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Transaction> transactions);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE userEmail = :userEmail ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactionsByUser(String userEmail);

    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    Transaction getById(String id);

    @Query("DELETE FROM transactions WHERE userEmail = :userEmail")
    void deleteAllForUser(String userEmail);
}