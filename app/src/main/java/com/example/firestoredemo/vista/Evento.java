package com.example.firestoredemo.vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.Obras;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;

public class Evento extends AppCompatActivity {
    int insertado = 0;
    private LinearLayout linearLayout;
    MetodosObtencion metodosObtencion = new MetodosObtencion();
    ArrayList<Obras> listaObras;
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milliseconds == 1 second
    TextView lblEventoSeleccionado;
    String nombreCategoria = "";
    ArrayList<Double> precioEvento = new ArrayList<>();
    ImageView iCargando;
    String gmail = "";

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bloquear la orientación de la pantalla en modo retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_evento);

        iCargando = findViewById(R.id.iCargando);
        // Obtener el ScrollView y LinearLayout del diseño de la actividad
        //ScrollView scrollView = findViewById(R.id.categoriaScrollView);
        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        linearLayout = findViewById(R.id.linearLayout);

        //Obtener categoria seleccionada mediante putExta
        nombreCategoria = getIntent().getStringExtra("id_categoria");
        gmail = getIntent().getStringExtra("id_gmail");

        //Colocación del nombre de la categoria seleccionada
        lblEventoSeleccionado.setText(nombreCategoria);

        // Crear un ArrayList con elementos de ejemplo
        listaObras = metodosObtencion.obtenerObras(nombreCategoria);
        ArrayList<modeloTeatro> elementos = new ArrayList<>();

        handler.postDelayed(new Runnable() {
            public void run() {
                if (!listaObras.isEmpty() && insertado < 1) {
                    for (Obras obra : listaObras) {
                        String nombreObra = obra.getNombre().toLowerCase().replaceAll("\\s+", "");
                        @SuppressLint("DiscouragedApi") int idImagen = getResources().getIdentifier(nombreObra, "drawable", getPackageName());
                        precioEvento.add(obra.getPrecio());
                        elementos.add(new modeloTeatro(idImagen, obra.getNombre()));
                    }
                    iCargando.getLayoutParams().height = 0;
                    addBlocksForArrayList(elementos);
                    insertado++;
                }
                if (insertado < 1)
                    handler.postDelayed(this, delay);
            }
        }, delay);

    }

    private void addBlocksForArrayList(ArrayList<modeloTeatro> elementos) {
        for (modeloTeatro elemento : elementos) {
            // Inflar el diseño del elemento de evento
            @SuppressLint("InflateParams") View vistaElementoEvento = getLayoutInflater().inflate(R.layout.eventosgeneral, null);

            // Obtener referencias a los elementos de la vista
            ImageView iconoImageView = vistaElementoEvento.findViewById(R.id.fotoSeleccionada);
            TextView nombreTextView = vistaElementoEvento.findViewById(R.id.nameTextView);
            LinearLayout linearLayoutEvento = vistaElementoEvento.findViewById(R.id.linearLayoutEvento);

            // Configurar el ícono y el nombre
            iconoImageView.setImageResource(elemento.getIconResId());
            nombreTextView.setText(elemento.getName());

            // Agregar el click listener al linearLayoutEvento
            linearLayoutEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Acciones que deseas realizar cuando se hace click
                    Intent mandar = new Intent(Evento.this, EventoSeleccionado.class);
                    mandar.putExtra("clave_eventoNombre", elemento.getName().toString());
                    mandar.putExtra("id_categoria", nombreCategoria);
                    mandar.putExtra("clave_datoImagen", elemento.getIconResId());
                    mandar.putExtra("id_precio", precioEvento.get(elementos.indexOf(elemento)));
                    mandar.putExtra("id_gmail", gmail);
                    startActivity(mandar);
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }
}
