package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by victor on 09/06/2016.
 */
public class clsConexion extends SQLiteAssetHelper{

    private static String baseDatos="BDProyectoPeliculas.db";
    private SQLiteDatabase bd;


    public clsConexion(Context context) {
        super(context, baseDatos, null, 1);
        bd=getReadableDatabase();
    }

    public boolean mInsertar(ContentValues valores,String nombreTabla)
    {
        try{
            bd.insert(nombreTabla,null,valores);
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            bd.close();

            return false;
        }
    }//fin del metodo insertar

    public boolean mEliminar(int id,String nombreTabla)
    {
        try{
            bd.delete(nombreTabla,"id"+"=?",new String[]{Integer.toString(id)});
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del mmetodo eliminar

    public boolean mModificar(ContentValues valores,int id,String nombreTabla)
    {
        try {
            bd.update(nombreTabla,valores,"id"+"=?",new String[]{Integer.toString(id)});
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del metodo consultar

    public Cursor mConsultar(String query)
    {
        Cursor cursor=bd.rawQuery(query,null);
        return cursor;
    }

    public Cursor consultarUsuario(String usuario,String clave){

        Cursor cursor=bd.rawQuery("Select * from tbUsuarios where nombre= ? and contrasena= ?",new String[]{usuario,clave});

        return cursor;
    }

}