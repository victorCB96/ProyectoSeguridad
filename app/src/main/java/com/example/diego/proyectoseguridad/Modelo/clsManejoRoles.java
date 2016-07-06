package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by victor on 09/06/2016.
 */
public class clsManejoRoles {
    private clsConexion conexion;

    public static final String TABLA_ROLES="tbRoles";
    public static final String TABLA_ROLESUSUARIOS="tbRolUsuario";

    public clsManejoRoles(Context context)
    {
        this.conexion=new clsConexion(context);
    }


    /*public boolean insertarRol(Rol rol, Rol){
        return conexion.mInsertarTransaccion();
    }
*/
    public boolean mAgregarRol(Rol rol)
    {
        ContentValues valoresRol=new ContentValues();
        valoresRol.put("nombre",rol.getNombre());

        return conexion.mInsertarTransaccion(valoresRol);
    }//fin del metodo mAgregarRol

    public boolean mEliminarRol(Rol rol)
    {
        Log.i("idRol: ",""+rol.getIdRol());
        return conexion.mEliminarRol(rol.getIdRol());
    }//fin del metodo mEliminarRol

    public boolean mModificarRol(Rol rol)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombre",rol.getNombre());

        return conexion.mModificarRol(valores,rol.getIdRol());
    }//fin del metodo mModificarRol

    public Cursor getRol()
    {
        String query;
        query=String.format("SELECT * FROM %s",TABLA_ROLES);

        return conexion.mConsultar(query, null);
    }

    public Cursor getRoles(String idUsuario)
    {
        String query;
        query=String.format("SELECT * FROM tbRolUsuario where idUsuario=?");

        return conexion.mConsultarVariasTablas(query,idUsuario);
    }

    public Cursor consultarRoles(String idRol){
        String query="SELECT v.nombre,rv.ver,rv.modificar,rv.eliminar,rv.insertar\n" +
                "FROM tbRolVentana rv, tbVentanas v\n" +
                "WHERE rv.idRol= ? \n" +
                "AND rv.idVentana=v.idVentana;";
        Cursor cursor=conexion.mConsultarVariasTablas(query,idRol);

        return cursor;
    }



    public Cursor getVentanasRoles(String idRol)
    {
        String query;
        query= String.format("select idVentana,ver,insertar,modificar,eliminar from tbRolVentana where idRol=? ");
        return conexion.mConsultarVariasTablas(query, idRol);
    }

    public Cursor getClasificacionesRoles(String idRol)
    {
        String query;
        query= String.format("select idRol,idClasificacion from tbRolClasificacion where idRol=? ");
        return conexion.mConsultarVariasTablas(query, idRol);
    }



    public Cursor consultarRolPorNombre(String nombre){
        String query="select idRol from tbRoles where nombre=? ";
        Cursor cursor=conexion.mConsultarVariasTablas(query,nombre);

        return cursor;
    }


}
