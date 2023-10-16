package com.example.firestoredemo.modelo;

import java.util.List;

public class Salas {

    String nombreEdif;
    List<String> nombreSalas;

    public Salas(String nombreEdif, List<String> nombreSalas){
        this.nombreEdif=nombreEdif;
        this.nombreSalas=nombreSalas;
    }
    public Salas(){

    }
    public List<String> getNombreSalas() {
        return nombreSalas;
    }

    public String getNombreEdif() {
        return nombreEdif;
    }
}
