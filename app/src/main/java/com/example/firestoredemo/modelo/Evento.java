package com.example.firestoredemo.modelo;

import java.util.List;

public class Evento {


    public String getNombre() {
        return nombre;
    }

    public List<Local> getLocal() {
        return locales;
    }

    private List<Local> locales;

    private String nombre;
    public Evento(String nombre, List<Local> locales){
        this.nombre=nombre;
        this.locales=locales;
    }
}
