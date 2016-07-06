package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    public Cursor getVentanas()
    {
        String query;
        query= String.format("select * from %s",TABLA_VENTANAS);
        return conexion.mConsultar(query, null);
    }

    public ArrayList<Ventana> getListVentanas(){
        ArrayList<Ventana> listVentanas = new ArrayList<>();
        Cursor cursorVentanas = getVentanas();

        while(cursorVentanas.moveToNext()){
            listVentanas.add(new Ventana(cursorVentanas.getInt(0), cursorVentanas.getString(1)));
        }

        cursorVentanas.close();
        return listVentanas;
    }

    public Cursor ventanasUsuario(int idUsuario){
        String query = "select * from tbUsuarioVentana where idUsuario=?";
        String [] args = {String.valueOf(idUsuario)};
        return conexion.mConsultar(query,args);
    }

    public ArrayList<Ventana> getListVentanasUser(int idUsuario)
    {
        ArrayList<Ventana> listVentanas = new ArrayList<>();
        Cursor cursorVentanas = getVentanas();
        Cursor cursorUsuarioVentanas = ventanasUsuario(idUsuario);

        while(cursorVentanas.moveToNext()){
            Ventana ventana = new Ventana(cursorVentanas.getInt(0), cursorVentanas.getString(1));

            if(cursorUsuarioVentanas.getCount()>0 && cursorUsuarioVentanas.moveToNext())
                if(cursorUsuarioVentanas.getInt(1) == ventana.getIdVentana() ) {
                    ventana.getPermiso("ver").setActivado(cursorUsuarioVentanas.getInt(2) == 1 );
                    ventana.getPermiso("insertar").setActivado(cursorUsuarioVentanas.getInt(3) == 1 );
                    ventana.getPermiso("modificar").setActivado(cursorUsuarioVentanas.getInt(4) == 1 );
                    ventana.getPermiso("eliminar").setActivado(cursorUsuarioVentanas.getInt(5) == 1 );


                }

            listVentanas.add(ventana);
        }


        cursorVentanas.close();
        return listVentanas;
    }

}
