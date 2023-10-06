package com.example.firestoredemo.metodos;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.util.Log;

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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                   // Bucle de los edificios que tiene la coleccion que se manda
                   for (QueryDocumentSnapshot documentLocal : task.getResult()) {

                       //Sacar el HashMap de Firebase
                       Map<String, Object> eventosHM = documentLocal.getData();
                       //Bucle de los Eventos
                        for (String nombre : eventosHM.keySet()){
                            Object salasObj = eventosHM.get(nombre);
                            salasObj.toString();
                            //Sala sala = new Sala("","","",0);

                            //Evento evento = new Evento(nombre, salas);
                            //eventos.add(evento);
                        }



                        //Local local = new Local(documentLocal.getId(), eventos);
                        //locales.add(local);

                   }

                   /*Ejemplo de bucle con hashmap
                        for (String clave:mapa.keySet()) {
                            int valor = mapa.get(clave);
                        System.out.println("Clave: " + clave + ", valor: " + valor);
}                       }
                    */


               } else {

               }
           }
       });

        return locales;
    }

}
