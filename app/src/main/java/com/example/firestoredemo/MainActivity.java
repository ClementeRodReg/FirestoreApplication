package com.example.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        inicioSesion = findViewById(R.id.inicioSesion);
        registrarboton = findViewById(R.id.btn_registro);
        modoInvitado = findViewById(R.id.btn_inicioInvitado);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

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

                if (!(gmail.isEmpty()) && !(contra.isEmpty())) {


                } else {
                    Toast.makeText(getApplicationContext(), "Rellene con los datos necesarios para iniciar sesión.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        modoInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Teatro.class));
            }
        });
    }

}