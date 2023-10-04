package com.example.firestoredemo.metodos;


import com.example.firestoredemo.modelo.Evento;
import com.example.firestoredemo.modelo.Local;
import com.example.firestoredemo.modelo.Sala;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MetodosObtencion {

    FirebaseFirestore myBBDD;

    public List<Local> obtenerEventos(String nombreColeccion){

        List<Evento> eventos = new ArrayList<>();
        List<Local> locales = new ArrayList<>();
        List<Sala> salas = new ArrayList<>();
        myBBDD = FirebaseFirestore.getInstance();

        Task coleccion = myBBDD.collection(nombreColeccion).get();

       coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   for (QueryDocumentSnapshot documentLocal : task.getResult()) {


                        Sala sala = new Sala("","","",0);
                        Evento evento = new Evento("a", salas);

                        Local local = new Local(documentLocal.getId(), eventos);
                        locales.add(local);
                   }
               } else {

               }
           }
       });

        return locales;
    }







}
