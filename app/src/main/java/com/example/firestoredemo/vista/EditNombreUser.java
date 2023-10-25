package com.example.firestoredemo.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nombre_user);

        gmail = getIntent().getStringExtra("id_gmail");
        nomUsuario = getIntent().getStringExtra("nombreUsuario");
        texteditarusuario=findViewById(R.id.texteditarusuario);
        texteditarusuario.setText(nomUsuario);

        btnAceptar=findViewById(R.id.botonAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("Nombre", texteditarusuario.getText().toString());
                myBBDD = FirebaseFirestore.getInstance();
                myBBDD.collection("Usuarios").document(gmail.toLowerCase()).set(datos)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

            }
        });
    }
}