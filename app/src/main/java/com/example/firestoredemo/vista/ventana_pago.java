package com.example.firestoredemo.vista;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.firestoredemo.R;
import com.example.firestoredemo.modelo.Obras;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ventana_pago extends AppCompatActivity {

    int numObras = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_pago);

        FirebaseFirestore myBBDD = FirebaseFirestore.getInstance();

        String ticketNuevo = "Ticket";

        Task coleccion = myBBDD.collection("Tickets").get();

        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Bucle de los edificios que tiene la colección que se manda
                    for (QueryDocumentSnapshot documentLocal : task.getResult()) {
                        // Sacar el HashMap de Firebase
                        Map<String, Object> eventosHM = documentLocal.getData();
                        // Bucle de los Eventos
                        for (Map.Entry<String, Object> pair : eventosHM.entrySet()) {
                            numObras++;
                        }
                    }
                }
            }
        });

        ticketNuevo = ticketNuevo + numObras + 1;
        System.out.println(numObras);


        Map<String, Object> ticket = new HashMap<>();
        ticket.put("Fecha", "Los Angeles");
        ticket.put("Sala", "CA");
        ticket.put("Edifio", "Nada");
        ticket.put("Evento", "Traxxas");
        ticket.put("Precio", 3.2655);

        myBBDD.collection("Tickets").document(ticketNuevo)
                .set(ticket)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });


    }
}