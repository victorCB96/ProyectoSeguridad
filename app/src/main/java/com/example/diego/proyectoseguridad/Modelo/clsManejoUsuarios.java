package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by victor on 09/06/2016.
 */
public class clsManejoUsuarios {
    private clsConexion conexion;

    public static final String TABLA_USUARIOS="tbUsuarios";
    public static final String TABLA_ROLUSUARIO="tbRolUsuario";
    public static final String TABLA_VENTANAUSUARIO="tbUsuarioVentana";

    public clsManejoUsuarios(Context context)
    {
        this.conexion=new clsConexion(context);
    }

    public boolean mAgregarUsuario(Usuario nuevoUsuario, ArrayList<Ventana> ventanas, ArrayList<Rol> roles, Usuario currentUser)
    {
        ContentValues tbUsuario=new ContentValues();
        ContentValues tbUsuarioVentana = new ContentValues();
        ContentValues tbRolUsuario= new ContentValues();
        boolean succes = false;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format =  new SimpleDateFormat("MM-d-yyyy", Locale.getDefault());
        String fecha = format.format(calendar.getTime());

        SQLiteDatabase db = conexion.getBd();
        db.beginTransaction();
        try {

            tbUsuario.put("nombre",nuevoUsuario.getNombre());
            tbUsuario.put("contrasena",nuevoUsuario.getContrasena());
            tbUsuario.put("creadoPor",currentUser.getNombre());
            tbUsuario.put("fechaCreacion", fecha);
            tbUsuario.put("modificadoPor",currentUser.getNombre());
            tbUsuario.put("fechaModificacion",fecha);
            conexion.mTransaccionInsertar(tbUsuario,TABLA_USUARIOS);

            Cursor cursor = db.rawQuery("select idUsuario from tbUsuarios where nombre=?",
                    new String[]{nuevoUsuario.getNombre()});
            cursor.moveToFirst();

            int id = cursor.getInt(0);

            if(!roles.isEmpty())
                for (Rol rol:roles) {
                    tbRolUsuario.put("idRol",rol.getIdRol());
                    tbRolUsuario.put("idUsuario",id);
                    conexion.mTransaccionInsertar(tbRolUsuario,TABLA_ROLUSUARIO);
                }

            for (Ventana ventana:ventanas) {
                if(ventana.isTienePermisos()) {
                    tbUsuarioVentana.put("idUsuario", id);
                    tbUsuarioVentana.put("idVentana",ventana.getIdVentana());
                    tbUsuarioVentana.put("ver",ventana.getPermiso("ver").isActivado() ? 1:0);
                    tbUsuarioVentana.put("insertar",ventana.getPermiso("insertar").isActivado() ? 1:0);
                    tbUsuarioVentana.put("modificar",ventana.getPermiso("modificar").isActivado() ? 1:0);
                    tbUsuarioVentana.put("eliminar",ventana.getPermiso("eliminar").isActivado() ? 1:0);
                    conexion.mTransaccionInsertar(tbUsuarioVentana,TABLA_VENTANAUSUARIO);
                }

            }

            db.setTransactionSuccessful();
            succes = true;

        }catch (SQLiteException e){
            succes= false;
        }
        finally {
            db.endTransaction();
        }

        return succes;
    }//fin del metodo mAgregarUsuario



    public boolean mEliminarUsuario(Usuario usuario)
    {
        SQLiteDatabase bd = conexion.getBd();
        String[]args={String.valueOf(usuario.getIdUsuario())};
        bd.beginTransaction();
        boolean isSucces;
        try {

            conexion.mTransaccionEliminar(TABLA_ROLUSUARIO,"idUsuario=?",args);
            conexion.mTransaccionEliminar(TABLA_VENTANAUSUARIO,"idUsuario=?",args);
            conexion.mTransaccionEliminar(TABLA_USUARIOS,"idUsuario=?",args);
            isSucces = true;
            bd.setTransactionSuccessful();
        }catch (SQLiteException e){
            isSucces = false;
        }
        finally {
            bd.endTransaction();
        }

        return isSucces;
    }//fin del metodo mEliminarUsuario

