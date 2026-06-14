package com.alkewallet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnSendMoney;
    private Button btnRequestMoney;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSendMoney = findViewById(R.id.btnSendMoney);
        btnRequestMoney = findViewById(R.id.btnRequestMoney);
        btnProfile = findViewById(R.id.btnProfile);

        // Ir a Enviar Dinero
        btnSendMoney.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    SendMoneyActivity.class
            );

            startActivity(intent);
        });

        // Ir a Solicitar Dinero
        btnRequestMoney.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    RequestMoneyActivity.class
            );

            startActivity(intent);
        });

        // Ir a Perfil
        btnProfile.setOnClickListener(v -> {

            Intent intent = new Intent(
                    HomeActivity.this,
                    ProfileActivity.class
            );

            startActivity(intent);
        });
    }
}