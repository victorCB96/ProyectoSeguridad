package com.example.diego.proyectoseguridad.Modelo;

import android.database.Cursor;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by chris on 09/06/16.
 */
public class Pelicula implements Parcelable {

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
    private String sGeneros;

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

    public Pelicula() {
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


    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
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

    public String getsGeneros() {
        return sGeneros;
    }

    public String getGeneros() {
        generos.moveToFirst();
        String sGeneros = generos.getString(0);

        while (generos.moveToNext()) {
            sGeneros += ", " + generos.getString(0);
        }

        return sGeneros;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.sinopsis);
        dest.writeString(this.urlImagen);
        dest.writeInt(this.idPelicula);
        dest.writeInt(this.clasificacion);
        dest.writeString(getGeneros());
    }

    protected Pelicula(Parcel in) {
        this.nombre = in.readString();
        this.sinopsis = in.readString();
        this.urlImagen = in.readString();
        this.idPelicula = in.readInt();
        this.clasificacion = in.readInt();
        this.sGeneros = in.readString();
    }

    public static final Parcelable.Creator<Pelicula> CREATOR = new Parcelable.Creator<Pelicula>() {
        @Override
        public Pelicula createFromParcel(Parcel source) {
            return new Pelicula(source);
        }

        @Override
        public Pelicula[] newArray(int size) {
            return new Pelicula[size];
        }
    };
}
