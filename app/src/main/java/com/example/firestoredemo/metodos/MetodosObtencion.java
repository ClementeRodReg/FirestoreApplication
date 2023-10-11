package com.example.firestoredemo.metodos;


import com.example.firestoredemo.modelo.Obras;
import com.example.firestoredemo.modelo.Salas;
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

    public ArrayList<Obras> obtenerObras(String nombreCategoria){

        ArrayList<Obras> obrasList = new ArrayList<Obras>();
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
                           System.out.println(obra.getNombre());
                           System.out.println(obra.getPrecio());
                       }
                   }
               }
           }
       });
        return obrasList;
    }

    public ArrayList<Salas> obtenerEdificios(String obra){

        ArrayList<Salas> edificios = new ArrayList<>();
        myBBDD = FirebaseFirestore.getInstance();
        Task coleccion = myBBDD.collection("Salas").get();

        ArrayList<Salas> edificiosEnLosQueSeCelebra = new ArrayList<>();

        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Bucle de los edificios que tiene la coleccion que se manda
                    for (QueryDocumentSnapshot documentLocal : task.getResult()) {
                        //Sacar el HashMap de Firebase
                        ArrayList<String> nombreSalas = new ArrayList<>();

                        //bucle para sacar sus datos
                        for (Map.Entry<String, Object> pair : documentLocal.getData().entrySet()) {
                            nombreSalas.add(pair.getValue().toString());
                        }

                        //insertamos todo en la calse personalizada
                        Salas edifio = new Salas(documentLocal.getId(), nombreSalas);

                        //metemos el objeto personalizado en el Arraylist
                        edificios.add(edifio);
                    }
                }
            }
        });

        Task coleccion2 = myBBDD.collection("SeCelebraT").get();

        coleccion2.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                if (task2.isSuccessful()) {
                    // Bucle de los edificios que tiene la coleccion que se manda
                    for (QueryDocumentSnapshot FechaSala : task2.getResult()) {
                        //dividir el id que recibimos de firebase
                        String fecha = FechaSala.getId().split("_")[0];
                        String sala = FechaSala.getId().split("_")[1];
                        if(!edificiosEnLosQueSeCelebra.contains(sala)){

                            if(FechaSala.get("NombreObra").equals(obra)){

                                System.out.println(sala);
                            }

                        }



                    }
                }
            }
        });





        return edificiosEnLosQueSeCelebra;
    }

}
