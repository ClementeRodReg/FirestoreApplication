package com.example.firestoredemo.modelo;

public class SeCelebraT {

    String fecha;
    String hora;
    String sala;
    String obra;

    public SeCelebraT(String fecha, String hora, String sala, String obra){
        this.fecha=fecha;
        this.hora=hora;
        this.sala=sala;
        this.obra=obra;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getObra() {
        return obra;
    }

    public String getSala() {
        return sala;
    }
}
