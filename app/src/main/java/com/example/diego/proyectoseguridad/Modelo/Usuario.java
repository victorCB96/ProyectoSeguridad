package com.example.diego.proyectoseguridad.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by diego on 8/6/2016.
 */
public class Usuario implements Parcelable {

    private int idUsuario;
    private String nombre;
    private String contrasena;



    public Usuario(String nombre, String contrasena, int idUsuario) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.idUsuario = idUsuario;
    }

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.idUsuario);
        parcel.writeString(this.nombre);
        parcel.writeString(this.contrasena);
    }

    protected Usuario(Parcel in){
        this.idUsuario = in.readInt();
        this.nombre = in.readString();
        this.contrasena = in.readString();
    }

    public  static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>(){

        @Override
        public Usuario createFromParcel(Parcel parcel) {
            return new Usuario(parcel);
        }

        @Override
        public Usuario[] newArray(int i) {
            return new Usuario[i];
        }
    };
}
