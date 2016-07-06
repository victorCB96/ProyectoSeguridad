package com.example.diego.proyectoseguridad.Modelo;

import java.util.ArrayList;

/**
 * Created by roy on 27/06/16.
 */
public class Variables {
    public static boolean PERMISO_EDITAR_USUARIOS=false;
    public static boolean PERMISO_ELIMINAR_USUARIOS=false;
    public static boolean PERMISO_AGREGAR_USUARIOS=false;

    public static boolean PERMISO_EDITAR_ROLES=false;
    public static boolean PERMISO_ELIMINAR_ROLES=false;
    public static boolean PERMISO_AGREGAR_ROLES=false;

    public static ArrayList<RolVentana> PERMISOS= new ArrayList<>();
    public static ArrayList<Integer> PERMISOS_CLASIFICACIONES= new ArrayList<>();
    public static ArrayList<Integer> PERMISOS_CLASIFICACIONES_ELIMINACION= new ArrayList<>();


}