    public boolean mModificarUsuario(Usuario usuario,ArrayList<Ventana> ventanas, ArrayList<Rol> roles)
    {
        ContentValues tbUsuario=new ContentValues();
        ContentValues tbUsuarioVentana = new ContentValues();
        ContentValues tbRolUsuario= new ContentValues();
        boolean succes = false;
        String [] args ;


        SQLiteDatabase db = conexion.getBd();
        db.beginTransaction();
        try {
            tbUsuario.put("nombre",usuario.getNombre());
            tbUsuario.put("contrasena",usuario.getContrasena());
            tbUsuario.put("modificadoPor",usuario.getModificadoPor());
            tbUsuario.put("fechaModificacion",usuario.getFechaModificacion());
            args = new String[]{String.valueOf(usuario.getIdUsuario())};
            conexion.mTrassacionModificar(tbUsuario,TABLA_USUARIOS,"idUsuario=?",args);


            if(!roles.isEmpty()) {
                args = new String[]{String.valueOf(usuario.getIdUsuario()),null};
                for (Rol rol : roles) {
                    args[1] = String.valueOf(rol.getIdRol());
                    Cursor cursor = conexion.mConsultar("select idRol from tbRolUsuario where idUsuario = ? and idRol = ?",args);

                    if (cursor.getCount() == 0 && rol.isAsignado()) {
                        tbRolUsuario.put("idRol", rol.getIdRol());
                        tbRolUsuario.put("idUsuario", usuario.getIdUsuario());
                        conexion.mTransaccionInsertar(tbRolUsuario, TABLA_ROLUSUARIO);
                    } else if(!rol.isAsignado()){
                        conexion.mTransaccionEliminar(TABLA_ROLUSUARIO, "idUsuario = ? and idRol = ?", args);
                    }
                }
            }
            else {
                args = new String[]{String.valueOf(usuario.getIdUsuario())};
                conexion.mTransaccionEliminar(TABLA_ROLUSUARIO, "idUsuario = ?", args);
            }


            for (Ventana ventana:ventanas) {
                args = new String[]{String.valueOf(usuario.getIdUsuario()), String.valueOf(ventana.getIdVentana())};
                if(ventana.isTienePermisos()) {
                    Log.i("mmmmmmm",String.valueOf(ventana.getPermiso("ver").isActivado()));
                    tbUsuarioVentana.put("ver",ventana.getPermiso("ver").isActivado() ? 1:0);
                    tbUsuarioVentana.put("insertar",ventana.getPermiso("insertar").isActivado() ? 1:0);
                    tbUsuarioVentana.put("modificar",ventana.getPermiso("modificar").isActivado() ? 1:0);
                    tbUsuarioVentana.put("eliminar",ventana.getPermiso("eliminar").isActivado() ? 1:0);

                    Cursor cursor = conexion.mConsultar("select * from tbUsuarioVentana where idUsuario = ? and idVentana = ?",args);

                    if(cursor.getCount()>0)
                        conexion.mTrassacionModificar(tbUsuarioVentana,TABLA_VENTANAUSUARIO,"idUsuario = ? and idVentana = ?",args);
                    else {
                        Log.i("mm",usuario.getIdUsuario()+"");
                        tbUsuarioVentana.put("idUsuario",usuario.getIdUsuario());
                        tbUsuarioVentana.put("idVentana",ventana.getIdVentana());
                        conexion.mTransaccionInsertar(tbUsuarioVentana, TABLA_VENTANAUSUARIO);
                    }

                }

            }

            db.setTransactionSuccessful();
            succes = true;

        }catch (SQLiteException e){
            succes= false;
        }
        finally {
            db.endTransaction();
        }

        return succes;
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

    public Usuario getUsuarioEspecifico(int idUsuario){
        String query = "Select * from tbUsuarios where idUsuario= ?";
        String [] args = {String.valueOf(idUsuario)};
        Cursor cursor= conexion.mConsultar(query, args);
        cursor.moveToFirst();
        return new Usuario(cursor.getInt(0),cursor.getString(1),
                           cursor.getString(2),cursor.getString(3),
                           cursor.getString(4),cursor.getString(5),
                          cursor.getString(6));
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
