package com.example.diego.proyectoseguridad.Modelo;

/**
 * Created by victor on 08/06/2016.
 */
public class Genero {
    //Variables
    private int idGenero;
    private String genero;

    //Constructor vacío
    public Genero() {
    }

    //Constructor sin parámetros
    public Genero(int idGenero, String genero) {
        this.idGenero = idGenero;
        this.genero = genero;
    }

    //Métodos set y get
    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
