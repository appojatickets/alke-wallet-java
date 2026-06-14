package com.alkewallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginSignupActivity extends AppCompatActivity {

    private Button btnCrearCuenta;
    private Button btnYaTengoCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnYaTengoCuenta = findViewById(R.id.btnYaTengoCuenta);

        btnCrearCuenta.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginSignupActivity.this,
                    SignupActivity.class
            );

            startActivity(intent);
        });

        btnYaTengoCuenta.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginSignupActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
        });
    }
}