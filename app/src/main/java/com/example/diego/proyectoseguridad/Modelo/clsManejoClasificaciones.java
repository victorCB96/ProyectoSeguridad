package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by victor on 27/06/2016.
 */
public class clsManejoClasificaciones {
    private SQLiteDatabase bd;
    private clsConexion conexion;
    private static final String TABLA_CLASIFICACIONES="tbClasificaciones";

    public clsManejoClasificaciones(Context context) {
        this.conexion = new clsConexion(context);
    }

    public Cursor getTipoClasificacion()
    {
        String query;
        query= "select idClasificacion, tipo from tbClasificaciones";
        return conexion.mConsultar(query, null);
    }
}
