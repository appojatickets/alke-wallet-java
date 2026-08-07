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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCrearCuenta;
    private ProgressBar progressBar;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        progressBar = findViewById(R.id.progressBar);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        observarViewModel();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            authViewModel.login(email, password);
        });

        btnCrearCuenta.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    private void observarViewModel() {
        authViewModel.getCargando().observe(this, cargando -> {
            progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE);
            btnLogin.setEnabled(!cargando);
        });

        authViewModel.getLoginExitoso().observe(this, exitoso -> {
            if (exitoso != null && exitoso) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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