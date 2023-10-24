package com.example.firestoredemo.vista;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodosObtencion;

import java.util.ArrayList;

public class ventana_pago extends AppCompatActivity {

    MetodosObtencion metodosObtencion = new MetodosObtencion();
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milliseconds == 1 second
    int insertado = 0;
    private LinearLayout llBotonera;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_pago);
        llBotonera = findViewById(R.id.llBotonera);

        ArrayList<String> listaTickets = metodosObtencion.obtenerTickets();

        handler.postDelayed(new Runnable() {
            public void run() {
                if (!listaTickets.isEmpty() && insertado < 1) {
                    crearTicketVisual(listaTickets);
                    insertado++;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void crearTicketVisual(ArrayList<String> listaTickets) {
        float precioTotal = 0;

        for (String ticket : listaTickets) {
            precioTotal = precioTotal + Float.parseFloat(ticket.split(";")[1]);

            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView label = new TextView(this);
            label.setText(ticket.split(";")[0]);
            label.setGravity(Gravity.CENTER);
            label.setTextColor(Color.WHITE);
            label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);

            // Establecer el ancho del TextView en base al contenido del texto
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            label.setLayoutParams(layoutParams);

            itemLayout.addView(label);

            Button button = new Button(this);
            button.setText("Eliminar");

            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
            button.setLayoutParams(buttonLayoutParams);

            button.setBackgroundColor(getResources().getColor(R.color.orange));
            button.setTextColor(getResources().getColor(android.R.color.white));
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

        Space space = new Space(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        space.setLayoutParams(params);
        llBotonera.addView(space);

        TextView label2 = new TextView(this);
        label2.setText("Suma total: " + (float) precioTotal + "€");
        label2.setGravity(Gravity.CENTER);
        label2.setTextColor(getResources().getColor(R.color.white));
        llBotonera.addView(label2);

        TextView textView = new TextView(this);
        textView.setText("");
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        llBotonera.addView(textView);

        Button button2 = new Button(this);
        button2.setText("Pagar");
        button2.setBackgroundColor(getResources().getColor(R.color.orange));
        button2.setTextColor(getResources().getColor(R.color.white));
        llBotonera.addView(button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.length() == 0) {
                    String text2 = "No tienes ninguna compra.";

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(ventana_pago.this, text2, duration);
                    toast.show();
                } else {
                    String text = "La compra ha sido realizada exitosamente.";

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(ventana_pago.this, text, duration);
                    toast.show();
                }
            }
        });
    }
}
