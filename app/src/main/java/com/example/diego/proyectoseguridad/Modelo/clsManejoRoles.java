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

        return conexion.mConsultar(query, null);
    }

    public Cursor consultarRoles(String idUsuario){
        String query="select u.idUsuario,r.idRol,v.idVentana from tbRoles r, tbUsuarios u, tbVentanas v, tbRolUsuario ru, tbRolVentana rv " +
                "where u.idUsuario=ru.idUsuario and ru.idRol=r.idRol " +
                "and v.idVentana=rv.idVentana and rv.idRol=r.idRol " +
                "and u.idUsuario=? "+
                "group by v.idVentana, r.idRol,u.idUsuario;";
        Cursor cursor=conexion.mConsultarVariasTablas(query,idUsuario);

        return cursor;
    }

    /*public Cursor consultarRoles(){
        String query="select idRol from tbRolUsuario where nombre=? ";
        Cursor cursor=conexion.mConsultarVariasTablas(query,idUsuario);

        return cursor;
    }
    */

}
