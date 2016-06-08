package com.example.diego.proyectoseguridad.Modelo;

/**
 * Created by victor on 08/06/2016.
 */
public class Pelicula
{
    //Variables
    private int idPelicula;
    private String nombre;
    private String sinopsis;
    private String imagen;

    //Constructor vací
    public Pelicula() {
    }

    //Constructor con parámetros
    public Pelicula(int idPelicula, String nombre, String sinopsis, String imagen) {
        this.idPelicula = idPelicula;
        this.nombre = nombre;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }

    //Métodos set y get

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
