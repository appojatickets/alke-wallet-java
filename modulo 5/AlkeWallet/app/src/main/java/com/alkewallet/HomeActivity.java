package com.alkewallet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alkewallet.data.Session;
import com.alkewallet.model.User;

public class HomeActivity extends AppCompatActivity {

    private TextView txtSaludo;
    private TextView txtSaldo;
    private Button btnDeposit;
    private Button btnWithdraw;

    private User usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usuarioActual = Session.getUsuarioActual();

        if (usuarioActual == null) {
            // No hay sesión activa: volver al login
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        txtSaludo = findViewById(R.id.txtSaludo);
        txtSaldo = findViewById(R.id.txtSaldo);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);

        actualizarVista();

        btnDeposit.setOnClickListener(v -> mostrarDialogoMonto(true));
        btnWithdraw.setOnClickListener(v -> mostrarDialogoMonto(false));
    }

    private void actualizarVista() {
        txtSaludo.setText("Hola, " + usuarioActual.getFirstName());
        txtSaldo.setText(String.format("$%,.2f", usuarioActual.getAccount().getBalance()));
    }

    private void mostrarDialogoMonto(boolean esDeposito) {
        EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Monto");

        String titulo = esDeposito ? "Depositar fondos" : "Retirar fondos";

        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setView(input)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    String texto = input.getText().toString();
                    try {
                        double monto = Double.parseDouble(texto);
                        if (esDeposito) {
                            usuarioActual.getAccount().deposit(monto);
                            Toast.makeText(this, "Depósito realizado con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            usuarioActual.getAccount().withdraw(monto);
                            Toast.makeText(this, "Retiro realizado con éxito", Toast.LENGTH_SHORT).show();
                        }
                        actualizarVista();
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Ingresa un monto válido", Toast.LENGTH_SHORT).show();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}