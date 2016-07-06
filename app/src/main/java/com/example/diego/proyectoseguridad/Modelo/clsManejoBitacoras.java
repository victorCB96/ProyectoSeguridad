package com.example.diego.proyectoseguridad.Modelo;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by victor on 26/06/2016.
 */
public class clsManejoBitacoras {

    private clsConexion conexion;
    public static final String TABLA_BITACORAS="tbBitacoras";

    public clsManejoBitacoras(Context context) {
        this.conexion = new clsConexion(context);
    }

    public boolean mAgregarBitacora(Bitacora bitacora) {
        ContentValues valores = new ContentValues();
        //valores.put("idBitacora", bitacora.getIdBitacora());
        valores.put("accion", bitacora.getAccion());
        valores.put("descripcion", bitacora.getDescripcion());
        valores.put("hora",(bitacora.getHora()).toString());
        valores.put("idUsuario", bitacora.getIdUsuario());

        return conexion.mInsertar(valores, TABLA_BITACORAS);
    }

}
