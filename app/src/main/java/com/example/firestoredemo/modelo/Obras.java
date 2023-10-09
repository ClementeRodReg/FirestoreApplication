package com.example.firestoredemo.modelo;

public class Obras {

    String nombre;
    double precio;

    public Obras(String nombre, double precio){
        this.nombre=nombre;
        this.precio=precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}
