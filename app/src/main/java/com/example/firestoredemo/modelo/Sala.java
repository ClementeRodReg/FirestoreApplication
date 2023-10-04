package com.example.firestoredemo.modelo;

public class Sala {
    private String nombre;
    private String fecha;
    private String hora;
    private double precio;

    public Sala(String nombre, String fecha, String hora, double precio){
        this.nombre=nombre;
        this.fecha=fecha;
        this.hora=hora;
        this.precio=precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public double getPrecio() {
        return precio;
    }


}
