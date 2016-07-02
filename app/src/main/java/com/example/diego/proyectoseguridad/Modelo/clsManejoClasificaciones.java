package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public Cursor getClasificaciones()
    {
        String query;
        query= String.format("select * from %s",TABLA_CLASIFICACIONES);
        return conexion.mConsultar(query, null);
    }

    public List<Clasificacion> getClasificacionesUsuarios(Cursor roles){

        String query = "select C.idClasificacion, tipo from tbClasificaciones C inner join tbRolClasificacion RC on (C.idClasificacion = RC.idClasificacion) where idRol = ?";
        ArrayList<Clasificacion> clasificacions = new ArrayList<>();

        if(roles != null && roles.getCount() > 0) {
            while (roles.moveToNext()) {

                String args[] = {String.valueOf(roles.getInt(0))};
                Cursor clasificacionRol = conexion.mConsultar(query, args);

                while (clasificacionRol.moveToNext()) {
                    Clasificacion clasificacion = new Clasificacion(clasificacionRol.getInt(0), clasificacionRol.getString(1));
                    if (!clasificacions.contains(clasificacion))
                        clasificacions.add(clasificacion);
                }

                clasificacionRol.close();
            }
        }else
            return null;


        return clasificacions;
    }
}
