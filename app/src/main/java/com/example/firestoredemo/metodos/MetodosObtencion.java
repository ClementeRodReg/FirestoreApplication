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

    public ArrayList<Salas> obtenerEdificios(String obra, String nombreCategoria){
        myBBDD = FirebaseFirestore.getInstance();

        ArrayList<Salas> edificiosEnLosQueSeCelebra = new ArrayList<>();
        ArrayList<Salas> edificiosEnLosQueSeCelebraReal = new ArrayList<>();


        ArrayList<String> esd = new ArrayList<>();
        String seCelebra = "";

        if(nombreCategoria.equalsIgnoreCase("Teatro")){
            seCelebra = "SeCelebraT";
        }else if(nombreCategoria.equals("Cine")){
            seCelebra = "SeCelebraC";
        }else if(nombreCategoria.equals("Concierto")){
            seCelebra = "SeCelebraCo";
        }else{
            seCelebra = "Deporte";
        }



        Task coleccion3 = myBBDD.collection("Salas").get();

        coleccion3.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task3) {
                if (task3.isSuccessful()) {
                    // Bucle de los edificios que tiene la coleccion que se manda
                    for (QueryDocumentSnapshot documentLocal : task3.getResult()) {
                        //Sacar el HashMap de Firebase
                        ArrayList<String> nombreSalas = new ArrayList<String>();

                        //bucle para sacar sus datos
                        for (Map.Entry<String, Object> pair : documentLocal.getData().entrySet()) {
                            nombreSalas.add(pair.getValue().toString());
                        }
                        Salas edificio = new Salas(documentLocal.getId(), nombreSalas);
                        edificiosEnLosQueSeCelebra.add(edificio);
                    }
                }
            }
        });

        for(Salas edif : edificiosEnLosQueSeCelebra){
            System.out.println(edif.getNombreEdif());
            for(String sala : edif.getNombreSalas()){
                System.out.println(sala);
            }
        }


        Task coleccion2 = myBBDD.collection(seCelebra).get();
        coleccion2.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                if (task2.isSuccessful()) {
                    // Bucle de los edificios que tiene la coleccion que se manda
                    for (QueryDocumentSnapshot FechaSala : task2.getResult()) {
                        //dividir el id que recibimos de firebase
                        String fecha = FechaSala.getId().split("_")[0];
                        String sala = FechaSala.getId().split("_")[1];

                        //for para mirar si coincide la obra y sala, mirar si ya esta en el arraylist
                        for(Salas edif : edificiosEnLosQueSeCelebra){
                            //bucle del edificio
                            Salas salaMeter = new Salas();
                            salaMeter.setNombreEdif(edif.getNombreEdif());
                            ArrayList<String> nombresDeLasSalas= new ArrayList<>();
                            for(String salaAL : edif.getNombreSalas()){
                                //bucle salas del edificio
                                if(salaAL.equals(sala) && FechaSala.get("NombreObra").equals(obra)){
                                    //misramos si el arraylist que vamos a mandar esta vacio para meter la pirmera sala que coincida
                                    if(edificiosEnLosQueSeCelebraReal.isEmpty()){



                                    }
                                    else {

                                        for (Salas edif2 : edificiosEnLosQueSeCelebraReal) {



                                        }
                                    }
                                }
                                //termina bucle salas del edificio
                            }

                            //termina bucle del edificio
                        }

                    }
                }
            }
        });


        return edificiosEnLosQueSeCelebraReal;
    }

}