package com.alkewallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alkewallet.viewmodel.AuthViewModel;

public class SignupActivity extends AppCompatActivity {

    private EditText etNombre;
    private EditText etApellido;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegistrar;
    private ProgressBar progressBar;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        progressBar = findViewById(R.id.progressBar);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        observarViewModel();

        btnRegistrar.setOnClickListener(v -> authViewModel.registrar(
                etNombre.getText().toString(),
                etApellido.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etConfirmPassword.getText().toString()
        ));
    }

    private void observarViewModel() {
        authViewModel.getCargando().observe(this, cargando -> {
            progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE);
            btnRegistrar.setEnabled(!cargando);
        });

        authViewModel.getRegistroExitoso().observe(this, exitoso -> {
            if (exitoso != null && exitoso) {
                Toast.makeText(this, "Cuenta creada, ahora inicia sesión", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        authViewModel.getMensajeError().observe(this, mensaje -> {
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}