package com.example.firestoredemo.modelo;

import java.util.List;

public class Local {
    public String getNombre() {
        return nombre;
    }
    private String nombre;
    private List<Sala> salas ;
    public List<Sala> getSalas() { return salas; }
    public Local(String nombre, List<Sala> salas){
        this.nombre=nombre;
        this.salas=salas;
    }

}
