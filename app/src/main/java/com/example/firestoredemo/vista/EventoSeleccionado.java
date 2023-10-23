package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosEventoSeleccionado;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.Salas;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;


public class EventoSeleccionado extends AppCompatActivity {

    TextView lblEventoSeleccionado;
    ImageView imageView;
    private LinearLayout linearLayout;
    MetodosObtencion metodosObtencion = new MetodosObtencion();
    ArrayList<Salas> listaEdificios;
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milliseconds == 1 second
    int insertado=0;
    String nombreCategoria = "";
    String nombreEvento = "";
    double precioEvento = 0;
    String nombreSala = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoseleccionado);

        MetodosEventoSeleccionado metodosEventoSeleccionado = new MetodosEventoSeleccionado();

        int imagenReferencia = getIntent().getIntExtra("clave_datoImagen", 0);

        // Iniciadores de ID

        lblEventoSeleccionado = findViewById(R.id.lblEventoSeleccionado);
        imageView = findViewById(R.id.fotoSeleccionada);

        //Saca nombre e imagen lugar seleccionado
        imageView.setImageResource(imagenReferencia);

        nombreEvento = getIntent().getStringExtra("clave_eventoNombre");
        lblEventoSeleccionado.setText(nombreEvento.toString());

        nombreCategoria = getIntent().getStringExtra("id_categoria");

        precioEvento = getIntent().getDoubleExtra("id_precio", 0);

        // Obtener el ScrollView y LinearLayout del diseño de la actividad
        ScrollView scrollView = findViewById(R.id.categoriaScrollView);
        linearLayout = findViewById(R.id.linearLayout);

        // Crear un ArrayList con elementos de ejemplo
        listaEdificios = metodosObtencion.obtenerEdificios(nombreEvento, nombreCategoria);
        ArrayList<modeloTeatro> elementos = new ArrayList<>();

        handler.postDelayed(new Runnable() {
            public void run() {
                if(!listaEdificios.isEmpty() && insertado < 1) {
                    for (Salas edificio : listaEdificios) {
                        String nombreEdificio = edificio.getNombreEdif().toLowerCase().replaceAll("\\s+", "");
                        int idImagen = getResources().getIdentifier(nombreEdificio, "drawable", getPackageName());
                        nombreSala = edificio.getNombreSalas().toString();

                        elementos.add(new modeloTeatro(idImagen, edificio.getNombreEdif()));
                    }
                    addBlocksForArrayList(elementos);
                    insertado++;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);


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

                    Intent mandar = new Intent(EventoSeleccionado.this, SalasHorasFechas.class);
                    mandar.putExtra("clave_edificioNombre", elemento.getName().toString());
                    mandar.putExtra("clave_eventoNombre", nombreEvento.toString());
                    mandar.putExtra("id_categoria", nombreCategoria.toString());
                    mandar.putExtra("id_precio", precioEvento);
                    mandar.putExtra("clave_salaNombre", nombreSala);
                    startActivity(mandar);
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }

}