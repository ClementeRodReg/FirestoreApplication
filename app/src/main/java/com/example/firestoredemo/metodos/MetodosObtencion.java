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

    public ArrayList<Obras> obtenerObras(String nombreCategoria) {

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
                        }
                    }
                }
            }
        });
        return obrasList;
    }

    public ArrayList<Salas> obtenerEdificios(String obra, String nombreCategoria) {
        myBBDD = FirebaseFirestore.getInstance();

        ArrayList<Salas> edificiosEnLosQueSeCelebra = new ArrayList<>();
        ArrayList<Salas> edificiosEnLosQueSeCelebraReal = new ArrayList<>();

        ArrayList<String> esd = new ArrayList<>();
        String seCelebraC = "";

        if (nombreCategoria.equalsIgnoreCase("Teatro")) {
            seCelebraC = "SeCelebraT";
        } else if (nombreCategoria.equals("Cine")) {
            seCelebraC = "SeCelebraC";
        } else if (nombreCategoria.equals("Concierto")) {
            seCelebraC = "SeCelebraCo";
        } else {
            seCelebraC = "Deporte";
        }
        final String seCelebra = seCelebraC;


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

                    ArrayList<String> salas = new ArrayList<>();
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

                                    if (FechaSala.getData().get("NombreEvento").toString().equals(obra) && !salas.contains(sala)) {
                                        salas.add(sala);
                                    }


                                }


                                for (String nSala : salas) {
                                    for (Salas edificio : edificiosEnLosQueSeCelebra) {
                                        if (edificio.getNombreSalas().contains(nSala)) {
                                            if (edificiosEnLosQueSeCelebraReal.isEmpty()) {
                                                ArrayList<String> salasAmeter = new ArrayList<>();
                                                salasAmeter.add(nSala);
                                                Salas edificionuevo = new Salas(edificio.getNombreEdif(), salasAmeter);
                                                edificiosEnLosQueSeCelebraReal.add(edificionuevo);
                                                System.out.println("Entre bien");
                                            } else {
                                                //este if tengo que hacer el conains con el contenido del array, no con el array
                                                if (edificiosEnLosQueSeCelebraReal.contains(edificio.getNombreEdif())) {
                                                    edificiosEnLosQueSeCelebraReal.get(edificiosEnLosQueSeCelebraReal.indexOf(edificio.getNombreEdif())).getNombreSalas().add(nSala);
                                                } else {
                                                    System.out.println("No dieveria estar aqui");
                                                    ArrayList<String> salasAmeter = new ArrayList<>();
                                                    salasAmeter.add(nSala);
                                                    Salas edificionuevo = new Salas(edificio.getNombreEdif(), salasAmeter);
                                                    edificiosEnLosQueSeCelebraReal.add(edificionuevo);
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    });
                }
            }
        });


        return edificiosEnLosQueSeCelebraReal;
    }

}