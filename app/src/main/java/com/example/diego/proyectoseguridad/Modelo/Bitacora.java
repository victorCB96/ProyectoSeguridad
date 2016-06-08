package com.example.diego.proyectoseguridad.Modelo;

import java.sql.Time;

/**
 * Created by roy on 08/06/16.
 */
public class Bitacora {
    private int idBitacora;
    private String accion;
    private String descripcion;
    private Time hora;
    private int idUsuario;

    public Bitacora(int idBitacora, String accion, String descripcion, Time hora, int idUsuario) {
        this.idBitacora = idBitacora;
        this.accion = accion;
        this.descripcion = descripcion;
        this.hora = hora;
        this.idUsuario = idUsuario;
    }

    public Bitacora() {
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
