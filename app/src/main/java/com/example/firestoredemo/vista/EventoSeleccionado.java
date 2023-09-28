package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firestoredemo.R;

public class EventoSeleccionado extends AppCompatActivity {

    TextView lblEventoSeleccionado ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoseleccionado);

        int imagenReferencia = getIntent().getIntExtra("clave_datoImagen", 0);

        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        imageView = findViewById(R.id.fotoSeleccionada);
        imageView.setImageResource(imagenReferencia);

        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());


    }
}