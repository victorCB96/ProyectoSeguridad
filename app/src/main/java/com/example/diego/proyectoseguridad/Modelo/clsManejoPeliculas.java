package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

    private Cursor getGenerosPeliculas(int idPelicula)
    {

        String query = "select g.genero, gp.idPelicula from tbGeneros g inner join tbGeneroPelicula gp on(g.idGenero=gp.idGenero) where gp.idPelicula=?";
        String [] args = {String.valueOf(idPelicula)};
        return conexion.mConsultar(query,args);
    }

    public ArrayList<Pelicula> getPeliculasClasificacion(Clasificacion clasificacion){
        String query = "select idPelicula, nombre, sinopsis, imagen from tbPeliculas where idClasificacion = ?";
        String [] args = {String.valueOf(clasificacion.getIdClasificacion())};

        Cursor cursorPeliculas = conexion.mConsultar(query, args);
        ArrayList<Pelicula> peliculas = new ArrayList<>();

        while (cursorPeliculas.moveToNext()){
            peliculas.add(new Pelicula(
                                cursorPeliculas.getInt(0),
                                cursorPeliculas.getString(1),
                                cursorPeliculas.getString(2),
                                cursorPeliculas.getString(3),
                                clasificacion.getIdClasificacion(),
                                getGenerosPeliculas(cursorPeliculas.getInt(0))));
        }

        cursorPeliculas.close();
        return peliculas;

    }
}
