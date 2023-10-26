package com.example.firestoredemo.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firestoredemo.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNombreUser extends AppCompatActivity {
    String gmail;
    String nomUsuario;
    private FirebaseFirestore myBBDD;
    EditText texteditarusuario;
    Button btnAceptar;



    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nombre_user);

        // Bloquear la orientación de la pantalla en modo retrato
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Obtener informacion seleccionada mediante putExta
        gmail = getIntent().getStringExtra("id_gmail");
        nomUsuario = getIntent().getStringExtra("nombreUsuario");

        //Conectar xml con las variables del java
        texteditarusuario=findViewById(R.id.texteditarusuario);
        btnAceptar=findViewById(R.id.botonAceptar);

        //Poner nombre recogido en texteditarusuario
        texteditarusuario.setText(nomUsuario);

        //Boton de aceptar nombre y su actualización
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("Nombre", texteditarusuario.getText().toString());
                myBBDD = FirebaseFirestore.getInstance();
                myBBDD.collection("Usuarios").document(gmail.toLowerCase()).update(datos)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");

                                //Si la actualizacion ha sido correcta nos devuelve a vista categorias enviando algun dato
                                Intent mandar;
                                mandar = new Intent(EditNombreUser.this, Vista_categorias.class);
                                mandar.putExtra("id_nombreNuevo", nomUsuario);
                                mandar.putExtra("id_gmail", gmail);
                                startActivity(mandar);

                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }

        });
    }
}