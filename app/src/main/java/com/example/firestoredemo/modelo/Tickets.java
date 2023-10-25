package com.example.firestoredemo.modelo;

public class Tickets {

    String edificio;
    String evento;
    String fecha;
    double precio = 0;
    String sala;

    public Tickets(String edificio, String evento, String fecha, double precio, String sala){
        this.edificio = edificio;
        this.evento = evento;
        this.fecha = fecha;
        this.precio = precio;
        this.sala = sala;
    }

    public Tickets(){

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
