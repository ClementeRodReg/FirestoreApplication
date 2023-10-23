package com.example.firestoredemo.vista;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestoredemo.R;

public class ventana_pago extends AppCompatActivity {

    private LinearLayout llBotonera;
    private String[] chipData = {"1", "2", "3", "4", "5"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_pago);
        llBotonera = findViewById(R.id.llBotonera);

        for (int i = 0; i < chipData.length; i++) {
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView label = new TextView(this);
            label.setText(chipData[i]);
            label.setGravity(Gravity.CENTER);
            label.setTextColor(Color.WHITE);

            itemLayout.addView(label, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            Button button = new Button(this);
            button.setText("Eliminar");

// Establecer un ancho más pequeño para el botón
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(buttonLayoutParams);

// Establecer el fondo naranja y el texto blanco
            button.setBackgroundColor(getResources().getColor(R.color.orange));
            button.setTextColor(getResources().getColor(android.R.color.white)); // Texto blanco

// Aplicar márgenes para dar un espacio entre los botones
            buttonLayoutParams.setMargins(10, 0, 10, 0);

            itemLayout.addView(button, buttonLayoutParams);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llBotonera.removeView(itemLayout);
                }
            });

            llBotonera.addView(itemLayout);
        }

        TextView label2 = new TextView(this);
        label2.setText("Suma total");
        label2.setGravity(Gravity.CENTER);
        label2.setTextColor(getResources().getColor(R.color.white)); // Texto blanco
        llBotonera.addView(label2);






        TextView textView = new TextView(this);
        textView.setText("");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white)); // Texto blanco
        llBotonera.addView(textView);

        Button button2 = new Button(this);
        button2.setText("Pagar");
        button2.setBackgroundColor(getResources().getColor(R.color.orange)); // Fondo naranja
        button2.setTextColor(getResources().getColor(R.color.white)); // Texto blanco
        llBotonera.addView(button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(textView.length()==0){
                  String text2 = "No tienes ningun compra.";

                  int duration = Toast.LENGTH_SHORT;

                  Toast toast = Toast.makeText(ventana_pago.this, text2, duration);
                  toast.show();
              }else {
                  String text = "La compra ha sido realizada exitosamente.";

                  int duration = Toast.LENGTH_SHORT;

                  Toast toast = Toast.makeText(ventana_pago.this, text, duration);
                  toast.show();
              }
            }
        });
    }
}
