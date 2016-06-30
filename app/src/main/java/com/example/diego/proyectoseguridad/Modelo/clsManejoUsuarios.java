package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
        query=String.format("SELECT * FROM %s", TABLA_USUARIOS);

        return conexion.mConsultar(query,null);
    }

    public Usuario consultarUsuario(String clave, String usuario){
        String query = "Select * from tbUsuarios where nombre= ? and contrasena= ?";
        String [] args = {usuario, clave};
        Cursor cursor = conexion.mConsultar(query, args);

        if( cursor.getCount() > 0){
            cursor.moveToFirst();
            Usuario usuarioEncontrado = new Usuario(cursor.getString(1), cursor.getString(2), cursor.getInt(0));
            cursor.close();
            return usuarioEncontrado;
        }else{
            cursor.close();
            return null;
        }
    }



    public Cursor getUsuarioEspecifico(String usuario){
        //String query;
        //query=String.format("Select * from tbUsuarios where nombre= ?",new String[]{usuario});
        //
        String query = "Select * from tbUsuarios where nombre= ?";
        String [] args = {usuario};
        return conexion.mConsultar(query, args);
    }


    public Cursor getPermisosDirectosUsuario(String idUsuario){
        String query ="select v.nombre, uv.ver,uv.modificar,uv.eliminar,uv.insertar\n" +
                "from tbUsuarioVentana uv, tbVentanas v\n" +
                "where uv.idUsuario=?\n" +
                "and uv.idVentana=v.idVentana;";
        Cursor cursor=conexion.mConsultarVariasTablas(query,idUsuario);

        return cursor;
    }



}
