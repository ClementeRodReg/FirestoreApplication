package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Vista_categorias extends AppCompatActivity {
    MetodosObtencion metodosObtencion = new MetodosObtencion();
    private LinearLayout linearLayout;
    TextView lblNombreUsuario;
    String gmail;
    String nombreUsuario;
    private FirebaseFirestore myBBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categorias);

        myBBDD = FirebaseFirestore.getInstance();

        // Iniciadores de ID
        linearLayout = findViewById(R.id.linearLayout);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);

        //Sacar Gmail
        gmail = getIntent().getStringExtra("id_gmail");

        if (gmail.equals("Modo Invitado")) {
            lblNombreUsuario.setText(gmail);
        } else {

            myBBDD = FirebaseFirestore.getInstance();

            // Obtener nombre del usuario
            DocumentReference docRef = myBBDD.collection("Usuarios").document(gmail);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Obtener el valor del campo "Nombre"
                        String nombre = documentSnapshot.getString("Nombre");
                        lblNombreUsuario.setText("Bienvenido, " + nombre);

                    } else {
                        Log.d("MainActivity", "El documento no existe.");
                    }
                }
            });

        }


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