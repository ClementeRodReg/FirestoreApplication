package com.example.firestoredemo.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestoredemo.R;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;

public class Teatro extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establecer el diseño de la actividad
        setContentView(R.layout.activity_teatro);

        // Obtener el ScrollView y LinearLayout del diseño de la actividad
        ScrollView scrollView = findViewById(R.id.cacahuete);
        linearLayout = findViewById(R.id.linearLayout);

        // Crear un ArrayList con elementos de ejemplo
        ArrayList<modeloTeatro> elementos = new ArrayList<>();
        elementos.add(new modeloTeatro(R.drawable.img1, "Las joyas de París"));
        elementos.add(new modeloTeatro(R.drawable.img2, "Teatro Liceo"));
        elementos.add(new modeloTeatro(R.drawable.img3, "Teatro Colón"));
        elementos.add(new modeloTeatro(R.drawable.img4, "Italia y su tradición"));
        elementos.add(new modeloTeatro(R.drawable.img5, "Ópera de Sídney"));
        elementos.add(new modeloTeatro(R.drawable.img6, "Royal Opera House"));
        elementos.add(new modeloTeatro(R.drawable.img7, "Metropolitan Ópera House"));
        elementos.add(new modeloTeatro(R.drawable.img8, "Ópera House Oslo"));
        elementos.add(new modeloTeatro(R.drawable.img9, "Ópera estatal de Viena"));
        elementos.add(new modeloTeatro(R.drawable.img10, "El Bolshoi"));

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

                    Intent mandar = new Intent(Teatro.this, EventoSeleccionado.class);
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
