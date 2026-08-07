package com.alkewallet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alkewallet.data.Session;
import com.alkewallet.view.TransactionAdapter;
import com.alkewallet.viewmodel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    private TextView txtSaludo;
    private TextView txtSaldo;
    private TextView txtEmptyTransactions;
    private Button btnDeposit;
    private Button btnWithdraw;
    private Button btnProfile;
    private ProgressBar progressBar;
    private RecyclerView recyclerTransactions;

    private HomeViewModel homeViewModel;
    private TransactionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!Session.haySesionActiva()) {
            // No hay sesión activa: volver al login
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        txtSaludo = findViewById(R.id.txtSaludo);
        txtSaldo = findViewById(R.id.txtSaldo);
        txtEmptyTransactions = findViewById(R.id.txtEmptyTransactions);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnProfile = findViewById(R.id.btnProfile);
        progressBar = findViewById(R.id.progressBar);
        recyclerTransactions = findViewById(R.id.recyclerTransactions);

        txtSaludo.setText("Hola, " + Session.getFirstName());

        adapter = new TransactionAdapter();
        recyclerTransactions.setLayoutManager(new LinearLayoutManager(this));
        recyclerTransactions.setAdapter(adapter);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.inicializar(Session.getUserEmail());

        observarViewModel();

        btnDeposit.setOnClickListener(v -> mostrarDialogoMonto(true));
        btnWithdraw.setOnClickListener(v -> mostrarDialogoMonto(false));
        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }

    private void observarViewModel() {
        // El historial viene de Room como LiveData: se actualiza solo,
        // incluso sin conexión (requerimiento de acceso offline).
        homeViewModel.getTransacciones().observe(this, transacciones -> {
            adapter.actualizarLista(transacciones);

            double saldo = homeViewModel.calcularSaldo(transacciones);
            txtSaldo.setText(String.format("$%,.2f", saldo));

            boolean vacio = transacciones == null || transacciones.isEmpty();
            txtEmptyTransactions.setVisibility(vacio ? View.VISIBLE : View.GONE);
            recyclerTransactions.setVisibility(vacio ? View.GONE : View.VISIBLE);
        });

        homeViewModel.getCargando().observe(this, cargando -> {
            progressBar.setVisibility(cargando ? View.VISIBLE : View.GONE);
            btnDeposit.setEnabled(!cargando);
            btnWithdraw.setEnabled(!cargando);
        });

        homeViewModel.getMensajeExito().observe(this, mensaje -> {
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getMensajeError().observe(this, mensaje -> {
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoMonto(boolean esDeposito) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_monto, null);
        EditText inputMonto = dialogView.findViewById(R.id.etMonto);
        EditText inputDescripcion = dialogView.findViewById(R.id.etDescripcion);
        inputMonto.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        String titulo = esDeposito ? "Depositar fondos" : "Retirar fondos";

        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setView(dialogView)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    String textoMonto = inputMonto.getText().toString();
                    String descripcion = inputDescripcion.getText().toString();
                    try {
                        double monto = Double.parseDouble(textoMonto);
                        homeViewModel.realizarTransaccion(monto, descripcion, esDeposito);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Ingresa un monto válido", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}