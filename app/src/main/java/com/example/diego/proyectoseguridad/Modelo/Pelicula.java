package com.example.diego.proyectoseguridad.Modelo;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by chris on 09/06/16.
 */
public class Pelicula {

    public static final int CLASIFICACION_ADULTOS = 1;
    public static final int CLASIFICACION_MAYOR_A_12 = 2;
    public static final int CLASIFICACION_TODO_PUBLICO = 3;
    public static final int CLASIFICACION_SUPERVISION_ADULTO = 4;

    private String nombre;
    private String sinopsis;
    private String urlImagen;
    private int idPelicula;
    private int clasificacion;
    private Cursor generos;

    public Pelicula(String nombre, String sinopsis, String urlImagen, int idPelicula, int clasificacion, Cursor generos) {
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.urlImagen = urlImagen;
        this.idPelicula = idPelicula;
        this.clasificacion = clasificacion;
        this.generos = generos;
    }

    public Pelicula(String sinopsis, Cursor generos, String urlImagen, String nombre, int clasificacion) {
        this.sinopsis = sinopsis;
        this.generos = generos;
        this.urlImagen = urlImagen;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
    }

    public Pelicula(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public void setGeneros(Cursor generos) {
        this.generos = generos;
    }

    public String getGeneros()
    {
        generos.moveToFirst();
        String sGeneros = generos.getString(0);

        while (generos.moveToNext()){
            sGeneros += ", " +generos.getString(0);
        }

        return sGeneros;
    }
}
