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
    private String creadoPor;
    private String fechaCreacion;
    private String modificadoPor;
    private String fechaModificacion;

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public static Creator<Usuario> getCREATOR() {
        return CREATOR;
    }



    public Usuario(int idUsuario, String nombre, String contrasena, String creadoPor, String fechaCreacion, String modificadoPor, String fechaModificacion) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.creadoPor = creadoPor;
        this.fechaCreacion = fechaCreacion;
        this.modificadoPor = modificadoPor;
        this.fechaModificacion = fechaModificacion;
    }

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
        parcel.writeString(this.creadoPor);
        parcel.writeString(this.fechaModificacion);
        parcel.writeString(this.fechaCreacion);
        parcel.writeString(this.modificadoPor);
    }

    protected Usuario(Parcel in){
        this.idUsuario = in.readInt();
        this.nombre = in.readString();
        this.contrasena = in.readString();
        this.creadoPor = in.readString();
        this.fechaModificacion = in.readString();
        this.fechaCreacion = in.readString();
        this.modificadoPor = in.readString();
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
