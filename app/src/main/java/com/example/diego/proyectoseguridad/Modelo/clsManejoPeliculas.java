package com.example.diego.proyectoseguridad.Modelo;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by victor on 15/06/2016.
 */
public class clsManejoPeliculas {
    private clsConexion conexion;
    private static final String TABLA_PELICULAS="tbPeliculas";
    private static final String TABLA_GENEROS="tbGeneros";
    private static final String TABLA_GENEROS_PELICULAS="tbGeneroPelicula";

    public clsManejoPeliculas(Context context)
    {
        this.conexion=new clsConexion(context);
    }

    public Cursor getGenerosPeliculas(Pelicula pelicula)
    {
        String query;
        query="select g.genero gp.idPelicula from tbGeneros g inner join tbGeneroPelicula gp on(g.idGenero=gp.idGenero) where gp.idPelicula='"+pelicula.getIdPelicula()+"'";
        return conexion.mConsultar(query);
    }
}
