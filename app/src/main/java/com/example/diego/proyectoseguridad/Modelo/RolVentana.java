package com.example.diego.proyectoseguridad.Modelo;

/**
 * Created by roy on 02/07/16.
 */
public class RolVentana {
    private int idVentana,ver,modificar,eliminar,insertar;

    public RolVentana(int idVentana, int ver, int modificar, int eliminar, int insertar) {
        this.idVentana = idVentana;
        this.ver = ver;
        this.modificar = modificar;
        this.eliminar = eliminar;
        this.insertar = insertar;
    }

    public RolVentana() {
        this.idVentana = 0;
        this.ver = 0;
        this.modificar = 0;
        this.eliminar = 0;
        this.insertar = 0;
    }

    public int getInsertar() {
        return insertar;
    }

    public void setInsertar(int insertar) {
        this.insertar = insertar;
    }

    public int getIdVentana() {
        return idVentana;
    }

    public void setIdVentana(int idVentana) {
        this.idVentana = idVentana;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getModificar() {
        return modificar;
    }

    public void setModificar(int modificar) {
        this.modificar = modificar;
    }

    public int getEliminar() {
        return eliminar;
    }

    public void setEliminar(int eliminar) {
        this.eliminar = eliminar;
    }
}
