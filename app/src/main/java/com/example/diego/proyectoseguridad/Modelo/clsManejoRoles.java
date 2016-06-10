package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by victor on 09/06/2016.
 */
public class clsManejoRoles {
    private clsConexion conexion;

    public static final String TABLA_ROLES="tbRoles";

    public clsManejoRoles(Context context)
    {
        this.conexion=new clsConexion(context);
    }

    public boolean mAgregarRol(Rol rol)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombre",rol.getNombre());

        return conexion.mInsertar(valores,TABLA_ROLES);
    }//fin del metodo mAgregarRol

    public boolean mEliminarRol(Rol rol)
    {
        return conexion.mEliminar(rol.getIdRol(),TABLA_ROLES);
    }//fin del metodo mEliminarRol

    public boolean mModificarRol(Rol rol)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombre",rol.getNombre());

        return conexion.mModificar(valores,rol.getIdRol(),TABLA_ROLES);
    }//fin del metodo mModificarRol

    public Cursor getRol()
    {
        String query;
        query=String.format("SELECT * FROM %s",TABLA_ROLES);

        return conexion.mConsultar(query);
    }

}
