package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firestoredemo.R;

import java.util.ArrayList;

public class SalasHorasFechas extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    private final String fechas[] = {"20-03-2024", "21-03-2024", "22-03-2024", "23-03-2024"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas_horas_fechas);

        // Iniciadores de ID
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        Spinner comboBox = findViewById(R.id.comboBox);

        //Saca nombre evento seleccionado
        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());


        //Cambiar Color Spinner/ComboBox
        comboBox.setBackgroundColor(Color.rgb(0, 0, 0));
        comboBox.setPopupBackgroundResource(android.R.color.black);
        comboBox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.rgb(255, 255, 255));
                ((TextView) view).setTextSize(20);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No es necesario hacer nada aquí
            }
        });



        //Insercion de datos en el Spinner/ComboBox
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SalasHorasFechas.this, android.R.layout.simple_spinner_dropdown_item , fechas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboBox.setAdapter(adapter);

    }
}