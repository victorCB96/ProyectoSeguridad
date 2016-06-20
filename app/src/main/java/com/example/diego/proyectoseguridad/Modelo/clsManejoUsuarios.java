package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by victor on 09/06/2016.
 */
public class clsManejoUsuarios {
    private clsConexion conexion;

    public static final String TABLA_USUARIOS="tbUsuarios";
    public static final String TABLA_ROLUSUARIO="tbRolUsuario";

    public clsManejoUsuarios(Context context)
    {
        this.conexion=new clsConexion(context);
    }

    public boolean mAgregarUsuario(Usuario usuario)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombre",usuario.getNombre());
        valores.put("contrasena",usuario.getContrasena());

        return conexion.mInsertar(valores,TABLA_USUARIOS);
    }//fin del metodo mAgregarUsuario

    public boolean mEliminarUsuario(Usuario usuario)
    {
        return conexion.mEliminar(usuario.getIdUsuario(),TABLA_USUARIOS);
    }//fin del metodo mEliminarUsuario

    public boolean mModificarUsuario(Usuario usuario)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombre",usuario.getNombre());
        valores.put("contrasena",usuario.getContrasena());

        return conexion.mModificar(valores,usuario.getIdUsuario(),TABLA_USUARIOS);
    }//fin del metodo mModificarUsuario

    public Cursor getUsuario()
    {
        String query;
        query=String.format("SELECT * FROM %s",TABLA_USUARIOS);

        return conexion.mConsultar(query);
    }



}
