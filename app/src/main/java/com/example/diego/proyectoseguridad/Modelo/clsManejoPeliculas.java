package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by victor on 15/06/2016.
 */
public class clsManejoPeliculas {
    private SQLiteDatabase bd;
    private clsConexion conexion;
    private static final String TABLA_PELICULAS="tbPeliculas";
    private static final String TABLA_GENEROS="tbGeneros";
    private static final String TABLA_GENEROS_PELICULAS="tbGeneroPelicula";

    public clsManejoPeliculas(Context context)
    {
        this.conexion=new clsConexion(context);
    }

    public Cursor getGenerosPeliculas(int idPelicula)
    {
        Cursor cursor;
        cursor=bd.rawQuery("select g.genero gp.idPelicula from tbGeneros g inner join tbGeneroPelicula gp on(g.idGenero=gp.idGenero) where gp.idPelicula=?",new String[]{String.valueOf(idPelicula)});
        return cursor;
    }
}
