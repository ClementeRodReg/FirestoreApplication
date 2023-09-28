package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ScrollView;

import com.example.firestoredemo.R;

public class Vista_categorias extends AppCompatActivity {
    ScrollView menuLateral;
    Button botonMenuLateral;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_categorias);
        botonMenuLateral=findViewById(R.id.idBotonMenuLateral);
        menuLateral = findViewById(R.id.idMenuLateral);
        menuLateral.setVisibility(View.INVISIBLE);
        ViewPropertyAnimator animateBoton = botonMenuLateral.animate();
        botonMenuLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuLateral.getVisibility() == View.INVISIBLE) {
                    animateBoton.translationX(680);
                    animateBoton.setDuration(500);
                    animateBoton.start();
                    menuLateral.setVisibility(View.VISIBLE);
                }
                else{
                    menuLateral.setVisibility(View.INVISIBLE);
                    animateBoton.translationX(30);
                    animateBoton.setDuration(500);
                    animateBoton.start();
                }

            }
        });

    }




}