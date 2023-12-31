package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosObtencion;
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
    String nomUser;
    private FirebaseFirestore myBBDD;
    boolean ticketAnyadido = false;
    boolean invitadoActivo = true;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categorias);

        // Bloquear la orientación de la pantalla en modo retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myBBDD = FirebaseFirestore.getInstance();

        // Iniciadores de ID
        linearLayout = findViewById(R.id.linearLayout);
        lblNombreUsuario = findViewById(R.id.lblNombreUsuario);

        //Sacar Datos
        gmail = getIntent().getStringExtra("id_gmail");
        ticketAnyadido = getIntent().getBooleanExtra("id_ticketAnadido", false);
        invitadoActivo = getIntent().getBooleanExtra("id_invitadoActivo", true);
        nomUser = getIntent().getStringExtra("id_nombreNuevo");

        if (gmail.equals("Modo Invitado")) {
            lblNombreUsuario.setText(gmail);
            Toast.makeText(getApplicationContext(), "En modo invitado no puede comprar tickets.", Toast.LENGTH_SHORT).show();
        } else {
            invitadoActivo = false;
            myBBDD = FirebaseFirestore.getInstance();

            // Obtener nombre del usuario
            DocumentReference docRef = myBBDD.collection("Usuarios").document(gmail);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        // Obtener el valor del campo "Nombre"
                        nomUser = documentSnapshot.getString("Nombre");
                        lblNombreUsuario.setText("Bienvenido, " + nomUser);
                    } else {
                        Log.d("MainActivity", "El documento no existe.");
                    }
                }
            });
        }


        // Crear un ArrayList con elementos de ejemplo

        ArrayList<modeloTeatro> elementos = new ArrayList<>();

        if(!invitadoActivo){
            elementos.add(new modeloTeatro(R.drawable.imgusercategory, "Usuario"));
        }

        elementos.add(new modeloTeatro(R.drawable.imgtheatrecategory, "Teatro"));
        elementos.add(new modeloTeatro(R.drawable.imgcinemacategory, "Cine"));
        elementos.add(new modeloTeatro(R.drawable.imgmusiccategory, "Concierto"));
        elementos.add(new modeloTeatro(R.drawable.imgsportcategory, "Deporte"));

        if(ticketAnyadido && !invitadoActivo){
            elementos.add(new modeloTeatro(R.drawable.imgshopcategory, "Ticket"));
        }


        // Agregar bloques con íconos y nombres al LinearLayout
        addBlocksForArrayList(elementos);

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
                    // Por ejemplo, mostrar un Toast

                    Intent mandar;

                    if(elemento.getName().equals("Ticket")){
                        mandar = new Intent(Vista_categorias.this, Ticket.class);
                    }else if(elemento.getName().equals("Usuario")) {
                        mandar = new Intent(Vista_categorias.this, EditNombreUser.class);
                        mandar.putExtra("nombreUsuario", nomUser);
                        mandar.putExtra("id_gmail", gmail);
                    }else{
                        mandar = new Intent(Vista_categorias.this, Evento.class);
                        mandar.putExtra("id_categoria", elemento.getName().toString());
                        mandar.putExtra("id_gmail", gmail);
                    }
                    startActivity(mandar);
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }
}