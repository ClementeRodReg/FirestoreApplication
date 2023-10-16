package com.example.firestoredemo.modelo;

import java.util.ArrayList;


public class Salas {

    String nombreEdif;
    ArrayList<String> nombreSalas;

    public Salas(String nombreEdif, ArrayList<String> nombreSalas){
        this.nombreEdif=nombreEdif;
        this.nombreSalas=nombreSalas;
    }
    public Salas(){

    }
    public ArrayList<String> getNombreSalas() {
        return nombreSalas;
    }

    public String getNombreEdif() {
        return nombreEdif;
    }
}
