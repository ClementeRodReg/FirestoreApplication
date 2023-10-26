package com.example.firestoredemo.metodos;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class MetodoBorrarTickets {
    FirebaseFirestore myBBDD;

    public void borrarTickets() {

        myBBDD = FirebaseFirestore.getInstance();
        Task coleccion = myBBDD.collection("Tickets").get();
        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documentoTicket : task.getResult()) {

                        myBBDD.collection("Tickets").document(documentoTicket.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "Ticket eliminado!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                }
            }
        });


    }

    public void borrarTicket(String nomTicket) {

        myBBDD = FirebaseFirestore.getInstance();
        System.out.println(nomTicket);
        DocumentReference documentRef = myBBDD.collection("Tickets").document(nomTicket);
        documentRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Documento eliminado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Documento NO eliminado");

                    }
                });
    }

}
