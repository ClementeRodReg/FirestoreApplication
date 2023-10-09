package com.example.firestoredemo.metodos;


import com.example.firestoredemo.modelo.Obras;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetodosObtencion {

    FirebaseFirestore myBBDD;

    public List<Obras> obtenerObras(String nombreCategoria){

        List<Obras> obrasList = new ArrayList<Obras>();
        myBBDD = FirebaseFirestore.getInstance();
        Task coleccion = myBBDD.collection(nombreCategoria).get();

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
                           Obras obra = new Obras(documentLocal.getId(), Double.valueOf(pair.getValue().toString()));
                           obrasList.add(obra);
                       }
                   }
               }
           }
       });
        return obrasList;
    }

}
