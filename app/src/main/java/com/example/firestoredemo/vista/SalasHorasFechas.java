package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodoInsercion;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.Salas;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalasHorasFechas extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    private ArrayList<String> fechas;
    private LinearLayout linearLayout;
    MetodoInsercion metodoInsercion = new MetodoInsercion();
    String fecha = "";
    String sala = "";
    String nombreEdificio = "";
    String nombreEvento = "";
    double precioEvento = 0;
    String nombreCategoria = "";
    MetodosObtencion metodosObtencion = new MetodosObtencion();
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milliseconds == 1 second
    int insertado=0;
    String numeroSala = "";
    String tipoSala = "";
    String gmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas_horas_fechas);

        // Iniciadores de ID
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        Spinner comboBoxFecha = findViewById(R.id.comboBoxFecha);
        ScrollView scrollView = findViewById(R.id.categoriaScrollView);
        linearLayout = findViewById(R.id.linearLayout);

        //Saca nombre evento seleccionado
        nombreEdificio = getIntent().getStringExtra("clave_edificioNombre");
        lblEventoSeleccionado.setText(nombreEdificio.toString());
        nombreEvento = getIntent().getStringExtra("clave_eventoNombre");
        precioEvento = getIntent().getDoubleExtra("id_precio", 0);
        nombreCategoria = getIntent().getStringExtra("id_categoria");
        sala = getIntent().getStringExtra("clave_salaNombre");
        gmail = getIntent().getStringExtra("id_gmail");

        System.out.println(gmail);

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

        //Insercion de datos en el Spinner/ComboBox
        fechas = metodosObtencion.obtenerfechaYhora(nombreEvento, nombreCategoria, sala, nombreEdificio);

        handler.postDelayed(new Runnable() {
            public void run() {
                if(!fechas.isEmpty() && insertado < 1) {

                    ArrayAdapter<String> adapterFecha = new ArrayAdapter<>(SalasHorasFechas.this, android.R.layout.simple_spinner_dropdown_item, fechas);
                    adapterFecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comboBoxFecha.setAdapter(adapterFecha);
                    fecha = comboBoxFecha.getSelectedItem().toString();
                    insertado++;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);

        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(sala);


        if (matcher.find()) {
            numeroSala = matcher.group();
            if(nombreCategoria.equals("Deporte")){
                tipoSala = "Cancha: " + numeroSala;
            }else{
                tipoSala = "Sala: " + numeroSala;
            }
        }

        //Mostrar salas disponibles
        // Crear un ArrayList con elementos de ejemplo

        ArrayList<modeloTeatro> elementos = new ArrayList<>();
        elementos.add(new modeloTeatro(R.drawable.img0, tipoSala));


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

                    Intent mandar;

                    if(gmail.equals("Modo Invitado")){
                        mandar = new Intent(SalasHorasFechas.this, Vista_categorias.class);
                        mandar.putExtra("id_ticketAnadido", true);
                        mandar.putExtra("id_gmail", gmail);
                        mandar.putExtra("id_invitadoActivo", true);
                    }else{
                        mandar = new Intent(SalasHorasFechas.this, Vista_categorias.class);
                        mandar.putExtra("id_ticketAnadido", true);
                        mandar.putExtra("id_gmail", gmail);
                        mandar.putExtra("id_invitadoActivo", false);
                        metodoInsercion.insertarTicket(fecha, sala, nombreEdificio, nombreEvento, precioEvento);
                    }
                    startActivity(mandar);


                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }

    }
}