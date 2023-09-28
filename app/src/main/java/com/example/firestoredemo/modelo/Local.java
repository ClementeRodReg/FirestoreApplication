package com.example.firestoredemo.modelo;

import java.util.List;

public class Local {
    public String getNombre() {
        return nombre;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    private String nombre;
    private List<Evento> eventos;
    public Local(String nombre, List<Evento> eventos){
        this.nombre=nombre;
        this.eventos=eventos;
    }

}
