package com.example.firestoredemo.modelo;

import java.util.List;

public class Evento {


    public String getNombre() {
        return nombre;
    }

    public List<Sala> getSala() {
        return sala;
    }

    private List<Sala> sala;
    private String nombre;
    public Evento(String nombre, List<Sala> sala){
        this.nombre=nombre;
        this.sala=sala;
    }
}
