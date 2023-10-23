package com.example.firestoredemo.metodos;

import android.widget.ArrayAdapter;

import com.example.firestoredemo.modelo.Obras;
import com.example.firestoredemo.modelo.Salas;
import com.example.firestoredemo.modelo.SeCelebraT;
import com.example.firestoredemo.vista.SalasHorasFechas;
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

        ArrayList<Obras> obrasList = new ArrayList<>();
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

        String seCelebraC = "";

        if (nombreCategoria.equalsIgnoreCase("Teatro")) {
            seCelebraC = "SeCelebraT";
        } else if (nombreCategoria.equals("Cine")) {
            seCelebraC = "SeCelebraC";
        } else if (nombreCategoria.equals("Concierto")) {
            seCelebraC = "SeCelebraCo";
        } else {
            seCelebraC = "SeCelebraD";
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
                        ArrayList<String> nombreSalas = new ArrayList<>();
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
                                    String sala = FechaSala.getId().split("_")[1];
                                    if (FechaSala.getData().get("NombreEvento").toString().equals(obra) && !salas.contains(sala)) {
                                        salas.add(sala);
                                    }
                                }

                                for (String nSala : salas) {
                                    boolean edificioEncontrado = false;

                                    for (Salas edificio : edificiosEnLosQueSeCelebra) {
                                        if (edificio.getNombreSalas().contains(nSala)) {
                                            if (edificiosEnLosQueSeCelebraReal.isEmpty()) {
                                                ArrayList<String> salasAmeter = new ArrayList<>();
                                                salasAmeter.add(nSala);
                                                Salas edificionuevo = new Salas(edificio.getNombreEdif(), salasAmeter);
                                                edificiosEnLosQueSeCelebraReal.add(edificionuevo);
                                            } else {
                                                for (Salas edificioExistente : edificiosEnLosQueSeCelebraReal) {
                                                    if (edificioExistente.getNombreEdif().equals(edificio.getNombreEdif())) {
                                                        edificioEncontrado = true;
                                                        edificioExistente.getNombreSalas().addAll(edificio.getNombreSalas());
                                                    }
                                                }

                                                if (!edificioEncontrado) {
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


    public ArrayList<String> obtenerfechaYhora(String obra, String nombreSala, String categoria) {
        ArrayList<String> fechaYhora = new ArrayList<>();

        myBBDD = FirebaseFirestore.getInstance();

        String seCelebra = "";

        if (categoria.equalsIgnoreCase("Teatro")) {
            seCelebra = "SeCelebraT";
        } else if (categoria.equals("Cine")) {
            seCelebra = "SeCelebraC";
        } else if (categoria.equals("Concierto")) {
            seCelebra = "SeCelebraCo";
        } else {
            seCelebra = "SeCelebraD";
        }


        System.out.println(seCelebra);

        Task coleccion = myBBDD.collection(seCelebra).get();
        System.out.println(seCelebra);

        coleccion.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentLocal : task.getResult()) {

                        String sala = nombreSala.substring(0, nombreSala.length()-1);
                        if (documentLocal.getId().contains(sala) && documentLocal.get("NombreEvento").equals(obra))
                            fechaYhora.add(documentLocal.getId().split("_")[0]+"_"+documentLocal.get("Hora"));
                    }
                }else{
                    System.out.println("No funciona");
                }
            }
        });

        return fechaYhora;
    }

}