package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by victor on 09/06/2016.
 */
public class clsConexion extends SQLiteAssetHelper{

    private static String BASE_DATOS="BDProyectoPeliculas.db";
    private SQLiteDatabase bd;
    ArrayList<ContentValues> valoresRolVentana;
    ArrayList<ContentValues> valoresRolClasificacion;


    public clsConexion(Context context) {
        super(context, BASE_DATOS, null, 1);
        bd=getReadableDatabase();
        bd.execSQL("PRAGMA foreign_keys=ON");
        valoresRolVentana=new ArrayList<>();
        valoresRolClasificacion=new ArrayList<>();
    }

    public boolean mInsertarTransaccion(ContentValues rol)
    {
        Boolean insert = false;
        bd.beginTransaction();
        try {
            bd.insertOrThrow("tbRoles",null,rol);
            Cursor consulta=bd.rawQuery("Select idRol from tbRoles where nombre=?",new String[]{rol.getAsString("nombre")});
            if(consulta.moveToFirst()){
                this.llenarDatos(consulta.getInt(0));
                if(!valoresRolVentana.isEmpty() && !valoresRolClasificacion.isEmpty()){
                    for (ContentValues rolVentana: valoresRolVentana){
                        bd.insertOrThrow("tbRolVentana",null, rolVentana);
                    }
                    for (ContentValues rolClasificacion: valoresRolClasificacion){
                        bd.insertOrThrow("tbRolClasificacion",null, rolClasificacion);
                    }
                    bd.setTransactionSuccessful();
                    insert = true;
                }//fin del if que verifica que ambos array esten llenos

                if(!valoresRolVentana.isEmpty()&& valoresRolClasificacion.isEmpty()){
                    for (ContentValues rolVentana: valoresRolVentana){
                        bd.insertOrThrow("tbRolVentana",null, rolVentana);
                    }
                    bd.setTransactionSuccessful();
                    insert = true;
                }

                if(valoresRolVentana.isEmpty()&& !valoresRolClasificacion.isEmpty()){
                    for (ContentValues rolClasificacion: valoresRolClasificacion){
                        bd.insertOrThrow("tbRolClasificacion",null, rolClasificacion);
                    }
                    bd.setTransactionSuccessful();
                    insert = true;
                }
            }

        }
        catch(SQLiteException e) {
            insert = false;
        }
        finally {
            bd.endTransaction();
        }

        return insert;

    }

    private void llenarDatos(int idRol){

        if(!Variables.PERMISOS.isEmpty()){
            for(int i=0;i<Variables.PERMISOS.size();i++){
                ContentValues valores= new ContentValues();
                valores.put("idRol",idRol);
                valores.put("idVentana",Variables.PERMISOS.get(i).getIdVentana());
                valores.put("ver",Variables.PERMISOS.get(i).getVer());
                valores.put("insertar",Variables.PERMISOS.get(i).getInsertar());
                valores.put("modificar",Variables.PERMISOS.get(i).getModificar());
                valores.put("eliminar",Variables.PERMISOS.get(i).getEliminar());
                valoresRolVentana.add(valores);
            }
        }

        if(!Variables.PERMISOS_CLASIFICACIONES.isEmpty()){
            for(int i=0;i<Variables.PERMISOS_CLASIFICACIONES.size();i++){
                ContentValues valores= new ContentValues();
                valores.put("idClasificacion",Variables.PERMISOS_CLASIFICACIONES.get(i));
                valores.put("idRol",idRol);
                valoresRolClasificacion.add(valores);
            }
        }
    }

    public boolean mInsertar(ContentValues valores,String nombreTabla)
    {
        try{
            bd.insert(nombreTabla,null,valores);
            //bd.close();
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
