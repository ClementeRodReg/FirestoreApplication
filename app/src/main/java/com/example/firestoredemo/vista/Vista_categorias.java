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

    TextView mostrargmail;
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
      mostrargmail = findViewById(R.id.idmostrargmail);
        mostrargmail.setText(getIntent().getStringExtra("email"));


        cerrarsesion = findViewById(R.id.idcerarsesion);

        id_texttheatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Vista_categorias.this, Teatro.class));
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
                    animator.translationX(30);
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