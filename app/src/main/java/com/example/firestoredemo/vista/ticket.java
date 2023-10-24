package com.example.firestoredemo.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodoBorrarTickets;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.util.ArrayList;

public class ticket extends AppCompatActivity {

    MetodosObtencion metodosObtencion = new MetodosObtencion();
    MetodoBorrarTickets metodoBorrarTickets = new MetodoBorrarTickets();
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milisegundos == 1 segundo
    int insertado = 0;
    TextView lblPrecioTotal;
    private LinearLayout linearLayout;
    float precioTotal = 0;
    ArrayList<String> nombresElementos = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        linearLayout = findViewById(R.id.linearLayout);
        lblPrecioTotal=findViewById(R.id.lblPrecioTotal);

        ArrayList<String> listaTickets = metodosObtencion.obtenerTickets();
        ArrayList<modeloTeatro> elementos = new ArrayList<>();

        handler.postDelayed(new Runnable() {
            public void run() {
                if (!listaTickets.isEmpty() && insertado < 1) {
                    for (String ticket : listaTickets) {
                        String textoTicket = ticket.split(";")[0];
                        elementos.add(new modeloTeatro(R.drawable.imgtheatrecategory, textoTicket));
                        precioTotal = precioTotal + Float.parseFloat(ticket.split(";")[1]);
                        nombresElementos.add("Ticket" + i); // Agregar el nombre a la lista
                        i++;
                    }
                    addBlocksForArrayList(elementos);
                    lblPrecioTotal.setText("Precio total: " + (float) precioTotal + "€");
                    insertado++;
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void addBlocksForArrayList(ArrayList<modeloTeatro> elementos) {
        for (modeloTeatro elemento : elementos) {
            // Inflar el diseño del elemento de evento
            @SuppressLint("InflateParams") View vistaElementoEvento = getLayoutInflater().inflate(R.layout.ticket_text, null);

            // Obtener referencias a los elementos de la vista
            TextView nombreTextView = vistaElementoEvento.findViewById(R.id.nameTextView);
            LinearLayout linearLayoutEvento = vistaElementoEvento.findViewById(R.id.linearLayoutEvento);

            // Configurar el ícono y el nombre
            nombreTextView.setText(elemento.getName());

            // Agregar el OnClickListener para ocultar el elemento cuando se haga clic
            linearLayoutEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayoutEvento.setVisibility(View.GONE);

                    // Obtener el nombre del ticket que se está eliminando
                    String nombreTicket = elemento.getName();

                    // Encontrar la posición del nombre del ticket en la lista de nombresElementos
                    int posicion = nombresElementos.indexOf(nombreTicket) + 1;


                    if (posicion >= 0) {
                        // Si se encuentra el nombre del ticket, eliminarlo de la lista
                        nombresElementos.remove(posicion);

                        // Llamar al método de borrado con el nombre del ticket
                        metodoBorrarTickets.borrarTicket("Ticket" + posicion);

                    }
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }
}
