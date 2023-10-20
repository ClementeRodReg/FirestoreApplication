package com.example.firestoredemo.modelo;

import java.util.ArrayList;


public class Salas {

    public void setNombreEdif(String nombreEdif) {
        this.nombreEdif = nombreEdif;
    }

    public void setNombreSalas(ArrayList<String> nombreSalas) {
        this.nombreSalas = nombreSalas;
    }

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
