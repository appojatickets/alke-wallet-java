package com.alkewallet;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alkewallet.data.Session;
import com.alkewallet.viewmodel.ProfileViewModel;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imgPerfil;
    private TextView txtNombreCompleto;
    private TextView txtEmail;
    private ProgressBar progressBar;

    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgPerfil = findViewById(R.id.imgPerfil);
        txtNombreCompleto = findViewById(R.id.txtNombreCompleto);
        txtEmail = findViewById(R.id.txtEmail);
        progressBar = findViewById(R.id.progressBar);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        observarViewModel();

        profileViewModel.cargarPerfil(Session.getUserId());
    }

    private void observarViewModel() {
        profileViewModel.getCargando().observe(this, cargando ->
                progressBar.setVisibility(cargando ? android.view.View.VISIBLE : android.view.View.GONE));

        profileViewModel.getPerfil().observe(this, perfil -> {
            if (perfil == null) return;

            txtNombreCompleto.setText(perfil.getFirstName() + " " + perfil.getLastName());
            txtEmail.setText(perfil.getEmail());

            // Carga de imagen con Picasso (requerimiento de la consigna).
            // placeholder: se muestra mientras carga.
            // error: se muestra si la URL falla o no existe.
            String imageUrl = perfil.getProfileImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imgPerfil);
            }
        });

        profileViewModel.getMensajeError().observe(this, mensaje -> {
            if (mensaje != null) {
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }
}