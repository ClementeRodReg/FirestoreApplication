package com.example.firestoredemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button registrarboton;

    Button inicioSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrarboton = findViewById (R.id.button);
        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrarUsuarios.class));
            }
        });

        inicioSesion = findViewById (R.id.inicioSesion);
        inicioSesion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View j) {
                startActivity(new Intent(MainActivity.this, Login_usuarios.class));
            }
        });

    }

}