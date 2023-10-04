package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.firestoredemo.R;

public class SalasHorasFechas extends AppCompatActivity {

    TextView lblEventoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas_horas_fechas);

        // Iniciadores de ID
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);

        //Saca nombre evento seleccionado
        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());
    }
}