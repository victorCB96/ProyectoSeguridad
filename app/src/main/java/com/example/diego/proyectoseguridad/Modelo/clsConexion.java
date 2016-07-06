package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

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
                if(!valoresRolVentana.isEmpty() && !valoresRolClasificacion.isEmpty()){//si se agregan los roles a clasificcacion y ventana
                    for (ContentValues rolVentana: valoresRolVentana){
                        bd.insertOrThrow("tbRolVentana",null, rolVentana);
                    }
                    for (ContentValues rolClasificacion: valoresRolClasificacion){
                        bd.insertOrThrow("tbRolClasificacion",null, rolClasificacion);
                    }
                    bd.setTransactionSuccessful();
                    insert = true;
                }//fin del if que verifica que ambos array esten llenos

                if(!valoresRolVentana.isEmpty()&& valoresRolClasificacion.isEmpty()){//si se agregan los roles a venta y no a clasificacion
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
            bd.insertOrThrow(nombreTabla,null,valores);
            bd.insert(nombreTabla,null,valores);
            //bd.close();
            return true;
        }catch (SQLiteAssetException e)
        {
            bd.close();

            return false;
        }
    }//fin del metodo insertar

    public void mTransaccionInsertar(ContentValues valores,String nombreTabla) throws SQLiteException
    {
        bd.insertOrThrow(nombreTabla,null,valores);
    }

    public void mTrassacionModificar(ContentValues valores, String nombreTabla, String whereClause, String[] args) throws SQLiteException
    {
        bd.update(nombreTabla,valores,whereClause,args);
    }

    public boolean mEliminar(int id,String nombreTabla)
    {
        try{
            bd.delete(nombreTabla,"id=?",new String[]{Integer.toString(id)});
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del mmetodo eliminar

    public boolean mEliminar(String nombreTabla, String whereClausule, String [] args)
    {
        try{
            bd.delete(nombreTabla,whereClausule,args);

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del metodo eliminar

    public boolean mModificar(ContentValues valores, int id, String nombreTabla)
    {
        try {
            bd.update(nombreTabla,valores,"id=?", new String[]{Integer.toString(id)});
            bd.close();

            return true;
        }catch (SQLiteAssetException e)
        {
            return false;
        }
    }//fin del metodo modificar

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

    public SQLiteDatabase getBd(){
        return bd;
    }

    public void mTransaccionEliminar(String nombreTabla, String whereClausule, String [] args) throws SQLiteException
    {
        bd.delete(nombreTabla,whereClausule,args);
    }

    public boolean mModificarRol(ContentValues rol, int id)
    {
        boolean modificar=false;
        bd.beginTransaction();
        try {
            valoresRolClasificacion.clear();
            valoresRolVentana.clear();
            this.llenarDatos(id);
            bd.update("tbRoles",rol,"idRol"+"=?", new String[]{Integer.toString(id)});
            Cursor consulta=this.mConsultarVariasTablas("select * from tbRolClasificacion where idRol=?",String.valueOf(id));
            if(consulta.getCount()==0){
                if(Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.isEmpty()){ //si no elimino algunas de las opciones que tiene en la base de datos
                    for (ContentValues valores:valoresRolClasificacion){//recorre los nuevos y los viejos valores del array
                        bd.insertOrThrow("tbRolClasificacion",null, valores);
                    }
                }
            }else {
                for(consulta.moveToFirst(); !consulta.isAfterLast(); consulta.moveToNext()){
                    if(Variables.PERMISOS_CLASIFICACIONES_ELIMINACION.isEmpty()){ //si no elimino algunas de las opciones que tiene en la base de datos
                        for (ContentValues valores:valoresRolClasificacion){//recorre los nuevos y los viejos valores del array
                            if(valores.getAsInteger("idClasificacion")!=consulta.getInt(1)){
                                Log.i("dato curioso: ", "entro");
                                Cursor consultaExistencia=bd.rawQuery("select * from tbRolClasificacion where idRol=? and idClasificacion=?",new String[]{Integer.toString(id),Integer.toString(valores.getAsInteger("idClasificacion"))});
                                if(consultaExistencia.getCount()==0){
                                    bd.insertOrThrow("tbRolClasificacion",null, valores);
                                }

                            }
                        }
                    }else {
                        for (int i:Variables.PERMISOS_CLASIFICACIONES_ELIMINACION){
                            if(i==consulta.getInt(1)){
                                bd.delete("tbRolClasificacion","idClasificacion="+"?",new String[]{Integer.toString(i)});
                            }
                        }
                    }
                }//fin del for que recorre las clasificaciones asignadaas actualmente al rol que entra por parametro
            }

            Cursor consultaVentana=this.mConsultarVariasTablas("select * from tbRolVentana where idRol=?",String.valueOf(id));
            if(consultaVentana.getCount()==0){
                for (ContentValues rolVentana: valoresRolVentana){
                    bd.insertOrThrow("tbRolVentana",null, rolVentana);
                }
            }else {
                for(consultaVentana.moveToFirst(); !consultaVentana.isAfterLast(); consultaVentana.moveToNext()){
                    if(Variables.PERMISOS_ELIMINAR.isEmpty()){
                        if(!valoresRolVentana.isEmpty()){
                            for (ContentValues rolVentana: valoresRolVentana){
                                if(rolVentana.getAsInteger("idVentana") == consultaVentana.getInt(1)){
                                    bd.update("tbRolVentana",rolVentana,"idRol"+"=? and idVentana=?", new String[]{Integer.toString(id),Integer.toString(rolVentana.getAsInteger("idVentana"))});
                                }else {
                                    Log.i("dato: ","hizo insert");
                                    Cursor consultaExistencia=bd.rawQuery("select * from tbRolVentana where idRol=? and idVentana=?",new String[]{Integer.toString(id),Integer.toString(rolVentana.getAsInteger("idVentana"))});
                                    if(consultaExistencia.getCount()==0){
                                        bd.insertOrThrow("tbRolVentana",null, rolVentana);
                                    }else {
                                        bd.update("tbRolVentana",rolVentana,"idRol"+"=? and idVentana=?", new String[]{Integer.toString(id),Integer.toString(rolVentana.getAsInteger("idVentana"))});
                                    }

                                }
                            }
                        }
                    }else{
                        for (RolVentana rolVentana:Variables.PERMISOS_ELIMINAR){
                            if(rolVentana.getIdVentana()==consultaVentana.getInt(1)){
                                bd.delete("tbRolVentana","idVentana="+"?",new String[]{Integer.toString(rolVentana.getIdVentana())});
                            }
                        }
                    }
                }
            }

            bd.setTransactionSuccessful();
            modificar=true;
        }catch(SQLiteException e) {
            modificar = false;
        }
        finally {
            bd.endTransaction();
        }

        return modificar;
    }

    public boolean mEliminarRol(int id)
    {
        Boolean eliminar = false;
        bd.beginTransaction();
        try {
            bd.delete("tbRolVentana","idRol"+"=?",new String[]{Integer.toString(id)});
            bd.delete("tbRolClasificacion","idRol"+"=?",new String[]{Integer.toString(id)});
            bd.delete("tbRoles","idRol"+"=?",new String[]{Integer.toString(id)});
            bd.setTransactionSuccessful();
            eliminar=true;
        }catch(SQLiteException e) {
            eliminar = false;
        }
        finally {
            bd.endTransaction();
        }

        return eliminar;
    }//fin del metodo eliminar

}
