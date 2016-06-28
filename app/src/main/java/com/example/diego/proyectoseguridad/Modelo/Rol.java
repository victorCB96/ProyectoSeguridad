package com.example.diego.proyectoseguridad.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by roy on 08/06/16.
 */
public class Rol implements Parcelable {
    private int idRol;
    private String nombre;

    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idRol);
        dest.writeString(this.nombre);
    }

    protected Rol(Parcel in) {
        this.idRol = in.readInt();
        this.nombre = in.readString();
    }

    public static final Parcelable.Creator<Rol> CREATOR = new Parcelable.Creator<Rol>() {
        @Override
        public Rol createFromParcel(Parcel source) {
            return new Rol(source);
        }

        @Override
        public Rol[] newArray(int size) {
            return new Rol[size];
        }
    };
}
