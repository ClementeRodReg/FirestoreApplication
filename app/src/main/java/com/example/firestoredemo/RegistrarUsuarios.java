package com.example.firestoredemo;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarios extends AppCompatActivity {
    private FirebaseFirestore myBBDD;
    Button registrarse;
    EditText nombre;
    EditText passw;
    EditText dni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuarios);
        FirebaseApp.initializeApp(this);
        this.setTitle("Registrar Usuario");



       nombre = findViewById(R.id.nombre);
       passw = findViewById(R.id.passw);
       dni = findViewById(R.id.dni);
       registrarse = findViewById(R.id.registrarse);

       registrarse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String nombreUser = nombre.getText().toString();
               String passwUser = passw.getText().toString();
               String dniUser = dni.getText().toString();

               if(nombreUser.isEmpty() || passwUser.isEmpty() || dniUser.isEmpty()){
                   Toast.makeText(getApplicationContext(), "Por Favor, rellena los campos.", Toast.LENGTH_SHORT).show();
               }else{
                   enviarUsuario(nombreUser, passwUser, dniUser);
               }

           }
       });
    }

    private void enviarUsuario(String nombreUser, String passwUser, String dniUser) {

        Map<String, Object> datos = new HashMap<>();
        datos.put("Nombre",nombreUser);
        datos.put("Contraseña",passwUser);
        myBBDD = FirebaseFirestore.getInstance();
        myBBDD.collection("Usuarios").document(dniUser).set(datos)
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}