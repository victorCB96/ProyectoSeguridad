package com.example.diego.proyectoseguridad.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by victor on 08/06/2016.
 */
public class Ventana implements Parcelable {
    //Variables
    private int idVentana;
    private String nombre;
    private ArrayList<Permiso> permisos;

    //Constructor vacío
    public Ventana() {
        //Permisos por defecto los que la mayoria de ventanas tienen

    }

    //Constructor con parámetros
    public Ventana(int idVentana, String nombre) {
        this.idVentana = idVentana;
        this.nombre = nombre;
        permisos = new ArrayList<>();
        permisos.add(new Permiso(false, "Insertar"));
        permisos.add(new Permiso(false, "Ver"));
        permisos.add(new Permiso(false, "Modificar"));
        permisos.add(new Permiso(false, "Eliminar"));
    }

    /**
     * no todas las ventanas pueden tener los mismos tipos de permiso
     * @param Ventana.Permiso newPermiso
     */
    public void agregarPermiso(Permiso newPermiso){
        if(newPermiso != null)
            permisos.add(newPermiso);
    }

    /**
     * retorna permiso
     * @param posicion
     * @return Permiso
     */
    public Permiso getPermiso(int posicion){
        return permisos.get(posicion);
    }

    public Permiso getPermiso(String nombre){
        for (int i = 0; i<permisos.size(); i++){
            if(permisos.get(i).getNombre().equalsIgnoreCase(nombre))
                return permisos.get(i);
        }

        return null;
    }

    /**
     *
     * @return int numero de permisos asignables
     */
    public int cantPermisosAsignables(){
        return permisos.size();
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

    public boolean isTienePermisos(){

        for (Permiso permiso : permisos) {
            if(permiso.isActivado()) {
                return true;
            }
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idVentana);
        dest.writeString(this.nombre);
        dest.writeList(this.permisos);
    }

    protected Ventana(Parcel in) {
        this.idVentana = in.readInt();
        this.nombre = in.readString();
        this.permisos = new ArrayList<>();
        in.readList(this.permisos, Permiso.class.getClassLoader());
    }

    public static final Parcelable.Creator<Ventana> CREATOR = new Parcelable.Creator<Ventana>() {
        @Override
        public Ventana createFromParcel(Parcel source) {
            return new Ventana(source);
        }

        @Override
        public Ventana[] newArray(int size) {
            return new Ventana[size];
        }
    };




    public static class Permiso implements Parcelable {
         private boolean activado;
         private String nombre;

        public Permiso(boolean activado, String nombre) {
            this.activado = activado;
            this.nombre = nombre;
        }

        public boolean isActivado() {
            return activado;
        }

        public void setActivado(boolean activado) {
            this.activado = activado;
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
            dest.writeByte(this.activado ? (byte) 1 : (byte) 0);
            dest.writeString(this.nombre);
        }

        protected Permiso(Parcel in) {
            this.activado = in.readByte() != 0;
            this.nombre = in.readString();
        }

        public static final Creator<Permiso> CREATOR = new Creator<Permiso>() {
            @Override
            public Permiso createFromParcel(Parcel source) {
                return new Permiso(source);
            }

            @Override
            public Permiso[] newArray(int size) {
                return new Permiso[size];
            }
        };
    }



}
