package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.firestoredemo.R;

public class Vista_categorias extends AppCompatActivity {
    ScrollView menuLateral;
    Button botonMenuLateral;
    TextView id_texttheatro;
    TextView id_cine;
    TextView id_deportes;
    TextView mostrargmail;
    TextView id_Concierto;
    LinearLayout idLena;
    Button cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categorias);

        idLena = findViewById(R.id.id_lena);
        botonMenuLateral = findViewById(R.id.idBotonMenuLateral);
        menuLateral = findViewById(R.id.idMenuLateral);
        menuLateral.setVisibility(View.INVISIBLE);

        ViewPropertyAnimator animator = botonMenuLateral.animate();
        id_texttheatro = findViewById(R.id.id_texttheatro);
        id_cine = findViewById(R.id.id_cine);
        id_deportes = findViewById(R.id.id_deportes);
        id_Concierto = findViewById(R.id.id_Concierto);
        mostrargmail = findViewById(R.id.idmostrargmail);
        cerrarsesion = findViewById(R.id.idcerarsesion);

        mostrargmail.setText(getIntent().getStringExtra("email"));

        id_texttheatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrAVentanaTeatro = new Intent(Vista_categorias.this, Teatro.class);
                IrAVentanaTeatro.putExtra("id_categoria", id_texttheatro.getText().toString());
                startActivity(IrAVentanaTeatro);
            }
        });

        id_cine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrAVentanaTeatro = new Intent(Vista_categorias.this, Teatro.class);
                IrAVentanaTeatro.putExtra("id_categoria", id_cine.getText().toString());
                startActivity(IrAVentanaTeatro);
            }
        });

        id_deportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrAVentanaTeatro = new Intent(Vista_categorias.this, Teatro.class);
                IrAVentanaTeatro.putExtra("id_categoria", id_deportes.getText().toString());
                startActivity(IrAVentanaTeatro);
            }
        });

        id_Concierto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrAVentanaTeatro = new Intent(Vista_categorias.this, Teatro.class);
                IrAVentanaTeatro.putExtra("id_categoria", id_Concierto.getText().toString());
                startActivity(IrAVentanaTeatro);
            }
        });



        botonMenuLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuLateral.getVisibility() == View.INVISIBLE) {
                    animator.translationX(680);
                    animator.setDuration(500);
                    animator.start();
                    menuLateral.setVisibility(View.VISIBLE);
                } else {
                    menuLateral.setVisibility(View.INVISIBLE);
                    animator.translationX(10);
                    animator.setDuration(500);
                    animator.start();
                }
            }
        });


        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Vista_categorias.this, MainActivity.class));
            }

        });


    }
}