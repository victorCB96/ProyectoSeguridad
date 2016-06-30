package com.example.diego.proyectoseguridad.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chris on 29/06/16.
 */
public class Clasificacion implements Parcelable {

    private int idClasificacion;
    private String tipo;

    public Clasificacion(int idClasificacion, String tipo) {
        this.idClasificacion = idClasificacion;
        this.tipo = tipo;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idClasificacion);
        dest.writeString(this.tipo);
    }

    protected Clasificacion(Parcel in) {
        this.idClasificacion = in.readInt();
        this.tipo = in.readString();
    }

    public static final Parcelable.Creator<Clasificacion> CREATOR = new Parcelable.Creator<Clasificacion>() {
        @Override
        public Clasificacion createFromParcel(Parcel source) {
            return new Clasificacion(source);
        }

        @Override
        public Clasificacion[] newArray(int size) {
            return new Clasificacion[size];
        }
    };
}
