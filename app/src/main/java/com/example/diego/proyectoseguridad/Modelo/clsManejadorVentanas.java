package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by victor on 16/06/2016.
 */
public class clsManejadorVentanas {
    private SQLiteDatabase bd;
    private clsConexion conexion;
    private static final String TABLA_VENTANAS="tbVentanas";
    private static final String TABLA_USUARIO_VENTANA="tbUsuarioVentana";
    private static final String TABLA_ROL_VENTANA="tbRolVentana";

    public clsManejadorVentanas(Context context) {
        this.conexion=new clsConexion(context);
    }

    public Cursor getNombreVentanas()
    {
        String query;
        query= "select idVentana, nombre from tbVentanas";
        return conexion.mConsultar(query, null);
    }

}
