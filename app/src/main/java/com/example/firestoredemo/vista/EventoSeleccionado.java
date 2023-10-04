package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosEventoSeleccionado;

import java.util.ArrayList;


public class EventoSeleccionado extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoseleccionado);

        MetodosEventoSeleccionado metodosEventoSeleccionado = new MetodosEventoSeleccionado();

        int imagenReferencia = getIntent().getIntExtra("clave_datoImagen", 0);

        // Iniciadores de ID

        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        imageView = findViewById(R.id.fotoSeleccionada);

        imageView.setImageResource(imagenReferencia);

        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());



    }
}