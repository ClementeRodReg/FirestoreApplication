package com.example.firestoredemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button registrarboton;
    Button inicioSesion;
    Button modoInvitado;
    EditText passwLogin;
    EditText gmailLogin;

    String gmail;
    String contra;

    private FirebaseFirestore myBBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicioSesion = findViewById(R.id.inicioSesion);
        registrarboton = findViewById(R.id.button);

        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrarUsuarios.class));
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                passwLogin = findViewById(R.id.passwLogin);
                gmailLogin = findViewById(R.id.loginGmail);

                gmail = gmailLogin.getText().toString();
                contra = passwLogin.getText().toString();

                if (!(gmail.isEmpty())) {

                    myBBDD = FirebaseFirestore.getInstance();
                    Task<DocumentSnapshot> datos = myBBDD.collection("Usuarios").document(gmail).get();

                    datos.addOnSuccessListener(result -> {
                                // now do something with result
                                if (datos.getResult().getData() != null) {
                                    if (datos.getResult().getData().get("Contraseña").equals(passwLogin.getText().toString())) {
                                        Toast.makeText(getApplicationContext(), "Inicio correcto", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Registrese para poder iniciar sesión.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> {
                                // now do something with the exception
                                Toast.makeText(getApplicationContext(), "Espere un segundo, ya casi esta.", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Rellene con los datos necesarios para iniciar sesión.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}