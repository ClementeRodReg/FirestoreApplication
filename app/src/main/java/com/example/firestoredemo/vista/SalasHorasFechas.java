package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;

public class SalasHorasFechas extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    private final String fechas[] = {"20-03-2024", "21-03-2024", "22-03-2024", "23-03-2024"};
    private final String horas[] = {"08:00", "09:30", "11:00", "13:00"};
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas_horas_fechas);

        // Iniciadores de ID
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        Spinner comboBoxFecha = findViewById(R.id.comboBoxFecha);
        Spinner comboBoxHora = findViewById(R.id.comboBoxHora);
        ScrollView scrollView = findViewById(R.id.cacahuete);
        linearLayout = findViewById(R.id.linearLayout);

        //Saca nombre evento seleccionado
        String nombreEvento = getIntent().getStringExtra("clave_datoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());


        //Cambiar Color Spinner/ComboBox
        comboBoxFecha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        comboBoxHora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        ArrayAdapter<String> adapterFecha = new ArrayAdapter<>(SalasHorasFechas.this, android.R.layout.simple_spinner_dropdown_item, fechas);
        adapterFecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboBoxFecha.setAdapter(adapterFecha);

        ArrayAdapter<String> adapterHora = new ArrayAdapter<>(SalasHorasFechas.this, android.R.layout.simple_spinner_dropdown_item, horas);
        adapterHora.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboBoxHora.setAdapter(adapterHora);

        //Mostrar salas disponibles
        // Crear un ArrayList con elementos de ejemplo
        ArrayList<modeloTeatro> elementos = new ArrayList<>();
        elementos.add(new modeloTeatro(R.drawable.img0, "Sala 1"));
        elementos.add(new modeloTeatro(R.drawable.img0, "Sala 2"));


        // Agregar bloques con íconos y nombres al LinearLayout
        addBlocksForArrayList(elementos);
    }

    private void addBlocksForArrayList(ArrayList<modeloTeatro> elementos) {
        for (modeloTeatro elemento : elementos) {
            // Inflar el diseño del elemento de evento
            View vistaElementoEvento = getLayoutInflater().inflate(R.layout.eventosgeneral, null);

            // Obtener referencias a los elementos de la vista
            ImageView iconoImageView = vistaElementoEvento.findViewById(R.id.fotoSeleccionada);
            TextView nombreTextView = vistaElementoEvento.findViewById(R.id.nameTextView);
            LinearLayout linearLayoutEvento = vistaElementoEvento.findViewById(R.id.linearLayoutEvento);

            // Configurar el ícono y el nombre
            iconoImageView.setImageResource(elemento.getIconResId());
            nombreTextView.setText(elemento.getName());

            // Agregar el clic listener al linearLayoutEvento
            linearLayoutEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acciones que deseas realizar cuando se hace clic
                    // Por ejemplo, mostrar un Toast

                    Intent mandar = new Intent(SalasHorasFechas.this, EventoSeleccionado.class);
                    mandar.putExtra("clave_datoNombre", elemento.getName().toString());
                    mandar.putExtra("clave_datoImagen", elemento.getIconResId());
                    startActivity(mandar);
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }

    }
}