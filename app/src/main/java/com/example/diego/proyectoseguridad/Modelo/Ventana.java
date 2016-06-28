package com.example.diego.proyectoseguridad.Modelo;

/**
 * Created by victor on 08/06/2016.
 */
public class Ventana {
    //Variables
    private int idVentana;
    private String nombre;

    //Constructor vacío
    public Ventana() {
    }

    //Constructor con parámetros
    public Ventana(int idVentana, String nombre) {
        this.idVentana = idVentana;
        this.nombre = nombre;
    }

    //Métodos set y get
    public int getIdVentana() {
        return idVentana;
    }

    public void setIdVentana(int idVentana) {
        this.idVentana = idVentana;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
