package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by victor on 09/06/2016.
 */
public class clsConexion extends SQLiteAssetHelper{

    private static String BASE_DATOS="BDProyectoPeliculas.db";
    private SQLiteDatabase bd;


    public clsConexion(Context context) {
        super(context, BASE_DATOS, null, 1);
        bd=getReadableDatabase();
        bd.execSQL("PRAGMA foreign_keys=ON");
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

    public boolean mModificar(ContentValues valores, int id, String nombreTabla)
    {
        try {
            bd.update(nombreTabla,valores,"id"+"=?", new String[]{Integer.toString(id)});
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del metodo consultar

    public Cursor mConsultar(String query, String[] args)
    {
        Cursor cursor = bd.rawQuery(query,args);
        return cursor;
    }

    public Cursor mConsultarVariasTablas(String query,String idUsuario)
    {
        Cursor cursor=bd.rawQuery(query,new String[]{idUsuario});
        return cursor;
    }



}
