package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.Obras;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;

public class Vista_categorias extends AppCompatActivity {
MetodosObtencion metodosObtencion = new MetodosObtencion();
    private LinearLayout linearLayout;
    TextView lblNombreUsuario;
    String gmail;
    String[] nombreUsuario = new String[1];
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milliseconds == 1 second
    int insertado=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categorias);

        // Iniciadores de ID
        linearLayout = findViewById(R.id.linearLayout);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);

        //Sacar Gmail
        gmail = getIntent().getStringExtra("id_gmail");

        nombreUsuario = metodosObtencion.obtenerNombreUsuario(gmail);

        handler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("Comprobando..."); // Do your work here
                if(!nombreUsuario[0].equals(null) && insertado < 1) {
                    lblNombreUsuario.setText(nombreUsuario[0]);
                    insertado++;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);



        // Crear un ArrayList con elementos de ejemplo
        ArrayList<modeloTeatro> elementos = new ArrayList<>();
        elementos.add(new modeloTeatro(R.drawable.imgtheatrecategory, "Teatro"));
        elementos.add(new modeloTeatro(R.drawable.imgcinemacategory, "Cine"));
        elementos.add(new modeloTeatro(R.drawable.imgmusiccategory, "Concierto"));
        elementos.add(new modeloTeatro(R.drawable.imgsportcategory, "Deporte"));

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
                    Intent IrAVentanaTeatro = new Intent(Vista_categorias.this, Teatro.class);
                    IrAVentanaTeatro.putExtra("id_categoria", elemento.getName().toString());
                    startActivity(IrAVentanaTeatro);
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }
}