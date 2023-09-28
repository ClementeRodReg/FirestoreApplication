package com.example.firestoredemo.metodos;


import com.example.firestoredemo.modelo.Evento;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MetodosObtencion {

    FirebaseFirestore myBBDD;

    public List<Evento> obtenerEventos(String nombreColeccion){
        List<Evento> eventos = new ArrayList<>();
        myBBDD = FirebaseFirestore.getInstance();

        Task coleccion = myBBDD.collection(nombreColeccion).get();

       coleccion.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        return eventos;
    }







}
