package com.example.firestoredemo.metodos;

import static android.content.ContentValues.TAG;

import android.util.Log;
import com.example.firestoredemo.modelo.Tickets;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;


public class MetodoInsercion {

    FirebaseFirestore myBBDD;

    public void insertarTicket(Tickets ticket) {

        myBBDD = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> coleccion = myBBDD.collection("Tickets").get();

        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();

                    // Encontrar la primera posición disponible
                    int primeraPosicionDisponible = 0;

                    for (QueryDocumentSnapshot document : result) {
                        int posicionActual = Integer.parseInt(document.getId().replace("Ticket", ""));
                        if (posicionActual == primeraPosicionDisponible) {
                            primeraPosicionDisponible++;
                        }
                    }

                    // Si no se encontró una posición disponible en medio, ir al final
                    if (primeraPosicionDisponible == result.size()) {
                        primeraPosicionDisponible = result.size();
                    }

                    String nuevoDocumento = "Ticket" + primeraPosicionDisponible;

                    myBBDD.collection("Tickets").document(nuevoDocumento)
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
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

}

