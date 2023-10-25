package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodoInsercion;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.Tickets;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalasHorasFechas extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    private ArrayList<String> fechas;
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
    int insertado = 0;
    String numeroSala = "";
    String tipoSala = "";
    String gmail = "";
    Button btn_AnadirTicket;

    Tickets tickets = new Tickets();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salas_horas_fechas);

        // Iniciadores de ID
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        Spinner comboBoxFecha = findViewById(R.id.comboBoxFecha);

        btn_AnadirTicket = findViewById(R.id.btn_AnadirTicket);

        //Saca nombre evento seleccionado
        nombreEdificio = getIntent().getStringExtra("clave_edificioNombre");
        lblEventoSeleccionado.setText(nombreEdificio.toString());
        nombreEvento = getIntent().getStringExtra("clave_eventoNombre");
        precioEvento = getIntent().getDoubleExtra("id_precio", 0);
        nombreCategoria = getIntent().getStringExtra("id_categoria");
        sala = getIntent().getStringExtra("clave_salaNombre");
        gmail = getIntent().getStringExtra("id_gmail");

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
                if (!fechas.isEmpty() && insertado < 1) {
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
            if (nombreCategoria.equals("Deporte")) {
                tipoSala = "Cancha: " + numeroSala;
            } else {
                tipoSala = "Sala: " + numeroSala;
            }
        }

        btn_AnadirTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tickets.setFecha(fecha);
                tickets.setSala(tipoSala);
                tickets.setEdificio(nombreEdificio);
                tickets.setEvento(nombreEvento);
                tickets.setPrecio(precioEvento);

                Intent mandar;
                if (gmail.equals("Modo Invitado")) {
                    mandar = new Intent(SalasHorasFechas.this, Vista_categorias.class);
                    mandar.putExtra("id_ticketAnadido", true);
                    mandar.putExtra("id_gmail", gmail);
                    mandar.putExtra("id_invitadoActivo", true);
                } else {
                    mandar = new Intent(SalasHorasFechas.this, Vista_categorias.class);
                    mandar.putExtra("id_ticketAnadido", true);
                    mandar.putExtra("id_gmail", gmail);
                    mandar.putExtra("id_invitadoActivo", false);
                    metodoInsercion.insertarTicket(tickets);
                }
                startActivity(mandar);

            }
        });



    }


}