package com.example.firestoredemo.vista;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class Prueba extends AppCompatActivity {
    Button botonLlamar;
    TextView texto;
    MetodosObtencion pruebaLlamada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        botonLlamar=findViewById(R.id.botonLlamar);
        texto=findViewById(R.id.texto);
        pruebaLlamada=new MetodosObtencion();

        botonLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoPrueba = pruebaLlamada.prueba("Teatro");
                texto.setText(textoPrueba);
            }
        });

    }
}