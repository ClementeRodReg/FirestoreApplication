package com.example.firestoredemo.vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firestoredemo.R;
import com.example.firestoredemo.metodos.MetodoBorrarTickets;
import com.example.firestoredemo.metodos.MetodosObtencion;
import com.example.firestoredemo.modelo.modeloTeatro;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Ticket extends AppCompatActivity {

    MetodosObtencion metodosObtencion = new MetodosObtencion();
    MetodoBorrarTickets metodoBorrarTickets = new MetodoBorrarTickets();
    final Handler handler = new Handler();
    final int delay = 1000; // 1000 milisegundos == 1 segundo
    int insertado = 0;
    TextView lblPrecioTotal;
    Button btn_pagar;
    private LinearLayout linearLayout;
    double precioTotal = 0;
    ArrayList<String> nombresElementos = new ArrayList<>();
    ArrayList<Double> precios = new ArrayList<>();
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        //Obtener informacion seleccionada mediante putExta
        linearLayout = findViewById(R.id.linearLayout);
        lblPrecioTotal = findViewById(R.id.lblPrecioTotal);
        btn_pagar = findViewById(R.id.btn_pagar);

        // Crear un ArrayList con elementos de ejemplo
        ArrayList<String> listaTickets = metodosObtencion.obtenerTickets();
        ArrayList<modeloTeatro> elementos = new ArrayList<>();


        handler.postDelayed(new Runnable() {
            public void run() {
                if (!listaTickets.isEmpty() && insertado < 1) {
                    for (String ticket : listaTickets) {
                        String textoTicket = ticket.split(";")[0];
                        elementos.add(new modeloTeatro(R.drawable.imgtheatrecategory, textoTicket));
                        precios.add(Double.parseDouble(ticket.split(";")[1]));
                        precioTotal = precioTotal + Double.parseDouble(ticket.split(";")[1]);
                        nombresElementos.add("Ticket" + i); // Agregar el nombre a la lista
                        i++;

                        if (precioTotal > 0) {
                            btn_pagar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mostrarPopup();

                                }
                            });
                        }

                    }
                    addBlocksForArrayList(elementos);
                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                    lblPrecioTotal.setText("Precio total: " + df.format((float) precioTotal) + "€");
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

                        precioTotal = (precioTotal - precios.get(posicion));
                        DecimalFormat df = new DecimalFormat("#.##");
                        df.setRoundingMode(RoundingMode.CEILING);
                        if (precioTotal < 0) {
                            lblPrecioTotal.setText("Precio total: 0€");
                        } else {
                            lblPrecioTotal.setText("Precio total: " + df.format(precioTotal) + "€");
                        }
                    }
                }
            });

            // Agregar la vista del evento al LinearLayout principal
            linearLayout.addView(vistaElementoEvento);
        }
    }

    public void mostrarPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (precioTotal <= 0) {
            builder.setTitle("No hay Tickets en la cesta de la compra.");
            builder.setMessage("No hay tickets en la lista, saliendo al login...");
            builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    metodoBorrarTickets.borrarTickets();
                    Intent mover = new Intent(Ticket.this, MainActivity.class);
                    startActivity(mover);
                }
            });
        } else {
            builder.setTitle("Gracias por la compra de nuestros tickets.");
            builder.setMessage("El precio total es de: " + precioTotal + "€");

            builder.setPositiveButton("Comprar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    metodoBorrarTickets.borrarTickets();
                    Intent mover = new Intent(Ticket.this, MainActivity.class);
                    startActivity(mover);
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
