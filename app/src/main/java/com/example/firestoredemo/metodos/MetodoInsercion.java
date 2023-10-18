package com.example.firestoredemo.metodos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.firestoredemo.modelo.Obras;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Deadline;

public class MetodoInsercion {

    FirebaseFirestore myBBDD;


    public void insertarTicket(String fecha, String sala, String edifio, String evento, float precio) {

        myBBDD = FirebaseFirestore.getInstance();

        ArrayList<String> ticketList = new ArrayList<String>();

        Task coleccion = myBBDD.collection("Tickets").get();

        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Bucle de los edificios que tiene la coleccion que se manda
                    for (QueryDocumentSnapshot documentLocal : task.getResult()) {
                        //Sacar el HashMap de Firebase
                        Map<String, Object> eventosHM = documentLocal.getData();
                        //Bucle de los Eventos
                        for (Map.Entry<String, Object> pair : eventosHM.entrySet()) {
                            String ticket = String.valueOf(new Obras(documentLocal.getId(), Double.valueOf(pair.getValue().toString())));
                            ticketList.add(ticket);
                            System.out.println(ticket.length());
                        }
                    }
                }
            }
        });


        Map<String, Object> ticket = new HashMap<>();
        ticket.put("Fecha", fecha);
        ticket.put("Sala", sala);
        ticket.put("Edifio", edifio);
        ticket.put("Evento", evento);
        ticket.put("Precio", precio);

        myBBDD.collection("Tickets").document("Ticket")
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
